package com.example.ayush.funstuff;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.orm.SugarContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> seriesnames;
    EditText editText;
    RecyclerView recyclerView;
    ImageView imageView;
    Subscription subscription;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SugarContext.init(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        editText = (EditText) findViewById(R.id.namequery);
        recyclerView = (RecyclerView) findViewById(R.id.rv);
        imageView = (ImageView) findViewById(R.id.subscribed);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);
        seriesnames = new ArrayList<>();
        recyclerView.setAdapter(new Adapter(this, seriesnames));
        editText.addTextChangedListener(textWatcher);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                {
                    String temp = "";
                    Iterator<Subscription> subscriptionIterator = Subscription.findAll(Subscription.class);
                    while (subscriptionIterator.hasNext())
                    {
                        subscription = subscriptionIterator.next();
                        temp+=subscription.nameOfSeries;
                    }
                    Toast.makeText(getBaseContext(),temp,Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length()!=0)
            {
                String processedquery=s.toString();
                seriesnames.clear();
                updateList(processedquery);
            }
//            else
//            {
//                textView.setText("");
//            }

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SugarContext.terminate();
    }

    private void updateList(String query)
    {
        String url = "http://thetvdb.com/api/GetSeries.php?seriesname="+query;
        if(url.contains(" "))
        {
            url=url.replace(' ','+');
        }
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                JSONObject jsonObject=null;
                try
                {
                    jsonObject = XML.toJSONObject(response);
                    //Toast.makeText(getBaseContext(),""+jsonObject.getString("Data").toString(),Toast.LENGTH_SHORT).show();
                    if(jsonObject.getString("Data").toString().length()!=0)
                    {
                        JSONArray jsonArray;
                        jsonObject = jsonObject.getJSONObject("Data");
                        String seriesjson = jsonObject.getString("Series");
                       // Toast.makeText(getBaseContext(),""+seriesjson,Toast.LENGTH_SHORT).show();
                        if(!seriesjson.contains("["))
                        {
                            seriesjson="["+seriesjson+"]";
                        }
                        jsonArray = new JSONArray(seriesjson);

                        for (int i=0;i<jsonArray.length();++i)
                        {
                            jsonObject = jsonArray.getJSONObject(i);
                            seriesnames.add(jsonObject.getString("SeriesName"));
                        }
                        recyclerView.setAdapter(new Adapter(getBaseContext(), seriesnames));
                        //
                       // textView.setText(seriesnames.toString());
                    }
                    //else
                    //{
                     //   textView.setText("");
                    //}
                }
                catch (JSONException e) {e.printStackTrace();}

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Volley.newRequestQueue(this).add(stringRequest);
       // Toast.makeText(getBaseContext(), "MainActivity:" + seriesnames.size(), Toast.LENGTH_SHORT).show();
    }

}

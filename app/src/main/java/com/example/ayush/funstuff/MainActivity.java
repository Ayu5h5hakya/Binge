package com.example.ayush.funstuff;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    ArrayList<String> seriesnames;
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        textView = (TextView) findViewById(R.id.response);
        editText = (EditText) findViewById(R.id.namequery);
        editText.addTextChangedListener(textWatcher);
        seriesnames = new ArrayList<>();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                {


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
            else
            {
                textView.setText("");
            }

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

    private void updateList(String query)
    {
        String url = "http://thetvdb.com/api/GetSeries.php?seriesname="+query;
        if(url.contains(" "))
        {
            url=url.replace(' ','+');
        }
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonObject=null;
                try {
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
                        textView.setText(seriesnames.toString());
                    }
                    else
                    {
                        textView.setText("");
                    }
                }
                catch (JSONException e) {e.printStackTrace();}

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Volley.newRequestQueue(this).add(stringRequest);
    }

}

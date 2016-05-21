package com.example.ayush.funstuff;

import android.content.Intent;
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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.stetho.Stetho;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> seriesnames,backpath;
    ArrayList<Integer> series_ids;
    EditText editText;
    RecyclerView recyclerView;
    ImageView imageView;
    Subscription subscription;
    static String api_key = "83ef7189721a67650fe8f404af8cf6aa";
    FloatingActionButton fab;
    public static Realm realm=null;
    public static DatabaseController databaseController = null;
    public static String directory = "Binge";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                        .build());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        editText = (EditText) findViewById(R.id.namequery);
        recyclerView = (RecyclerView) findViewById(R.id.rv);
        fab = (FloatingActionButton) findViewById(R.id.s_fab);

        realm =Realm.getInstance(this);
        databaseController = new DatabaseController(this,realm);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);
        seriesnames = new ArrayList<>();
        backpath=new ArrayList<>();
        series_ids = new ArrayList<>();
        recyclerView.setAdapter(new Adapter(this, seriesnames,series_ids,backpath));
        editText.addTextChangedListener(textWatcher);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.example.ayush.funstuff.Serieslist");
                startActivity(intent);
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
                series_ids.clear();
                backpath.clear();
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
    }

    private void updateList(String query)
    {
        if(query.contains(" "))
        {
            query=query.replace(' ','+');
        }
        String url = "http://api.themoviedb.org/3/search/tv?api_key=83ef7189721a67650fe8f404af8cf6aa&query="+query;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String temp = response.getString("results");
                    JSONArray jsonArray = new JSONArray(temp);
                    temp="";
                    for(int i=0;i<jsonArray.length();++i)
                    {
                        response = jsonArray.getJSONObject(i);
                        seriesnames.add(response.getString("name"));
                        series_ids.add(response.getInt("id"));
                        backpath.add(response.getString("backdrop_path"));
                    }
                    recyclerView.setAdapter(new Adapter(getBaseContext(), seriesnames,series_ids,backpath));
                }
                catch (JSONException e) {e.printStackTrace();}
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Volley.newRequestQueue(this).add(jsonObjectRequest);
    }
}

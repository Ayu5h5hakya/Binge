package com.example.ayush.funstuff;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Ayush on 5/7/2016.
 */
public class SeriesDetails extends AppCompatActivity {

    private String new_series_url = "http://api.themoviedb.org/3/tv/";
    ImageView imageView;
    TextView seriestitle,synopsis;
    private String name,path;
    private int id;
    private int latest_season=-1,latest_episode=-1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.series_details);

        imageView= (ImageView) findViewById(R.id.poster);
        seriestitle= (TextView) findViewById(R.id.title_detail);
        synopsis= (TextView) findViewById(R.id.overview_detail);

        Intent intent = getIntent();
        path = intent.getStringExtra("back_path");
        name = intent.getStringExtra("seriesname");
        id = intent.getIntExtra("id",-1);

        String url = new_series_url+id+"?api_key="+MainActivity.api_key;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONObject jsonObject;
                try {
                    latest_season=response.getInt("number_of_seasons");
                    String seasons_info=response.getString("seasons");
                    JSONArray jsonArray=new JSONArray(seasons_info);
                    jsonObject=jsonArray.getJSONObject(jsonArray.length()-1);
                    latest_episode=jsonObject.getInt("episode_count");

                    Picasso.with(getBaseContext()).load("http://image.tmdb.org/t/p/w500"+path).placeholder(R.drawable.ic_star_outline_24dp).into(imageView);
                    seriestitle.setText(name);
                    synopsis.setText(response.getString("overview"));

                }
                catch (JSONException e){}
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getBaseContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}

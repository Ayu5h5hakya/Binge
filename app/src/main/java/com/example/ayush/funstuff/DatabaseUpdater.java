package com.example.ayush.funstuff;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by ayush on 3/8/16.
 */
public class DatabaseUpdater {
    Context context;
    Subscription subscription;
    int latest_season;
    int latest_episode;
    Date date;

    DatabaseUpdater(Context context)
    {
        this.context = context;
    }
    public void update(String series_name,Long series_id){
        String url = "http://api.themoviedb.org/3/tv/"+series_id+"?api_key="+MainActivity.api_key;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, (String) null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    latest_season = response.getInt("number_of_seasons");
                    String season_info = response.getString("seasons");
                    JSONArray jsonArray = new JSONArray(season_info);
                    JSONObject jsonObject = jsonArray.getJSONObject(jsonArray.length() - 1);
                    latest_episode = jsonObject.getInt("episode_count");
                    //Toast.makeText(context,""+latest_episode,Toast.LENGTH_LONG).show();
                }
                catch (JSONException e) {e.printStackTrace();}
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        url = "http://api.themoviedb.org/3/tv/"+series_id+"/season/"+latest_season+"/episode/"+latest_episode+"?api_key="+MainActivity.api_key;
        JsonObjectRequest jsonObjectNextRequest = new JsonObjectRequest(Request.Method.GET, url, (String) null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String air_date = response.getString("air_date");
                    DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                    date = dateFormat.parse(air_date);
                    //Toast.makeText(context,,Toast.LENGTH_LONG).show();
                }
                catch (JSONException e) {e.printStackTrace();}
                catch (ParseException e) {e.printStackTrace();}
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        Volley.newRequestQueue(context).add(jsonObjectRequest);
        Volley.newRequestQueue(context).add(jsonObjectNextRequest);
        Toast.makeText(context,""+latest_season+" "+latest_episode,Toast.LENGTH_LONG).show();
       // subscription = new Subscription(series_name,series_id,latest_season,latest_episode,date);
       // subscription.save();
    }
}

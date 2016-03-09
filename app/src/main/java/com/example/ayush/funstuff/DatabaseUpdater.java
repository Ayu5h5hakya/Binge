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
    Date parsed_date;
    int latest_season;
    int latest_episode;
    String date;

    DatabaseUpdater(Context context)
    {
        this.context = context;
    }
    public void update(final String series_name,final Long series_id){
        String url = "http://api.themoviedb.org/3/tv/"+series_id+"?api_key="+MainActivity.api_key;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, (String) null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    latest_season=response.getInt("number_of_seasons");
                    String season_info = response.getString("seasons");
                    JSONArray jsonArray = new JSONArray(season_info);
                    JSONObject jsonObject = jsonArray.getJSONObject(jsonArray.length() - 1);
                    latest_episode=jsonObject.getInt("episode_count");
                    getDate(latest_season,latest_episode);
                }
                catch (JSONException e) {e.printStackTrace();}
            }

            public void getDate(final int latest_season, final int latest_episode)
            {
                String url = "http://api.themoviedb.org/3/tv/"+series_id+"/season/"+latest_season+"/episode/"+latest_episode+"?api_key="+MainActivity.api_key;
                JsonObjectRequest jsonObjectNextRequest = new JsonObjectRequest(Request.Method.GET, url, (String) null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            date = response.getString("air_date");
                            Toast.makeText(context,""+date+" "+latest_episode+" "+latest_season,Toast.LENGTH_SHORT).show();
                            DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                            try {
                                parsed_date = dateFormat.parse(date);
                            } catch (ParseException e) {e.printStackTrace();}
                            subscription = new Subscription(series_name,series_id,latest_season,latest_episode,parsed_date);
                            subscription.save();
                        }
                        catch (JSONException e) {e.printStackTrace();}
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                Volley.newRequestQueue(context).add(jsonObjectNextRequest);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Volley.newRequestQueue(context).add(jsonObjectRequest);
//
//        //ss
        // Toast.makeText(context,""+latest_season[0]+" "+latest_episode[0]+parsed_date,Toast.LENGTH_LONG).show();
       //
    }
}

package com.example.ayush.funstuff;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.realm.Realm;

/**
 * Created by Ayush on 5/6/2016.
 */
public class DatabaseController {

    private String new_series_url = "http://api.themoviedb.org/3/tv/";
    private int latest_season=-1;
    private Context context;
    private int latest_episode=-1;
    private Realm realm=null;
    public DatabaseController(Context context)
    {
        this.context=context;
        if(realm==null) realm=Realm.getInstance(context);
    }
    public void updateDatabase(final Integer series_id, final String series_name)
    {
        String url = new_series_url+series_id+"?api_key="+MainActivity.api_key;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, (JSONObject)null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try
                {
                    JSONObject jsonObject;
                    latest_season=response.getInt("number_of_seasons");
                    String seasons_info=response.getString("seasons");
                    JSONArray jsonArray=new JSONArray(seasons_info);
                    jsonObject=jsonArray.getJSONObject(jsonArray.length()-1);
                    latest_episode=jsonObject.getInt("episode_count");

                    realm.beginTransaction();
                        Subscription newSubscription=realm.createObject(Subscription.class);
                        newSubscription.setNameOfSeries(series_name);
                        newSubscription.setSeries_id(series_id);
                        newSubscription.setLatest_season(latest_season);
                        newSubscription.setLast_episode(latest_episode);
                    realm.commitTransaction();
                }
                catch (JSONException e) {}

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        Volley.newRequestQueue(context).add(jsonObjectRequest);
    }
}

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
import io.realm.RealmResults;

/**
 * Created by Ayush on 5/6/2016.
 */
public class DatabaseController {

    private String new_series_url = "http://api.themoviedb.org/3/tv/";
    private int latest_season=-1;
    private Context context;
    private int latest_episode=-1;
    private Realm realm=null;
    public DatabaseController(Context context,Realm realm)
    {
        this.context=context;
        this.realm = realm;
    }
    public void updateDatabase(String id,String name,int latest_season,int latest_episode,String details)
    {
        RealmResults<Subscription> realmResults = realm.where(Subscription.class).contains("series_id",id).findAll();
        if(realmResults.size()!=0) {
            Toast.makeText(context,"You have already subscribed to this series",Toast.LENGTH_SHORT).show();
            return;
        }
        realm.beginTransaction();
        Subscription newSubscription = realm.createObject(Subscription.class);
        newSubscription.setSeries_id(id);
        newSubscription.setNameOfSeries(name);
        newSubscription.setLatest_season(latest_season);
        newSubscription.setLast_episode(latest_episode);
        newSubscription.setAdditionalInformation(details);
        realm.commitTransaction();
    }
}

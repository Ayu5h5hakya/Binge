package com.example.ayush.funstuff;

import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Ayush on 5/7/2016.
 */
public class SeriesDetails extends AppCompatActivity {

    public static String new_series_url = "http://api.themoviedb.org/3/tv/";
    ImageView imageView;
    TextView seriestitle,overview,seasoncount,episode_number;
    private JSONObject details;
    private int id,latest_season=-1,latest_episode=-1;
    private String name,synopsis,path;
    FloatingActionButton subscribe_button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.series_details);

        imageView= (ImageView) findViewById(R.id.poster);
        seriestitle= (TextView) findViewById(R.id.title_detail);
        overview= (TextView) findViewById(R.id.overview_detail);
        seasoncount = (TextView) findViewById(R.id.season_count);
        episode_number = (TextView) findViewById(R.id.episode_number);
        subscribe_button = (FloatingActionButton) findViewById(R.id.fab);
        subscribe_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SeriesDetails.this, "subscribed!", Toast.LENGTH_SHORT).show();
                MainActivity.databaseController.updateDatabase(""+id,name,latest_season,latest_episode,details.toString());
                BitmapDrawable tempBitmapDrawable = (BitmapDrawable) imageView.getDrawable();
                saveToInternal(tempBitmapDrawable.getBitmap());
            }
        });
        Intent intent = getIntent();
        try {
            details = new JSONObject(intent.getStringExtra("all_details"));
            id=details.getInt("id");
            path = details.getString("backdrop_path");
            name = details.getString("name");
            synopsis = details.getString("overview");
            latest_season = details.getInt("number_of_seasons");
            String seasons_details = details.getString("seasons");
            JSONArray jsonArray = new JSONArray(seasons_details);
            JSONObject jsonObject = jsonArray.getJSONObject(jsonArray.length()-1);
            latest_episode = jsonObject.getInt("episode_count");
            RealmResults<Subscription> results = MainActivity.realm.where(Subscription.class).contains("series_id",id+"").findAll();
            Picasso.with(this).load("http://image.tmdb.org/t/p/w500"+path).placeholder(R.drawable.ic_star_outline_24dp).into(imageView);
            seriestitle.setText(name);
            overview.setText(synopsis);
            seasoncount.append(""+latest_season);
            episode_number.append("Season " + latest_season + ",Episode "+latest_episode);
        }
        catch (JSONException e) {}
    }

    private String saveToInternal(Bitmap bitmap)
    {
        ContextWrapper cw = new ContextWrapper(this);
        File directory= cw.getDir("Binge",MODE_PRIVATE),
             myPath = new File(directory,name+".jpg");
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(myPath);
            bitmap.compress(Bitmap.CompressFormat.PNG,100,fos);
            fos.close();
        }
        catch (FileNotFoundException e) {}
        catch (IOException e) {}
        return directory.getAbsolutePath();
    }
}

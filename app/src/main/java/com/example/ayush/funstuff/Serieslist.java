package com.example.ayush.funstuff;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Ayush on 5/8/2016.
 */
public class Serieslist extends AppCompatActivity {
    RecyclerView recyclerView=null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sublist);
        recyclerView= (RecyclerView) findViewById(R.id.s_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        RealmResults<Subscription> results = MainActivity.realm.where(Subscription.class).findAll();
        //Toast.makeText(this,results.size()+"",Toast.LENGTH_LONG).show();
        recyclerView.setAdapter(new S_adapter(results,this));
    }
}

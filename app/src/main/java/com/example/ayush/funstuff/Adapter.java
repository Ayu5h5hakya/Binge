package com.example.ayush.funstuff;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;

import io.realm.Realm;

/**
 * Created by ayush on 2/28/16.
 */
public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    LayoutInflater layoutInflater;
    ArrayList<String> seriesnames,backpath;
    ArrayList<Integer> seriesids;
    Context context;
    DatabaseController databaseController=null;

    public Adapter(Context context,ArrayList<String> seriesnames,ArrayList<Integer> series_ids,ArrayList<String> backpath){
        layoutInflater = LayoutInflater.from(context);
        this.seriesnames = seriesnames;
        this.context = context;
        this.seriesids = series_ids;
        this.backpath=backpath;
        if (databaseController==null) {databaseController = new DatabaseController(context);}
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.child_layout,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textView.setText(seriesnames.get(position));
    }

    @Override
    public int getItemCount() {
        return seriesnames.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView textView;
        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.textView);
            textView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(context,""+seriesids.get(getAdapterPosition()),Toast.LENGTH_SHORT).show();
            Intent intent = new Intent("com.example.ayush.funstuff.SeriesDetails");
            intent.putExtra("back_path",backpath.get(getAdapterPosition()));
            intent.putExtra("id",seriesids.get(getAdapterPosition()));
            intent.putExtra("seriesname",seriesnames.get(getAdapterPosition()));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            //databaseController.updateDatabase(seriesids.get(getAdapterPosition()),seriesnames.get(getAdapterPosition()));
        }
    }
}

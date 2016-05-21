package com.example.ayush.funstuff;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import io.realm.RealmResults;

/**
 * Created by Ayush on 5/15/2016.
 */
public class S_adapter extends RecyclerView.Adapter<S_adapter.ViewHolder>{

    private final RealmResults<Subscription> results;
    LayoutInflater layoutInflater;
    Context context;
    S_adapter(RealmResults<Subscription> results, Context context)
    {
        layoutInflater = LayoutInflater.from(context);
        this.results = results;
        this.context=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =layoutInflater.inflate(R.layout.s_child,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String temp = results.get(position).getNameOfSeries();
        holder.textview.setText(temp);
        try {
            holder.imageView.setImageBitmap(BitmapFactory.decodeStream(new FileInputStream(new File("/data/data/"+context.getPackageName()+"/app_Binge/"+temp+".jpg"))));
        }
        catch (FileNotFoundException e) {}
    }
    @Override
    public int getItemCount() {
        return results.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView textview;
        ImageView imageView;
        public ViewHolder(View itemView) {
            super(itemView);
            textview = (TextView) itemView.findViewById(R.id.s_title);
            imageView= (ImageView) itemView.findViewById(R.id.s_poster);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent("com.example.ayush.funstuff.SeriesDetails");
            intent.putExtra("all_details",results.get(getAdapterPosition()).getAdditionalInformation());
            context.startActivity(intent);
        }
    }

    private Bitmap loadImageFromStorage(String path)
    {
        Bitmap b=null;
        try {
            File f=new File(path, "profile.jpg");
            b = BitmapFactory.decodeStream(new FileInputStream(f));
        }
        catch (FileNotFoundException e) {}
        return b;
    }
}

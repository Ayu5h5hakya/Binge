package com.example.ayush.funstuff;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by ayush on 2/28/16.
 */
public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    LayoutInflater layoutInflater;
    ArrayList<String> seriesnames;
    Context context;
    Subscription subscription;
    public Adapter(Context context,ArrayList<String> seriesnames){
        layoutInflater = LayoutInflater.from(context);
        this.seriesnames = seriesnames;
        this.context = context;

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
            //Toast.makeText(context,""+textView.getText(),Toast.LENGTH_SHORT).show();
            subscription = new Subscription(textView.getText().toString());
            subscription.save();
        }
    }
}

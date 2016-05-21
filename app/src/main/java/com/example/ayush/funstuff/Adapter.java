package com.example.ayush.funstuff;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ayush on 2/28/16.
 */
public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    LayoutInflater layoutInflater;
    ArrayList<String> seriesnames,backpath;
    ArrayList<Integer> seriesids;
    Context context;

    public Adapter(Context context,ArrayList<String> seriesnames,ArrayList<Integer> series_ids,ArrayList<String> backpath){
        layoutInflater = LayoutInflater.from(context);
        this.seriesnames = seriesnames;
        this.context = context;
        this.seriesids = series_ids;
        this.backpath=backpath;
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
        ProgressBar progressBar;
        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.textView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progress);
            textView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            progressBar.setVisibility(View.VISIBLE);
            String url = SeriesDetails.new_series_url+seriesids.get(getAdapterPosition())+"?api_key="+MainActivity.api_key;
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, (JSONObject) null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Toast.makeText(context,""+seriesids.get(getAdapterPosition()),Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent("com.example.ayush.funstuff.SeriesDetails");
                        intent.putExtra("all_details",response.toString());
//                        intent.putExtra("back_path",backpath.get(getAdapterPosition()));
//                        intent.putExtra("id",seriesids.get(getAdapterPosition()));
//                        intent.putExtra("seriesname",seriesnames.get(getAdapterPosition()));
//                        intent.putExtra("overview",response.getString("overview"));
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        progressBar.setVisibility(View.INVISIBLE);
                        context.startActivity(intent);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context,error.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });
            Volley.newRequestQueue(context).add(jsonObjectRequest);
            //
            //databaseController.updateDatabase(seriesids.get(getAdapterPosition()),seriesnames.get(getAdapterPosition()));
        }
    }
}

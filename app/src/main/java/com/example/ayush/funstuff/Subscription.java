package com.example.ayush.funstuff;

import com.orm.SugarRecord;

import java.util.Date;

/**
 * Created by ayush on 2/28/16.
 */
public class Subscription extends SugarRecord<Subscription> {
    public String nameOfSeries;
    public Long series_id;
    public int latest_season=-1;
    public int last_episode=-1;
    public Date last_air_date=null;
    public Subscription(){}
    public Subscription(String nameOfSeries,Long series_id,int latest_season,int last_episode,Date last_air_date)
    {
        this.nameOfSeries = nameOfSeries;
        this.series_id=series_id;
        this.latest_season = latest_season;
        this.last_episode = last_episode;
        this.last_air_date = last_air_date;
    }
}

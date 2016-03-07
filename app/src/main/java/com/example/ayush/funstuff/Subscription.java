package com.example.ayush.funstuff;

import com.orm.SugarRecord;

/**
 * Created by ayush on 2/28/16.
 */
public class Subscription extends SugarRecord<Subscription> {
    public String nameOfSeries;
    public Long series_id;

    public Subscription(){}
    public Subscription(String nameOfSeries,Long series_id)
    {
        this.nameOfSeries = nameOfSeries;this.series_id=series_id;
    }
}

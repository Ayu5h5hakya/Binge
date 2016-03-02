package com.example.ayush.funstuff;

import com.orm.SugarRecord;

/**
 * Created by ayush on 2/28/16.
 */
public class Subscription extends SugarRecord<Subscription> {
    public String nameOfSeries;

    public Subscription(){}
    public Subscription(String nameOfSeries)
    {
        this.nameOfSeries = nameOfSeries;
    }
}

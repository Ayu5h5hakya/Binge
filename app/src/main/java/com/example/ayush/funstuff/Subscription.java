package com.example.ayush.funstuff;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by ayush on 2/28/16.
 */
public class Subscription extends RealmObject {

    @PrimaryKey
    private Integer series_id;
    private int latest_season=-1;
    private int last_episode=-1;
    //private Date last_air_date=null;
    private String nameOfSeries;

    public Subscription(){}

    public String getNameOfSeries(){return nameOfSeries;}
    public void setNameOfSeries(String nameOfSeries){this.nameOfSeries=nameOfSeries;}

    public Integer getSeries_id(){return series_id;}
    public void setSeries_id(Integer series_id) {this.series_id=series_id;}

    public int getLatest_season(){return latest_season;}
    public void setLatest_season(int latest_season){this.latest_season=latest_season;}

    public int getLast_episode(){return last_episode;}
    public void setLast_episode(int last_episode){this.last_episode=last_episode;}

    //public Date getLast_air_date(){return last_air_date;}
    //public void setLast_air_date(Date last_air_date){this.last_air_date=last_air_date;}
}

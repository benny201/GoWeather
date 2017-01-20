package com.example.benny.goweather.db;

import org.litepal.crud.DataSupport;

/**
 * Created by benny on 17/1/18.
 */
public class County extends DataSupport {
    private int id;
    private String countyName;
    private String weatherId;
    private int cityId;

    //ID
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    //countyName
    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public String getCountyName() {
        return countyName;
    }

    //weatherId
    public void setWeatherId(String weatherId) {
        this.weatherId = weatherId;
    }

    public String getWeatherId() {
        return weatherId;
    }

    //cityID
    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public int getCityId() {
        return cityId;
    }
}

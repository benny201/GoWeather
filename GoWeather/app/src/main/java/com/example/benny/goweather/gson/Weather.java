package com.example.benny.goweather.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by benny on 17/1/19.
 */
public class Weather {

    public String status;

    public Basic basic;

    public AQI aqi;

    public Now now;

    public Suggestion suggestion;

    @SerializedName("daily_forecast")
    public List<ForeCast> foreCastList;
}

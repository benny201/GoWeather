package com.example.benny.goweather.gson;

/**
 * Created by benny on 17/1/19.
 */
public class AQI {

    public AQICity city;

    public class AQICity {
        public String aqi;
        public String pm25;
    }
}

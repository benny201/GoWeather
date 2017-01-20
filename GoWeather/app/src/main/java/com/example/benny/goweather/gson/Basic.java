package com.example.benny.goweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by benny on 17/1/19.
 */
public class Basic {
    @SerializedName("city")
    public String cityName;

    @SerializedName("id")
    public String weatherId;

    public Update update;

    public class Update {
        @SerializedName("loc")
        public String updateTime;
    }
}

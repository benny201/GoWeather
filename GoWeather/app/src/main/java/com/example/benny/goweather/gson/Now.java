package com.example.benny.goweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by benny on 17/1/19.
 */
public class Now {

    @SerializedName("tmp")
    public String temperature;

    @SerializedName("cond")
    public More more;

    public class More {

        @SerializedName("txt")
        public String info;
    }
}

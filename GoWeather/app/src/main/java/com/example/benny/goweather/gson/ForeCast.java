package com.example.benny.goweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by benny on 17/1/19.
 */
public class ForeCast {
    public String date;

    @SerializedName("tmp")
    public Temperature temperature;

    @SerializedName("cond")
    public More more;

    public class Temperature {
        public String max;
        public String min;
    }

    public class More {
        @SerializedName("txt_d")
        public String info;
    }
}

package com.example.benny.goweather.db;

import org.litepal.crud.DataSupport;

/**
 * Created by benny on 17/1/18.
 */
public class City extends DataSupport {
    private int id;
    private String cityName;
    private int cityCode;
    private int provinceId;

    //ID
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    //CityName
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityName() {
        return cityName;
    }

    //City code
    public void setCityCode(int cityCode) {
        this.cityCode = cityCode;
    }

    public int getCityCode() {
        return cityCode;
    }

    //provinceId : store the province that the city belong
    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    public int getProvinceId() {
        return provinceId;
    }
}

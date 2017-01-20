package com.example.benny.goweather.db;

import org.litepal.crud.DataSupport;

/**
 * Created by benny on 17/1/18.
 */
public class Province extends DataSupport {
    private int id ;
    private String provinceName;
    private int provinceCode;


    //ID
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    //ProvinceCode
    public void setProvinceCode(int provinceCode) {
        this.provinceCode = provinceCode;
    }

    public int getProvinceCode() {
        return provinceCode;
    }

    //ProvinceName
    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getProvinceName() {
        return provinceName;
    }
}

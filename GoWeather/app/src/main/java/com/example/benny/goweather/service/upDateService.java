package com.example.benny.goweather.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.benny.goweather.WeatherActivity;
import com.example.benny.goweather.gson.Weather;
import com.example.benny.goweather.util.HttpUtil;
import com.example.benny.goweather.util.Utility;
import com.example.benny.goweather.util.Statics;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class upDateService extends Service {

    private static final int update_weather = 0;
    private static final int update_pic = 1;

    public upDateService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        updateWeather();
        upadtePic();
        //Log.d("BENNYTIMETEST", "onStartCommand: uodated" );
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        int anHour =  8 * 60 * 60 *1000;
        //int anHour = 10 * 1000;
        long triggerAtTime = SystemClock.elapsedRealtime() + anHour;
        Intent intent1 = new Intent(this, upDateService.class);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent1, 0);
        manager.cancel(pendingIntent);
        manager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pendingIntent);
        return super.onStartCommand(intent, flags, startId);
    }

    public void updateWeather() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String weatherPre = preferences.getString("weather", null);
        if (weatherPre != null) {
                    Weather weather = Utility.handleWeatherResponse(weatherPre);
                    String weatherID = weather.basic.weatherId;
                    String weatherUrl = "http://guolin.tech/api/weather?cityid" + weatherID + "&key=bf58d34d77464f359a5d232bf1ae2716";
                    HttpUtil.sendOKHttpRequest(weatherUrl, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String responseText = response.body().string();
                            Weather weather = Utility.handleWeatherResponse(responseText);
                            if (weather != null && "ok".equals(weather.status)) {
                                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(upDateService.this).edit();
                                editor.putString("weather", responseText);
                                editor.commit();
                            }
                        }
                    });
                }
        }

    public void upadtePic() {
        String requestBingPic = "http://guolin.tech/api/bing_pic";
        HttpUtil.sendOKHttpRequest(requestBingPic, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String bingPic = response.body().string();
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(upDateService.this).edit();
                editor.putString("bing_pic", bingPic);
                editor.commit();
            }
        });
    }
    }


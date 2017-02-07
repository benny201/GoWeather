package com.example.benny.goweather;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.benny.goweather.gson.ForeCast;
import com.example.benny.goweather.gson.Weather;
import com.example.benny.goweather.util.HttpUtil;
import com.example.benny.goweather.util.Utility;
import com.example.benny.goweather.util.Statics;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import com.example.benny.goweather.service.upDateService;
import com.example.benny.goweather.service.notificateService;

/**
 * Created by benny on 17/1/19.
 */
public class WeatherActivity extends AppCompatActivity {

    private TextView titleCity;

    private TextView titleUpdateTime;

    private TextView degreeText;

    private TextView weatherIndoText;

    private TextView aqiText;

    private TextView pm25Text;

    private TextView comfortText;

    private TextView carWashText;

    private TextView sportText;

    private TextView cityTitleInNow;

    private Button selectCountyButton;

    private ImageView bingPic;

    private SwipeRefreshLayout swipeRefresh;


    private LinearLayout forecastLayout;
    private ScrollView weatherLayout;
    public DrawerLayout drawerLayout;

    private final String TAG = "BENNY's Test!";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        setContentView(R.layout.weather_activity);

        Log.d(TAG, "onCreate: created!");



        //textview initialize
        titleCity = (TextView) findViewById(R.id.title_city);
        titleUpdateTime = (TextView) findViewById(R.id.title_update_time);
        degreeText = (TextView) findViewById(R.id.degree_text);
        weatherIndoText = (TextView) findViewById(R.id.weather_info);
        aqiText = (TextView)  findViewById(R.id.aqi_text);
        pm25Text = (TextView) findViewById(R.id.pm25_text);
        comfortText = (TextView) findViewById(R.id.comfort_text);
        carWashText = (TextView) findViewById(R.id.car_wash_text);
        sportText = (TextView) findViewById(R.id.sport_text);
        bingPic = (ImageView) findViewById(R.id.bing_pic_img);
        cityTitleInNow = (TextView) findViewById(R.id.city_title_in_now);

        //typeface
        Utility.typeFaceStter(this, cityTitleInNow, "fonts/font2.ttf");
        Utility.typeFaceStter(this, degreeText, "fonts/font2.ttf");
        Utility.typeFaceStter(this, weatherIndoText, "fonts/font3.ttf");
        Utility.typeFaceStter(this, aqiText, "fonts/font3.ttf");
        Utility.typeFaceStter(this, pm25Text, "fonts/font3.ttf");
        Utility.typeFaceStter(this, comfortText, "fonts/font3.ttf");
        Utility.typeFaceStter(this, carWashText, "fonts/font3.ttf");
        Utility.typeFaceStter(this, sportText, "fonts/font3.ttf");


        //layout
        forecastLayout = (LinearLayout) findViewById(R.id.forecast_layout);
        weatherLayout = (ScrollView) findViewById(R.id.weather_layout);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        selectCountyButton = (Button) findViewById(R.id.select_county_Button);
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);


        //SharedPreference
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String weatherString = preferences.getString("weather", null);
        final String wether_id = preferences.getString("weather_id", null);
        final String weatherID;
        if (weatherString != null) {
            //Log.d(TAG, "onCreate: weatherString not null");
            Weather weather = Utility.handleWeatherResponse(weatherString);
            weatherID = weather.basic.weatherId;
            showWeatherInfo(weather);
        } else {
            weatherID = getIntent().getStringExtra("weather_id");
            weatherLayout.setVisibility(View.INVISIBLE);
            //Log.d(TAG, "onCreate: weatherString is null");
            requestWeather(weatherID);
        }

        //swiper fresh
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                requestWeather(wether_id);
            }
        });


        //Background picture
        String bingpic = preferences.getString("bing_pic", null);
        if (bingpic != null) {
            Glide.with(this).load(bingpic).into(bingPic);
        } else {
            loadPicture();
        }

        //select new county button
        selectCountyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    //set typeface
    public void initType() {

    }

    //load picture
    private void loadPicture() {
        String requestBingPic = "http://guolin.tech/api/bing_pic";
        HttpUtil.sendOKHttpRequest(requestBingPic, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //Log.d(TAG, "onResponse: " + 1);
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String bingPicture = response.body().string();
                Log.d(TAG, "onResponse: " + bingPicture);
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this).edit();
                editor.putString("bing_pic", bingPicture);
                editor.commit();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(WeatherActivity.this).load(bingPicture).into(bingPic);
                    }
                });
            }
        });
    }



    public void requestWeather(final String weatherId) {
        Log.d(TAG, "onCreate: weatherString is null" + weatherId);
        String weatherUrl = "http://guolin.tech/api/weather?cityid=" + weatherId + "&key=bf58d34d77464f359a5d232bf1ae2716";
        HttpUtil.sendOKHttpRequest(weatherUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(WeatherActivity.this, "无法获取天气信息,请检查网络", Toast.LENGTH_LONG).show();
                        //close refresh
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                final Weather weather = Utility.handleWeatherResponse(responseText);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (weather != null && "ok".equals(weather.status)) {
                            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this).edit();
                            editor.putString("weather", responseText);
                            editor.commit();
                            showWeatherInfo(weather);
                        } else {
                            Toast.makeText(WeatherActivity.this, "无法获取天气信息", Toast.LENGTH_SHORT).show();
                        }

                        // close refresh
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        });
    }

    private void showWeatherInfo (Weather weather) {
        String cityName = weather.basic.cityName;
        String updateTime = "更新时间:" + weather.basic.update.updateTime.split(" ")[1];
        String degree = weather.now.temperature + "℃";
        String weatherInfo = weather.now.more.info;

        //start service
        Intent intent = new Intent(this, upDateService.class);
        startService(intent);

        //Intent intent1 = new Intent(this, notificateService.class);
        //startService(intent1);

        //titleCity.setText(cityName);
        cityTitleInNow.setText(cityName);
        titleUpdateTime.setText(updateTime);
        degreeText.setText(degree);
        weatherIndoText.setText(weatherInfo);
        forecastLayout.removeAllViews();

        for (ForeCast foreCast : weather.foreCastList) {
            View view = LayoutInflater.from(this).inflate(R.layout.forecast_item, forecastLayout, false);
            TextView dateText = (TextView) view.findViewById(R.id.date_text);
            TextView infoText = (TextView) view.findViewById(R.id.info_text);
            TextView maxText = (TextView) view.findViewById(R.id.max_text);
            TextView minText = (TextView) view.findViewById(R.id.min_text);

            dateText.setText(foreCast.date);
            infoText.setText(foreCast.more.info);
            maxText.setText(foreCast.temperature.max);
            minText.setText(foreCast.temperature.min);
            forecastLayout.addView(view);
        }

        if (weather.aqi != null) {
            aqiText.setText(weather.aqi.city.aqi);
            pm25Text.setText(weather.aqi.city.pm25);
        }

        String comfort = "舒适度: " + weather.suggestion.comfort.info;
        String carWash = "洗车指数: " + weather.suggestion.carWash.info;
        String sport = "运动建议: " + weather.suggestion.sport.info;

        comfortText.setText(comfort);
        carWashText.setText(carWash);
        sportText.setText(sport);
        weatherLayout.setVisibility(View.VISIBLE);
    }
}

package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements ServiceCheck {

    private final int CURRENT_WEATHER = 0;
    private final int NEXT_FIVE_HOURS = 1;
    private final int AVERAGE_TWO_DAYS = 2;
    private final int WEEKLY_FORECAST = 3;
    private final int PAST_TEMPERATURE = 4;
    private DarkSky service;
    private String coordinates;
    private Button currentWeather;
    private Button coordinatesButton;
    private Button nextFiveButton;
    private Button pastTemperatureButton;
    private Button twoDayButton;
    private Button weekForecastButton;
    @Override
    protected void onResume() {
        super.onResume();
        coordinates = getIntent().getStringExtra("coordinates");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactivitypage_format);
        coordinates = "23.4326,-54.3956";
        currentWeather = (Button) findViewById(R.id.currentWeather);
        nextFiveButton = (Button) findViewById(R.id.nextFiveHours);
        pastTemperatureButton = (Button) findViewById(R.id.pastWeather);
        twoDayButton = (Button) findViewById(R.id.averageTwoDays);
        weekForecastButton = (Button) findViewById(R.id.nextWeek);
        // Location services
        coordinatesButton = (Button) findViewById(R.id.coordinatesButton);
        service = new DarkSky(this, CURRENT_WEATHER, "");
        service.refreshWeather(coordinates);
        enableButtons();
    }
    private void enableButtons() {
        currentWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToCurrentWeatherActivity();
            }
        });
        coordinatesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Location.class);
                startActivity(i);
            }
        });
        nextFiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, NextFiveHoursPage.class);
                i.putExtra("coordinates", coordinates);
                startActivity(i);
            }
        });
        twoDayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Next2DaysPage.class);
                i.putExtra("coordinates", coordinates);
                startActivity(i);
            }
        });
        weekForecastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SevenDayForecastPage.class);
                i.putExtra("coordinates", coordinates);
                startActivity(i);
            }
        });
        pastTemperatureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, TemperatureHistoryPage.class);
                i.putExtra("coordinates", coordinates);
                startActivity(i);
            }
        });
    }
    private void goToCurrentWeatherActivity(){
        Intent i = new Intent( this, CurrentWeatherPage.class);
        i.putExtra("coordinates", coordinates);
        startActivity(i);
    }
    @Override
    public void serviceWorked(CurrentWeatherType data) {
    }
    @Override
    public void serviceWorked(NextFiveHoursType data) { }
    @Override
    public void serviceWorked(TemperatureHistoryType data) { }
    @Override
    public void serviceWorked(Next2DaysType data) { }
    @Override
    public void serviceWorked(SevenDayForecastType data) { }
    @Override
    public void serviceFailed(Exception exception) {
        Toast.makeText(this, exception.getMessage(), Toast.LENGTH_LONG).show();
    }
}
package com.example.weatherapp;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
public class SevenDayForecastPage extends AppCompatActivity implements ServiceCheck
{
    private static final int WEEKLY_FORECAST = 3;
    private Button homeButton;
    private TextView weekText;
    private String coordinates;
    private DarkSky service;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sevendayforecastpage_format);
        coordinates = getIntent().getStringExtra("coordinates");
        homeButton = (Button) findViewById(R.id.homeButton);
        weekText = (TextView) findViewById(R.id.weeklyText);
        enableButtons();
        service = new DarkSky(this, WEEKLY_FORECAST,"");
        service.refreshWeather(coordinates);
    }
    private void enableButtons() {
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SevenDayForecastPage.this, MainActivity.class);
                i.putExtra("coordinates", coordinates);
                startActivity(i);
            }
        });
    }
    @Override
    public void serviceWorked(CurrentWeatherType weatherData) {
    }
    @Override
    public void serviceWorked(NextFiveHoursType weatherData) {
    }
    @Override
    public void serviceWorked(TemperatureHistoryType weatherData) {
    }
    @Override
    public void serviceWorked(Next2DaysType weatherData) {
    }
    @Override
    public void serviceWorked(SevenDayForecastType weatherData) {
        for(String string: weatherData.getTemperatures()){
            weekText.append(string + " F\n");
        }
    }
    @Override
    public void serviceFailed(Exception exception) {
    }
}

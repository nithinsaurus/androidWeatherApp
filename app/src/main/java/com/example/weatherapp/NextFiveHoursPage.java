package com.example.weatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
public class NextFiveHoursPage extends AppCompatActivity implements ServiceCheck
{
    private static final int NEXT_FIVE_HOURS = 1;
    private String coordinates;
    private TextView tempTV;
    private Button homeButton;
    private DarkSky service;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nextfivehourspage_format);
        coordinates = getIntent().getStringExtra("coordinates");
        tempTV = (TextView) findViewById(R.id.temperatures);
        homeButton = (Button) findViewById(R.id.homeButton);
        enableButtons();
        service = new DarkSky(this, NEXT_FIVE_HOURS, "");
        service.refreshWeather(coordinates);
    }
    private void enableButtons() {
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(NextFiveHoursPage.this, MainActivity.class);
                i.putExtra("coordinates", coordinates);
                startActivity(i);
            }
        });
    }
    @Override
    public void serviceWorked(CurrentWeatherType data) {
    }
    @Override
    public void serviceWorked(NextFiveHoursType data) {
        for(Double val: data.getTemperatures()){
            tempTV.append("Temperature: " + val.toString() + " F\n");
        }
    }
    @Override
    public void serviceWorked(TemperatureHistoryType data) {
    }
    @Override
    public void serviceWorked(Next2DaysType data) {
    }
    @Override
    public void serviceWorked(SevenDayForecastType data) {
    }
    @Override
    public void serviceFailed(Exception exception) {
        Toast.makeText(this, exception.getMessage(), Toast.LENGTH_LONG).show();
    }
}
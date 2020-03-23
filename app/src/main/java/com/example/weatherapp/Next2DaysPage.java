package com.example.weatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Locale;
public class Next2DaysPage extends AppCompatActivity implements ServiceCheck
{
    private static final int AVERAGE_TWO_DAYS = 2;
    private String coordinates;
    private Button homeButton;
    private TextView averageTemperature;
    private DarkSky service;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.next2dayspage_format);
        coordinates = getIntent().getStringExtra("coordinates");
        homeButton = (Button) findViewById(R.id.homeButton);
        averageTemperature = (TextView) findViewById(R.id.averageTemp);
        enableButtons();
        service = new DarkSky(this, AVERAGE_TWO_DAYS,"");
        service.refreshWeather(coordinates);
    }
    private void enableButtons() {
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                averageTemperature.setText("");
                Intent i = new Intent(Next2DaysPage.this, MainActivity.class);
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
    }
    @Override
    public void serviceWorked(TemperatureHistoryType data) {
    }
    @Override
    public void serviceWorked(com.example.weatherapp.Next2DaysType data) {
        averageTemperature.append(": " + String.format(Locale.ENGLISH, "%.04f", data.getAverageTemp()) + " F");
    }
    @Override
    public void serviceWorked(SevenDayForecastType data) {
    }
    @Override
    public void serviceFailed(Exception exception) {
        Toast.makeText(this, exception.getMessage(), Toast.LENGTH_LONG).show();
    }
}
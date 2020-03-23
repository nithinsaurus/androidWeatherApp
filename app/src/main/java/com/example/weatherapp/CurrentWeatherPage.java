package com.example.weatherapp;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class CurrentWeatherPage extends MainActivity implements ServiceCheck
{
    private static final int CURRENT_WEATHER = 0;
    private TextView tempTV;
    private TextView humidityTV;
    private TextView windspeedTV;
    private TextView precipTV;
    private DarkSky service;
    private String coordinates;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.currentweatherpage_format);
        coordinates = getIntent().getStringExtra("coordinates");
        tempTV = (TextView) findViewById(R.id.temperatureTextView);
        humidityTV = (TextView) findViewById(R.id.humidityTextView);
        windspeedTV = (TextView) findViewById(R.id.windspeedTextView);
        precipTV = (TextView) findViewById(R.id.precipitationTextView);
        service = new DarkSky(this, CURRENT_WEATHER, "");
        service.refreshWeather(coordinates);
        Button home = (Button) findViewById(R.id.homeButton);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToHome();
            }
        });
    }
    private void goToHome(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("coordinates", coordinates);
        startActivity(intent);
    }
    @Override
    public void serviceWorked(CurrentWeatherType data) {
        String[] coo = new String[4];
        coo[0] = "Temperature: " + data.getTemperature();
        coo[1] = "Humidity: " + data.getHumidity();
        coo[2] = "Windspeed: " + data.getWindspeed();
        coo[3] = "Precipitation: " + data.getPrecipitation();
        tempTV.setText(coo[0]);
        humidityTV.setText(coo[1]);
        windspeedTV.setText(coo[2]);
        precipTV.setText(coo[3]);
    }
    @Override
    public void serviceFailed(Exception exception) {
        Toast.makeText(this, exception.getMessage(), Toast.LENGTH_LONG).show();
    }
}

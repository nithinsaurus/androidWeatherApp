package com.example.weatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class TemperatureHistoryPage extends AppCompatActivity implements ServiceCheck
{
    private static final int PAST_TEMPERATURE = 4;

    private EditText yearInput;
    private EditText monthInput;
    private EditText dayInput;
    private EditText hourInput;

    private Button startButton;
    private Button homeButton;

    private TextView temp;
    private String coordinates;
    private DarkSky service;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.temperaturehistorypage_format);
        coordinates = getIntent().getStringExtra("coordinates");

        startButton = (Button) findViewById(R.id.startButton);
        homeButton = (Button) findViewById(R.id.homeButton);
        temp = (TextView) findViewById(R.id.tempTV);
        yearInput = (EditText) findViewById(R.id.yearInput);
        monthInput = (EditText) findViewById(R.id.monthInput);
        dayInput = (EditText) findViewById(R.id.dayInput);
        hourInput = (EditText) findViewById(R.id.hourInput);

        enableButtons(this);

    }

    private void enableButtons(final TemperatureHistoryPage callback) {
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String year = "";
                String month = "";
                String day = "";
                String hour = "";
                year = yearInput.getText().toString();
                month = monthInput.getText().toString();
                day = dayInput.getText().toString();
                hour = hourInput.getText().toString();
                String time = year + "-" + month + "-" + day + "T" + hour + ":00:00";
                //temp.setText(time);
                service = new DarkSky(callback, PAST_TEMPERATURE, time); //does weather refresh
                service.refreshWeather(coordinates);

            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TemperatureHistoryPage.this, MainActivity.class);
                i.putExtra("coordinates", coordinates);
                startActivity(i);
            }
        });
    }

    public EditText getDayInput() {
        return dayInput;
    }

    public EditText getHourInput() {
        return hourInput;
    }
    public EditText getMonthInput() {
        return monthInput;
    }

    public EditText getYearInput() {
        return yearInput;
    }

    public TextView getTemperature() {
        return temp;
    }

    @Override
    public void serviceWorked(CurrentWeatherType data) {

    }

    @Override
    public void serviceWorked(NextFiveHoursType data) {

    }

    @Override
    public void serviceWorked(com.example.weatherapp.TemperatureHistoryType data) {
        String text = "Temperature: " + data.getTemperature() + " F";
        temp.setText(text);
    }

    @Override
    public void serviceWorked(Next2DaysType data) {

    }

    @Override
    public void serviceWorked(SevenDayForecastType data) {

    }

    @Override
    public void serviceFailed(Exception exception) {

    }
}

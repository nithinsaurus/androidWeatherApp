package com.example.weatherapp;
import org.json.JSONObject;
public class CurrentWeatherType implements JSONGetter {
    private double temperature;
    private double humidity;
    private double windspeed;
    private double precip;
    @Override
    public void populate(JSONObject data){
        temperature = data.optDouble("temperature");
        humidity = data.optDouble("humidity");
        precip = data.optDouble("precipIntensity");
        windspeed = data.optDouble("windSpeed");
    }
    public double getPrecipitation()
    {
        return precip;
    }
    public double getWindspeed()
    {
        return windspeed;
    }
    public double getTemperature()
    {
        return temperature;
    }
    public double getHumidity()
    {
        return humidity;
    }
}

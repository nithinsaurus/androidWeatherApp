package com.example.weatherapp;
import org.json.JSONObject;

public class TemperatureHistoryType implements JSONGetter {
    private Double temp;
    @Override
    public void populate(JSONObject data)
    {
        temp = data.optDouble("temperature");
    }

    public Double getTemperature() { return temp; }

}

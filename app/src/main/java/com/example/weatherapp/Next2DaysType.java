package com.example.weatherapp;
import org.json.JSONArray;
import org.json.JSONObject;

public class Next2DaysType implements JSONGetter {
    private JSONArray hours;
    private double averageTemp;
    @Override
    public void populate(JSONObject data) {
        double sum = 0;
        hours = data.optJSONArray("data");
        for(int i = 0; i < 48; i++){
            JSONObject hour = hours.optJSONObject(i);
            Double temperature = hour.optDouble("temperature");
            sum += temperature;
        }
        averageTemp = sum / 48;
    }
    public double getAverageTemp()
    {
        return averageTemp;
    }
}

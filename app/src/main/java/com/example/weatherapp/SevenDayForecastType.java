package com.example.weatherapp;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
public class SevenDayForecastType implements JSONGetter
{
    private ArrayList<String> temps;
    @Override
    public void populate(JSONObject data) {
        temps = new ArrayList<String>();
        JSONArray dates = data.optJSONArray("data");
        for(int i = 0; i < 5; i++){
            JSONObject date = dates.optJSONObject(i);
            temps.add("low: " + date.optDouble("temperatureLow") + " high: " + date.optDouble("temperatureHigh"));
        }
    }

    public ArrayList<String> getTemperatures()
    {
        return temps;
    }
}

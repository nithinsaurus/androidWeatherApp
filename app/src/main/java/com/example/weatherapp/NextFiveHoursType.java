package com.example.weatherapp;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
public class NextFiveHoursType implements JSONGetter{
    private JSONArray times;
    private ArrayList<Double> temperatures;
    @Override
    public void populate(JSONObject data) {
        temperatures = new ArrayList<Double>();
        times = data.optJSONArray("data");
        for(int i = 0; i <= 4; i++){
            JSONObject time = times.optJSONObject(i);
            temperatures.add(time.optDouble("temperature"));
        }
    }
    public ArrayList<Double> getTemperatures() {
        return temperatures;
    }
}

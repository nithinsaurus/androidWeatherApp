package com.example.weatherapp;

import android.os.AsyncTask;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import com.example.weatherapp.CurrentWeatherType;
import com.example.weatherapp.NextFiveHoursType;
import com.example.weatherapp.TemperatureHistoryType;
import com.example.weatherapp.Next2DaysType;
import com.example.weatherapp.SevenDayForecastType;
public class DarkSky {
    private ServiceCheck callback;
    private String location;
    private Exception error;
    private int code;
    private String date;
    public DarkSky(ServiceCheck callback, int code, String date){
        this.callback = callback;
        this.code = code;
        this.date = date;
    }
    public String getLocation(){
        return location;
    }
    public void refreshWeather(final String location){
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... strings) {
                String dataPoint;
                //String YQL = String.format("");
                if(code <= 3){
                    dataPoint = String.format("https://api.darksky.net/forecast/def9e437b2976705e1ae57a58211dd2a/" + location);

                } else {
                    dataPoint = String.format("https://api.darksky.net/forecast/def9e437b2976705e1ae57a58211dd2a/" + location + "," + date);

                }
                try {
                    URL url = new URL(dataPoint);
                    URLConnection connection = url.openConnection();
                    InputStream inputStream = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder result = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }
                    return result.toString();
                } catch (MalformedURLException e) {
                    error = e;
                } catch (IOException e) {
                    error = e;
                }
                return null;
            }
            @Override
            protected void onPostExecute(String string){

                if(string == null && error != null) {
                    callback.serviceFailed(error);
                    return;
                }

                try {
                    JSONObject data = new JSONObject(string);
                    switch (code){
                        case 0:
                            CurrentWeatherType currently = new CurrentWeatherType();
                            currently.populate(data.optJSONObject("currently"));
                            callback.serviceWorked(currently);
                            break;
                        case 1:
                            NextFiveHoursType nextFive = new NextFiveHoursType();
                            nextFive.populate(data.optJSONObject("hourly"));
                            callback.serviceWorked(nextFive);
                            break;
                        case 2:
                            Next2DaysType twoDay = new Next2DaysType();
                            twoDay.populate(data.optJSONObject("hourly"));
                            callback.serviceWorked(twoDay);
                            break;
                        case 3:
                            SevenDayForecastType weekForecast = new SevenDayForecastType();
                            weekForecast.populate(data.optJSONObject("daily"));
                            callback.serviceWorked(weekForecast);
                            break;
                        case 4:
                            TemperatureHistoryType pastTemperature = new TemperatureHistoryType();
                            pastTemperature.populate(data.optJSONObject("currently"));
                            callback.serviceWorked(pastTemperature);
                    }

                } catch (JSONException e) {
                    callback.serviceFailed(e);
                }
            }
        }.execute(location);
    }
    public class LocationWeatherException extends Exception{
        public LocationWeatherException(String detailMessage){
            super(detailMessage);
        }
    }
}

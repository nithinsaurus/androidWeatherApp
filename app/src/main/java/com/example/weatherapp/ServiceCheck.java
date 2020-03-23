package com.example.weatherapp;

public interface ServiceCheck {
    void serviceWorked(CurrentWeatherType data);
    void serviceWorked(NextFiveHoursType data);
    void serviceWorked(TemperatureHistoryType data);
    void serviceWorked(Next2DaysType data);
    void serviceWorked(SevenDayForecastType data);
    void serviceFailed(Exception exception);
}

package com.example.weatherapp;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import java.util.Locale;
public class GPS extends Service
{
    private LocationListener locListener;
    private LocationManager locManager;
    public GPS() {
    }
    @Override
    public IBinder onBind(Intent i) {
        throw new UnsupportedOperationException("onBind not yet coded");
    }
    @SuppressLint("MissingPermission")
    @Override
    public void onCreate() {
        locListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Intent i = new Intent("location_update");
                String coordinates = String.format(Locale.ENGLISH, "%.4f,%.4f", location.getLatitude(), location.getLongitude());
                i.putExtra("coordinates", coordinates);
                sendBroadcast(i);
            }
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }
            @Override
            public void onProviderEnabled(String provider) {
            }
            @Override
            public void onProviderDisabled(String provider) {
                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        };
        locManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 0, locListener);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if(locManager != null){
            locManager.removeUpdates(locListener);
        }
    }
}

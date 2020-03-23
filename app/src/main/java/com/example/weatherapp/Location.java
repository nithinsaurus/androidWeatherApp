package com.example.weatherapp;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
public class Location extends AppCompatActivity
{
    private Button startButton, homeButton;
    private TextView coordinateTextView;
    private BroadcastReceiver bCaster;
    private String coordinates;
    @Override
    protected void onResume() {
        super.onResume();
        if(bCaster == null){
            bCaster = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    coordinateTextView.append("\n " + intent.getExtras().get("coordinates"));
                    coordinates = intent.getStringExtra("coordinates");
                }
            };
        }
        registerReceiver(bCaster, new IntentFilter("location_update"));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.locationpage_format);
        coordinates = "42.3601,-71.0589";//default
        startButton = (Button) findViewById(R.id.button);
        homeButton = (Button) findViewById(R.id.homeButton);
        coordinateTextView = (TextView) findViewById(R.id.coordinatesTextView);
        if(!runtime_permissions()){
            enable_buttons();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(bCaster != null){
            unregisterReceiver(bCaster);
        }
    }
    private void enable_buttons() {
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), GPS.class);
                startService(i);
            }
        });
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), GPS.class);
                stopService(i);
                i = new Intent(Location.this, MainActivity.class);
                i.putExtra("coordinates", coordinates);
                startActivity(i);
            }
        });
    }
    private boolean runtime_permissions() {
        if(Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
            return true;
        }
        return false;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 100){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                enable_buttons();
            } else {
                runtime_permissions();
            }
        }
    }
}

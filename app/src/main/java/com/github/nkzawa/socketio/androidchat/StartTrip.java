package com.github.nkzawa.socketio.androidchat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.github.nkzawa.socketio.androidchat.Model.DriverRequestData;
import com.google.gson.Gson;

public class StartTrip extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_trip);
        TextView displayArea = (TextView) findViewById(R.id.displayTripInfo);
        DriverRequestData tripData = (DriverRequestData) getIntent().getSerializableExtra("trip_data");

        Gson gson = new Gson();
        displayArea.setText(gson.toJson(tripData));
    }
}

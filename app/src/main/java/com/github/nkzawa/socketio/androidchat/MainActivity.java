package com.github.nkzawa.socketio.androidchat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.github.nkzawa.socketio.androidchat.Model.DriverInfo;
import com.github.nkzawa.socketio.androidchat.Model.DriverRequestData;
import com.github.nkzawa.socketio.androidchat.Model.PassengerRequest;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;


public class MainActivity extends AppCompatActivity {

    Socket mSocket;
    private String TAG = "MainActivity";
    private String passengerKey;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressDialog = new ProgressDialog(this);

        ChatApplication app = (ChatApplication) getApplication();
        mSocket = app.getSocket();
        mSocket.on(Socket.EVENT_CONNECT,onConnect);
        mSocket.on(Socket.EVENT_DISCONNECT,onDisconnect);
        mSocket.on("request-channel", driverAcceptRequest);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSocket.disconnect();
    }


    public void connect(View view) {
        mSocket.connect();
    }

    public void disconnect(View view) {
        mSocket.disconnect();
    }

    public void searchForDriver(View view){
        if (mSocket.connected()){
            progressDialog.setMessage("Searching for a near by driver");
            progressDialog.show();
            Gson gson = new Gson();
            mSocket.emit("request-channel", gson.toJson(getPassengerRequest()));
        }else{
            Toast.makeText(getApplicationContext(), "Please Connect socket", Toast.LENGTH_SHORT).show();
        }

    }

    private Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(mSocket.connected()) {
                        Toast.makeText(getApplicationContext(), R.string.connect, Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    };

    private Emitter.Listener onDisconnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.i(TAG, "diconnected");
                    Toast.makeText(getApplicationContext(), R.string.disconnect, Toast.LENGTH_LONG).show();
                }
            });
        }
    };

    private Emitter.Listener onRequestSent = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject object = (JSONObject) args[0];
                    Gson gson = new Gson();
                    Toast.makeText(getApplicationContext(), "Request sent", Toast.LENGTH_SHORT).show();
                }
            });
        }
    };

    private Emitter.Listener driverAcceptRequest = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JsonObject object = (JsonObject) args[0];
                    Gson gson = new Gson();
                    DriverRequestData data = gson.fromJson(object, DriverRequestData.class);
                    if (data.passengerKey.equals(passengerKey)){
                        progressDialog.dismiss();
                        Intent intent = new Intent(getApplicationContext(), StartTrip.class);
                        intent.putExtra("trip_data" , data);
                        startActivity(intent);
                    }
                }
            });
        }
    };

    private PassengerRequest getPassengerRequest(){
        PassengerRequest request = new PassengerRequest();
        request.key = "40f62535-0ab2-4ea4-88a3-5ebec394fec1";
        request.passengerDestination = "Otigba Enugu";
        request.passengerDestinationLat = 6.441320857992139;
        request.passengerDestinationLong = 7.441320857992139;
        request.passengerName = "Okoro ugochukwu";
        request.passengerPhoneNumber = "08135323878";
        request.passengerPickUpLat = 6.441320857992139;
        request.passengerPickUpLong = 7.441320857992139;
        request.passengerPaymentType = "CASH";
        request.vehicleType = "";
        request.passengerPickUpName = "Garden Avenue Enugu";
        request.status = "NOT_ACCEPTED";
        request.passengerDeviceType = "";
        request.driverKey = "";
        return request;
    }

}

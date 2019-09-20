package com.github.nkzawa.socketio.androidchat.Model;

import java.io.Serializable;

public class PassengerRequest implements Serializable {
    public String key;
    public String passengerDestination;
    public double passengerDestinationLat;
    public double passengerDestinationLong;
    public String passengerName;
    public String passengerPhoneNumber;
    public double passengerPickUpLat;
    public double passengerPickUpLong;
    public String passengerPaymentType;
    public String passengerPickUpName;
    public String status;
    public String passengerDeviceType;
    public String driverKey;
    public String vehicleType;
}

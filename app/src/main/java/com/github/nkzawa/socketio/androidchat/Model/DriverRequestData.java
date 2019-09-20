package com.github.nkzawa.socketio.androidchat.Model;

import java.io.Serializable;

public class DriverRequestData implements Serializable {
    public String key;
    public String passengerDestination;
    public Double passengerDestinationLat;
    public Double passengerDestinationLong;
    public String passengerKey;
    public String passengerName;
    public String passengerPhoneNumber;
    public Double passengerPickUpLat;
    public Double passengerPickUpLong;
    public String passengerPaymentType;
    public String passengerPickUpName;
    public String status;
    public Double driverLatitude;
    public Double driverLongitude;
    public DriverInfo driverDetail;
}

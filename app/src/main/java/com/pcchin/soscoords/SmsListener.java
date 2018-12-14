package com.pcchin.soscoords;

import android.Manifest;
import android.arch.persistence.room.Room;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;

import com.pcchin.soscoords.contactlist.ContactListDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class SmsListener extends BroadcastReceiver {
    // Copied from https://github.com/KAPLANDROID/SmsForwarderForAndroid
    // Combined with https://stackoverflow.com/questions/1973071/broadcastreceiver-sms-received
    Context mContext;

    @Override
    public void onReceive(final Context context, Intent intent) {

        mContext = context;
        if (Objects.equals(intent.getAction(), "android.provider.Telephony.SMS_RECEIVED")) {
            // ****** DON'T MODIFY STARTING HERE ****** //
            Bundle bundle = intent.getExtras(); // ---get the SMS message
            // passed
            // in---
            SmsMessage[] msgs;
            String msg_from = "";
            if (bundle != null) {
                // ---retrieve the SMS message received---
                try {

                    Object[] pdus = (Object[]) bundle.get("pdus");
                    assert pdus != null;
                    msgs = new SmsMessage[pdus.length];

                    StringBuilder message = new StringBuilder("_");

                    for (int i = 0; i < msgs.length; i++) {
                        msgs[i] = SmsMessage
                                .createFromPdu((byte[]) pdus[i]);
                        msg_from = msgs[i].getOriginatingAddress();
                        String msgBody = msgs[i].getMessageBody();

                        message.append(msgBody);

                    }
                    // ****** DON'T MODIFY ENDING HERE ****** //

                    // Check sender matches saved number
                    List<String> savedContactList = updateNumberList();
                    SharedPreferences appKeys = mContext.getSharedPreferences("com.pcchin.soscoords.shared_pref_key", Context.MODE_PRIVATE);
                    String secretCode = appKeys.getString("secret_code_input", null);
                    for(int i=0; i < savedContactList.size(); i++) {
                        // Check if message matches
                        if (Objects.equals(savedContactList.get(i), msg_from) && Objects.equals(secretCode, message.toString())) {
                            // Get coords
                            Location currentLoc = getCoords();
                            String response = "GPS coordinates is not available at this time. Please try again later.";
                            if (currentLoc != null) {
                                response = currentLoc.toString();
                            }
                            SmsManager smsManager = SmsManager.getDefault();
                            smsManager.sendTextMessage(msg_from, null, response, null, null);
                            break;
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }

    @NonNull
    private List<String> updateNumberList() {
        // Access database
        ContactListDatabase contactListDatabase = Room.databaseBuilder(mContext, ContactListDatabase.class, "current_contact_list").build();
        List<String> updateNumberString = contactListDatabase.daoAccess().getAllContactsNum();
        contactListDatabase.close();

        // Convert String back to array
        List<String> responseArray = new ArrayList<>();
        //Merge all arrays into one
        for (int i = 0; i < updateNumberString.size(); i++) {
            // Convert current phone number stored in contacts (String to Array)
            List<String> responseArrayCurrent = new ArrayList<>(Arrays.asList(updateNumberString.get(i).split("; ")));
            // Append all values in current array to final array
            responseArray.addAll(responseArrayCurrent);
        }
        return responseArray;
    }

    private Location getCoords() {
        // Check GPS Permission
        Location locationGPS = null;
        Location locationNet = null;
        LocationManager mLocationManager = (LocationManager)mContext.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(mContext.getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationGPS = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            locationNet = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }

        long GPSLocationTime = 0;
        if (null != locationGPS) {
            GPSLocationTime = locationGPS.getTime();
        }
        long NetLocationTime = 0;
        if (null != locationNet) {
            NetLocationTime = locationNet.getTime();
        }
        if (0 < GPSLocationTime - NetLocationTime) {
            return locationGPS;
        } else {
            return locationNet;
        }
    }
}

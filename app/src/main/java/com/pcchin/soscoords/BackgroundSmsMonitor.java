package com.pcchin.soscoords;

import android.app.IntentService;
import android.content.Intent;
import android.widget.Toast;

public class BackgroundSmsMonitor extends IntentService {
    // TODO: Start on Boot

    public BackgroundSmsMonitor() {
        super("BackgroundSmsMonitor");
    }

    @Override
    protected void onHandleIntent(Intent workIntent) {
        new SmsListener();
        Toast.makeText(this, "Sms Coords started", Toast.LENGTH_SHORT).show();
    }
}
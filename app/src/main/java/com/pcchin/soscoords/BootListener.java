package com.pcchin.soscoords;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import java.util.Objects;

public class BootListener extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Objects.equals(intent.getAction(), Intent.ACTION_BOOT_COMPLETED)) {
            // Start SmsListener
            IntentFilter filter = new IntentFilter();
            filter.addAction("android.provider.Telephony.SMS_RECEIVED");
            filter.setPriority(999);
            SmsListener receiver = new SmsListener();
            context.registerReceiver(receiver, filter);
        }
    }
}

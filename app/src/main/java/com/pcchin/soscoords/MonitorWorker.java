package com.pcchin.soscoords;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class MonitorWorker extends Worker {
    public MonitorWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        // Start BootListener
        IntentFilter filter1 = new IntentFilter();
        filter1.addAction(Intent.ACTION_BOOT_COMPLETED);
        filter1.setPriority(999);
        BootListener receiver1 = new BootListener();
        getApplicationContext().registerReceiver(receiver1, filter1);

        // Start SmsListener
        IntentFilter filter2 = new IntentFilter();
        filter2.addAction("android.provider.Telephony.SMS_RECEIVED");
        filter2.setPriority(999);
        SmsListener receiver2 = new SmsListener();
        getApplicationContext().registerReceiver(receiver2, filter2);

        return Result.success();
    }
}

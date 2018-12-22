package com.pcchin.soscoords;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import java.util.Objects;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class SmsMonitorService extends Service {

    String CHANNEL_ID = "default";
    BroadcastReceiver callExplicitReceiver;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Log.d("Testing", "Receiver started");
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.setPriority(2147483647);
        intentFilter.addAction("android.intent.action.PHONE_STATE");
        this.callExplicitReceiver = new SmsListener();
        registerReceiver(callExplicitReceiver, intentFilter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int CALL_SERVICE_REQUEST_CODE = 134;
        int CALL_NOTIFICATION_ID = 1000;

        if (Objects.equals(intent.getAction(), "START")) {
            createNotificationChannel();
            Intent callServiceNotificationIntent = new Intent(this, MainActivity.class);
            callServiceNotificationIntent.setFlags(
                    Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent
                    .getActivity(this, CALL_SERVICE_REQUEST_CODE,
                            callServiceNotificationIntent, Intent.FILL_IN_ACTION);

            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle(getString(R.string.app_name))
                    .setContentText(getString(R.string.app_name) + " is running")
                    .setSmallIcon(R.mipmap.ic_launcher_round)
                    .setContentIntent(pendingIntent)
                    .setOngoing(true)
                    .build();
            startForeground(CALL_NOTIFICATION_ID, notification);
        }
        return flags;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(callExplicitReceiver);
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.app_name);
            String description = getString(R.string.app_name) + " is running";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}

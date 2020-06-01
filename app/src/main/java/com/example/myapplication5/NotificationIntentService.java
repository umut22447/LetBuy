package com.example.myapplication5;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.Context;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class NotificationIntentService extends IntentService {

    public static final int NOTIFICATION_ID = 1;

    public NotificationIntentService(){
        super("NotificationIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(android.R.drawable.sym_def_app_icon)
                .setContentTitle(getString(R.string.successfully_purchased))
                .setContentText(getString(R.string.successfully_purchased))
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(NOTIFICATION_ID,builder.build());
    }
}

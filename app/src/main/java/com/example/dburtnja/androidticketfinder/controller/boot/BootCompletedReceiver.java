package com.example.dburtnja.androidticketfinder.controller.boot;

import android.app.AlarmManager;
import android.app.ListActivity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.widget.Toast;

import com.example.dburtnja.androidticketfinder.MyService;
import com.example.dburtnja.androidticketfinder.model.DbHelper;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

/**
 * Created by denys on 9/8/17.
 */

public class BootCompletedReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        PendingIntent   pendingIntent;
        AlarmManager    alarmManager;

        if (!intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED))
            return;

        pendingIntent = MyService.getServicePendingIntent(context);
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime(), 60000, pendingIntent);
    }

}


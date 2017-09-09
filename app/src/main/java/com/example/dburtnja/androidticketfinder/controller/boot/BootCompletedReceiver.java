package com.example.dburtnja.androidticketfinder.controller.boot;

import android.app.ListActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.dburtnja.androidticketfinder.MyService;
import com.example.dburtnja.androidticketfinder.model.DbHelper;

/**
 * Created by denys on 9/8/17.
 */

public class BootCompletedReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent      serviceIntent;

        if (!intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED))
            return;

            serviceIntent = new Intent(context, MyService.class);
            serviceIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }

    }
}

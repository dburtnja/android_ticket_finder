package com.example.dburtnja.androidticketfinder.controller.boot;

import android.app.ListActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by denys on 9/8/17.
 */

public class BootCompletedReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent  serviceIntent;

        serviceIntent = new Intent(context, ListActivity.class);
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED))
            Toast.makeText(context, "LLLLLLLLLLLLLLLL", Toast.LENGTH_LONG).show();
    }
}

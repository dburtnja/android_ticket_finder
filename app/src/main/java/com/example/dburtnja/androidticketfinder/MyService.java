package com.example.dburtnja.androidticketfinder;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.example.dburtnja.androidticketfinder.model.DbHelper;
import com.example.dburtnja.androidticketfinder.model.Passenger;
import com.google.gson.Gson;

import java.util.List;


public class MyService extends Service {
    public static final int     REQUEST_CODE = 1;

    private Gson    gson;

    public MyService() {
        gson = new Gson();
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        Thread          thread;
        DbHelper        dbHelper;
        List<Passenger> passengers;

        dbHelper = new DbHelper(this);
        passengers = dbHelper.getPassengerList();
        thread = new Thread(){
            @Override
            public void run() {
                runFinder(passengers);
            }
        };
        if (passengers.size() > 0)
            thread.start();
        else
            stopFinder(this);
        return super.onStartCommand(intent, flags, startId);
    }

    private void stopFinder(Context context) {
        ((AlarmManager)getSystemService(ALARM_SERVICE)).cancel(getServicePendingIntent(context));
        Toast.makeText(context, R.string.stopFinderMessage, Toast.LENGTH_LONG).show();
    }

    private void runFinder(List<Passenger> passengers) {

//                search_ticket = new Search_ticket(ticket, MyService.this);
//                ticket.pendingIntent = PendingIntent.getService(MyService.this, ticket.pendingCode, intent, 0);
//                search_ticket.checkForTrain();
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public static PendingIntent getServicePendingIntent(Context context) {
        Intent          intent;
        PendingIntent   pendingIntent;

        intent = new Intent(context, MyService.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        pendingIntent = PendingIntent.getActivity(context, REQUEST_CODE, intent, 0);
        return pendingIntent;
    }

}

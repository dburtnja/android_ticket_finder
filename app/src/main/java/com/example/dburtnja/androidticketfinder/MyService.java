package com.example.dburtnja.androidticketfinder;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.google.gson.Gson;


public class MyService extends Service {
    public static final int     REQUEST_CODE = 1;

    private Gson    gson;

    public MyService() {
        gson = new Gson();
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        Thread          thread;

        thread = new Thread(){
            @Override
            public void run() {
                Ticket          ticket;
                Search_ticket   search_ticket;

                ticket = gson.fromJson(intent.getStringExtra("ticket"), Ticket.class);
                ticket.setSimpleFormats();
                search_ticket = new Search_ticket(ticket, MyService.this);
                ticket.pendingIntent = PendingIntent.getService(MyService.this, ticket.pendingCode, intent, 0);
                search_ticket.checkForTrain();
            }
        };
        thread.start();
        Log.d("end", "rservice!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public static void getServicePandingIntent(Context context) {
        Intent          intent;
        PendingIntent   pendingIntent;

        intent = new Intent(context, MyService.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        pendingIntent = PendingIntent.getActivities(context, REQUEST_CODE, intent, )

    }

}

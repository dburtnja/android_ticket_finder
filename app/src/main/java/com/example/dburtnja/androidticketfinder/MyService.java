package com.example.dburtnja.androidticketfinder;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.example.dburtnja.androidticketfinder.Search.Search_ticket;
import com.example.dburtnja.androidticketfinder.TicketInfo.Ticket;
import com.google.gson.Gson;

import java.net.CookieManager;

public class MyService extends Service {
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
                search_ticket.checkForTrain();
            }
        };
        thread.start();
        
       // ticket.error = false;
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}

package com.example.dburtnja.androidticketfinder.Search;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;


/**
 * Created by denys on 01.08.17.
 */

public class MyNotification {
    private NotificationCompat.Builder  builder;
    private NotificationManager         notificationManager;

    public MyNotification(Context context) {
        this.builder = new NotificationCompat.Builder(context);
        this.notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        builder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(android.R.drawable.btn_star)
                .setContentTitle(ticket.getStationFrom().getTitle() + "=>" + ticket.getStationTill().getTitle())
                .setContentText(ticket.status)
                .setContentInfo(ticket.dateFromStart.getStrDate());
    }

    public void postNotification(Ticket ticket){
        builder.setWhen(System.currentTimeMillis())
                .setContentTitle(ticket.getStationFrom().getTitle() + "=>" + ticket.getStationTill().getTitle())
                .setContentText(ticket.status);
        notificationManager.notify(1, builder.build());
    }
}

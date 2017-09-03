package com.example.dburtnja.androidticketfinder.model;

import com.example.dburtnja.androidticketfinder.TicketInfo.TicketDate;

/**
 * Created by denys on 9/3/17.
 */

public class Date {
    private long    date;

    public Date(long date) {
        this.date = date;
    }

    public long getDate() {
        return date;
    }

    public long getDateMS(){
        return date / 1000;
    }
}

package com.example.dburtnja.androidticketfinder.model;

import android.widget.TextView;

import com.example.dburtnja.androidticketfinder.view.DateViewAreaImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by denys on 9/3/17.
 */

public class Date {
    private long    date;

    public Date(TextView date, TextView time) {
        SimpleDateFormat    dateAndTimeFormat;
        String              dateAndTime;


        dateAndTime = date.getText().toString() + time.getText().toString();
        dateAndTimeFormat = new SimpleDateFormat(
                DateViewAreaImpl.VIEW_DATE_FORMAT + DateViewAreaImpl.VIEW_TIME_FORMAT,
                Locale.getDefault());
        try {
            this.date = dateAndTimeFormat.parse(dateAndTime).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public long getDate() {
        return date;
    }

    public long getDateMS(){
        return date / 1000;
    }
}

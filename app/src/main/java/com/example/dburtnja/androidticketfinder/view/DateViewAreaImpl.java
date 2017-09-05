package com.example.dburtnja.androidticketfinder.view;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.TextView;

import com.example.dburtnja.androidticketfinder.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by denys on 9/4/17.
 */

public class DateViewAreaImpl implements DateViewArea{
    public static final String VIEW_DATE_FORMAT = "Дата: dd.MM.yyyy";
    public static final String VIEW_TIME_FORMAT = "Час: HH:mm";

    private DateView    dateStart;
    private DateView    dateEnd;

    public DateViewAreaImpl(Activity activity) {
        long                dateMS;

        dateMS = getDateInMS(Calendar.getInstance().getTime());
        this.dateStart = new DateView(activity, R.id.dateFromStart, R.id.timeFromStart, dateMS, 0);
        this.dateEnd = new DateView(activity, R.id.dateFromEnd, R.id.timeFromEnd, dateMS, DateUtils.DAY_IN_MILLIS);
    }

    @Override
    public void setOnClickListener(Context context) {
        View.OnClickListener    dateListener;
        View.OnClickListener    timeListener;

        dateListener = onDateClick(context);
        timeListener = onTimeClick(context);
        this.dateStart.setOnClickListener(dateListener);
        this.timeStart.setOnClickListener(timeListener);
        this.dateEnd.setOnClickListener(dateListener);
        this.timeEnd.setOnClickListener(timeListener);
    }



    /**
     *This method take away hours, seconds, and milliseconds from date
     * to return only date in long type
     * For example  date = new Date().toString //01.02.2017 21:22
     * ret:  long = new Date('return value').toString // 01.02.2017 00:00
     *
     * @param date to convert in milliseconds
     * @return only date in milliseconds long
     */
    private long getDateInMS(Date date) {
        SimpleDateFormat    dateFormat;
        String              onlyDate;

        dateFormat = new SimpleDateFormat(VIEW_DATE_FORMAT, Locale.getDefault());
        onlyDate = dateFormat.format(date);
        try {
            date = dateFormat.parse(onlyDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return -1;
        }
        return date.getTime();
    }

    @Override
    public long getTimeStart() {
        return this.dateStart.getValue();
    }

    @Override
    public long getTimeEnd() {
        return this.dateEnd.getValue();
    }

//    public void setTimeFromStart(long time) {
//        SimpleDateFormat dateFormat;
//        SimpleDateFormat    timeFormat;
//
//        dateFormat = new SimpleDateFormat(VIEW_DATE_FORMAT, Locale.getDefault());
//        timeFormat = new SimpleDateFormat(VIEW_TIME_FORMAT, Locale.getDefault());
//        this.dateStart.setText(dateFormat.format(time));
//        this.timeStart.setText(timeFormat.format(time));
//    }
//
//    public void setTimeFromEnd(long time) {
//        SimpleDateFormat    dateFormat;
//        SimpleDateFormat    timeFormat;
//
//        dateFormat = new SimpleDateFormat(VIEW_DATE_FORMAT, Locale.getDefault());
//        timeFormat = new SimpleDateFormat(VIEW_TIME_FORMAT, Locale.getDefault());
//        this.dateEnd.setText(dateFormat.format(time));
//        this.timeEnd.setText(timeFormat.format(time));
//    }
}

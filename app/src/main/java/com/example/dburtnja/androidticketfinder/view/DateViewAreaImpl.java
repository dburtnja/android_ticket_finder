package com.example.dburtnja.androidticketfinder.view;

import android.app.Activity;
import android.content.Context;
import android.text.format.DateUtils;

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

    DateViewAreaImpl(Activity activity) {
        Calendar    calendarStart;
        Calendar    calendarEnd;

        calendarStart = Calendar.getInstance();
        calendarEnd = Calendar.getInstance();
        calendarStart.set(Calendar.HOUR_OF_DAY, 0);
        calendarStart.set(Calendar.MINUTE, 0);
        calendarEnd.set(Calendar.HOUR_OF_DAY, 23);
        calendarEnd.set(Calendar.MINUTE, 59);
        this.dateStart = new DateView(activity, R.id.dateFromStart, R.id.timeFromStart, calendarStart);
        this.dateEnd = new DateView(activity, R.id.dateFromEnd, R.id.timeFromEnd, calendarEnd);
    }

    @Override
    public void setOnClickListeners(Context context) {
        dateStart.onClickListeners(context, this);
        dateEnd.onClickListeners(context, this);
    }

    @Override
    public void notifyValuesHasChanged(DateView changed) {
        if (dateStart.getValue() >= dateEnd.getValue()) {
            if (changed == this.dateStart)
                dateEnd.setEndValues(this.dateStart);
            else
                dateStart.setStartValues(this.dateEnd);
        }
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
}

package com.example.dburtnja.androidticketfinder.view;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by denys on 9/5/17.
 */

public class DateView {
    private TextView    date;
    private TextView    time;
    private long        dateValue;
    private long        timeValue;

    public DateView(Activity activity, int dateId, int timeId, long dateValue, long timeValue) {
        this.date = activity.findViewById(dateId);
        this.time = activity.findViewById(timeId);
        this.dateValue = dateValue;
        this.timeValue = timeValue;
        applyValue();
    }

    public void showDatePicker(Context context) {
        DatePickerDialog    datePickerDialog;
        Calendar            calendar;

        calendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(context, 0, (datePicker, year, month, day) -> {
            Calendar    currentDate;

            currentDate = Calendar.getInstance();
            currentDate.set(year, month, day);
            this.dateValue = currentDate.getTimeInMillis();
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    public void showTimePicker(Context context) {
        TimePickerDialog timePickerDialog;

        timePickerDialog = new TimePickerDialog(context, 0, (timePicker, h, m) -> {
            Calendar    currentDate;

            currentDate = Calendar.getInstance();
            currentDate.set(0, 0, 0, h, m);
            this.timeValue = currentDate.getTimeInMillis();
        }, 0, 0, true);
        timePickerDialog.show();
    }

    /**
     * This method takes current values of time and date in object parameters
     * and post it to the view in readable format
     */
    public void applyValue() {
        SimpleDateFormat    dateFormat;
        SimpleDateFormat    timeFormat;

        dateFormat = new SimpleDateFormat(DateViewAreaImpl.VIEW_DATE_FORMAT, Locale.getDefault());
        timeFormat = new SimpleDateFormat(DateViewAreaImpl.VIEW_TIME_FORMAT, Locale.getDefault());
        this.date.setText(dateFormat.format(new Date(this.dateValue)));
        this.time.setText(timeFormat.format(new Date(this.timeValue)));
    }

    public long getValue() {
        return this.dateValue + this.timeValue;
    }

    public TextView getDateView() {
        return date;
    }

    public TextView getTimeView() {
        return time;
    }
}

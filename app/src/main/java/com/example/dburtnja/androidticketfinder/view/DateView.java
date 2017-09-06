package com.example.dburtnja.androidticketfinder.view;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by denys on 9/5/17.
 * This object contains values of time and date in Date View Area,
 * and have methods that allows to have always valid DATE value
 */

class DateView {
    private TextView    date;
    private TextView    time;
    private Calendar    calendar;

    DateView(Activity activity, int dateId, int timeId, Calendar calendar) {
        this.date = activity.findViewById(dateId);
        this.time = activity.findViewById(timeId);
        this.calendar = calendar;
        applyValue();
    }

    void setEndValues(DateView startDate){
        this.calendar = (Calendar) startDate.calendar.clone();
        this.calendar.set(Calendar.HOUR_OF_DAY, 23);
        this.calendar.set(Calendar.MINUTE, 59);
        applyValue();
    }

    void setStartValues(DateView endDate) {
        this.calendar = (Calendar) endDate.calendar.clone();
        this.calendar.set(Calendar.HOUR_OF_DAY, 0);
        this.calendar.set(Calendar.MINUTE, 0);
        applyValue();
    }

    void onClickListeners(Context context, DateViewArea dateViewArea) {
        this.date.setOnClickListener((view -> showDatePicker(context, dateViewArea)));
        this.time.setOnClickListener((view -> showTimePicker(context, dateViewArea)));
    }

    private void showDatePicker(Context context, DateViewArea dateViewArea) {
        DatePickerDialog    datePickerDialog;
        Calendar            calendar;

        calendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(context, 0, (datePicker, year, month, day) -> {
            this.calendar.set(Calendar.YEAR, year);
            this.calendar.set(Calendar.MONTH, month);
            this.calendar.set(Calendar.DAY_OF_MONTH, day);
            dateViewArea.notifyValuesHasChanged(this);
            applyValue();
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    private void showTimePicker(Context context, DateViewArea dateViewArea) {
        TimePickerDialog timePickerDialog;

        timePickerDialog = new TimePickerDialog(context, 0, (timePicker, h, m) -> {
            this.calendar.set(Calendar.HOUR_OF_DAY, h);
            this.calendar.set(Calendar.MINUTE, m);
            dateViewArea.notifyValuesHasChanged(this);
            applyValue();
        }, 0, 0, true);
        timePickerDialog.show();
    }

    /**
     * This method takes current values of time and date in object parameters
     * and post it to the view in readable format
     */
    private void applyValue() {
        SimpleDateFormat    dateFormat;
        SimpleDateFormat    timeFormat;

        dateFormat = new SimpleDateFormat(DateViewAreaImpl.VIEW_DATE_FORMAT, Locale.getDefault());
        timeFormat = new SimpleDateFormat(DateViewAreaImpl.VIEW_TIME_FORMAT, Locale.getDefault());
        this.date.setText(dateFormat.format(this.calendar.getTime()));
        this.time.setText(timeFormat.format(this.calendar.getTime()));
    }

    public long getValue() {
        return this.calendar.getTimeInMillis();
    }
}

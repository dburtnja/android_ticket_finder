package com.example.dburtnja.androidticketfinder.view;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

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
    }

    private View.OnClickListener onDateClick(Context context) {
        View.OnClickListener    listener;
        Calendar calendar;

        calendar = Calendar.getInstance();
        listener = view -> {
            DatePickerDialog datePickerDialog;

            datePickerDialog = new DatePickerDialog(context, 0, (datePicker, year, month, day) -> {

            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        };
        return listener;
    }

    private View.OnClickListener onTimeClick(Context context) {

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
        this.date.setText(dateFormat.format(new Date(this.dateValue)));
        this.time.setText(timeFormat.format(new Date(this.timeValue)));
    }

    public long getValue() {
        return this.dateValue + this.timeValue;
    }
}

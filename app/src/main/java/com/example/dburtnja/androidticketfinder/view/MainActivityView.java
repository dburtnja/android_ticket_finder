package com.example.dburtnja.androidticketfinder.view;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.dburtnja.androidticketfinder.R;
import com.example.dburtnja.androidticketfinder.model.Station;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by denys on 9/3/17.
 */

public class MainActivityView {
    private static final String DATE_FORMAT = "Дата: dd.MM.yyyy";
    private static final String TIME_FORMAT = "Час: HH:mm";

    private EditText    stationFrom;
    private EditText    stationTill;
    private Button      replaceStations;
    private TextView    dateFromStart;
    private TextView    timeFromStart;
    private TextView    dateFromEnd;
    private TextView    timeFromEnd;
    private CheckBox[]  places;
    private EditText    firstName;
    private EditText    lastName;
    private EditText    stud;
    private Button      add;

    public MainActivityView(Activity activity) {
        this.stationFrom = activity.findViewById(R.id.stationFrom);
        this.stationTill = activity.findViewById(R.id.stationTill);
        this.replaceStations = activity.findViewById(R.id.replaceStations);
        this.dateFromStart = activity.findViewById(R.id.dateFromStart);
        this.timeFromStart = activity.findViewById(R.id.timeFromStart);
        this.dateFromEnd = activity.findViewById(R.id.dateFromEnd);
        this.timeFromEnd = activity.findViewById(R.id.timeFromEnd);
        this.places = new CheckBox[]{
                activity.findViewById(R.id.checkP),
                activity.findViewById(R.id.checkK),
                activity.findViewById(R.id.checkC1),
                activity.findViewById(R.id.checkC2)
        };
        this.firstName = activity.findViewById(R.id.firstName);
        this.lastName = activity.findViewById(R.id.lastName);
        this.stud = activity.findViewById(R.id.stud);
        this.add = activity.findViewById(R.id.add);
    }

    public void setCurrentDate() {
        Date                currentDate;
        SimpleDateFormat    dateFormat;

        currentDate = Calendar.getInstance().getTime();
        dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
        this.dateFromStart.setText(dateFormat.format(currentDate));
        this.dateFromEnd.setText(dateFormat.format(currentDate));
    }

    public void setTimeFromStart(long time) {
        SimpleDateFormat    dateFormat;
        SimpleDateFormat    timeFormat;

        dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
        timeFormat = new SimpleDateFormat(TIME_FORMAT, Locale.getDefault());
        this.dateFromStart.setText(dateFormat.format(time));
        this.timeFromStart.setText(timeFormat.format(time));
    }

    public void setTimeFromEnd(long time) {
        SimpleDateFormat    dateFormat;
        SimpleDateFormat    timeFormat;

        dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
        timeFormat = new SimpleDateFormat(TIME_FORMAT, Locale.getDefault());
        this.dateFromEnd.setText(dateFormat.format(time));
        this.timeFromEnd.setText(timeFormat.format(time));
    }

    public void onStationEnter(View.OnFocusChangeListener from, View.OnFocusChangeListener till) {
        this.stationFrom.setOnFocusChangeListener(from);
        this.stationTill.setOnFocusChangeListener(till);
    }

    public void setStationFromText(String stationName) {
        this.stationFrom.setText(stationName);
    }

    public void setStationTillText(String stationName) {
        this.stationTill.setText(stationName);
    }

    public void onReplaceStationsListener(View.OnClickListener listener) {
        this.replaceStations.setOnClickListener(listener);
    }

    public Map<String, Boolean> getPlacesMap() {
        HashMap<String, Boolean>    mapPlaces;

        mapPlaces = new HashMap<>();
        for (CheckBox place : this.places) {
            if (place.isChecked()) {
                switch (place.getId()) {
                    case R.id.checkP:
                        mapPlaces.put("P", true);
                        break;
                    case R.id.checkK:
                        mapPlaces.put("K", true);
                        break;
                    case R.id.checkC1:
                        mapPlaces.put("C1", true);
                        break;
                    case R.id.checkC2:
                        mapPlaces.put("C2", true);
                }
            }
        }
        return mapPlaces;
    }

    public String getFirstName() {
        return firstName.getText().toString();
    }

    public String getLastName() {
        return lastName.getText().toString();
    }

    public String getStud() {
        return stud.getText().toString();
    }

    public void setOnAddClickListener(View.OnClickListener listener) {
        this.add.setOnClickListener(listener);
    }
}

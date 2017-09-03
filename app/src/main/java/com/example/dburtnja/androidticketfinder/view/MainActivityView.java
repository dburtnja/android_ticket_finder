package com.example.dburtnja.androidticketfinder.view;

import android.app.Activity;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.dburtnja.androidticketfinder.R;

import java.util.ArrayList;

/**
 * Created by denys on 9/3/17.
 */

public class MainActivityView {
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


}

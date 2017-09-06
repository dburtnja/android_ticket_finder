package com.example.dburtnja.androidticketfinder.view;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.dburtnja.androidticketfinder.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by denys on 9/3/17.
 */

public class MainActivityView {
    private StationViewArea stationViewArea;
    private DateViewArea    dateView;
    private CheckBox[]      places;
    private EditText        firstName;
    private EditText        lastName;
    private EditText        stud;
    private Button          add;

    public MainActivityView(Activity activity) {
        this.stationViewArea = new StationViewArea(activity, R.id.stationFrom, R.id.stationTill, R.id.replaceStations);
        this.dateView = new DateViewAreaImpl(activity);
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

    public void onStationEnter(View.OnFocusChangeListener listener) {
        this.stationViewArea.setListeners(listener);
    }

    public Station getStationFrom() {
        return stationViewArea.getFrom();
    }

    public Station getStationTill() {
        return stationViewArea.getTill();
    }

    public long getStartDate() {
        return dateView.getTimeStart();
    }

    public long getEndDate() {
        return dateView.getTimeEnd();
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

    public void setOnAddClickListener(View.OnClickListener listener) {
        this.add.setOnClickListener(listener);
    }

    public void setOnDateViewListener(Context context) {
        this.dateView.setOnClickListeners(context);
    }
}

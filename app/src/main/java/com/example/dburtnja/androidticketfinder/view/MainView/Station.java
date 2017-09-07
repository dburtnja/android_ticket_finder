package com.example.dburtnja.androidticketfinder.view.MainView;

import android.widget.EditText;

/**
 * Created by denys on 9/6/17.
 */

public class Station {
    private transient EditText  stationView;
    private String              name;
    private int                 value;

    public Station(EditText stationView) {
        this.stationView = stationView;
        this.name = null;
        this.value = 0;
    }

    public Station(Station station) {
        this.stationView = station.stationView;
        this.name = station.getName();
        this.value = station.getValue();
    }

    public Station(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public void setStation(String name, int value) {
        this.stationView.setText(name);
        this.name = name;
        this.value = value;
    }

    public void setStation(Station station) {
        this.stationView.setText(station.name);
        this.name = station.name;
        this.value = station.value;
    }

    EditText getStationView(){
        return this.stationView;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    public boolean isSet() {
        return (name != null && value != 0);
    }

    public void setNameView() {
        this.stationView.setText(name);
    }
}
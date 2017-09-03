package com.example.dburtnja.androidticketfinder.model;

import android.widget.EditText;

/**
 * Created by denys on 9/3/17.
 */

public class Passenger {
    private Station from;
    private Station till;
    private Date    dateStart;
    private Date    dateEnd;
    private String  firstName;
    private String  lastName;
    private String  stud;

    public void setFromStation(EditText station) {
        this.from = new Station(station);
    }

    public void setTillStation(EditText station) {
        this.from = new Station(station);
    }
}

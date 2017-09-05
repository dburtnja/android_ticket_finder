package com.example.dburtnja.androidticketfinder.model;

import android.widget.EditText;
import android.widget.TextView;

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

    public Passenger() {
        this.from = new Station();
        this.till = new Station();
    }

    public void setDate(TextView dateStart, TextView timeStart, TextView dateEnd, TextView timeEnd) {
        this.dateStart = new Date(dateStart, timeStart);
        this.dateEnd = new Date(dateEnd, timeEnd);
    }

    public void setName(EditText firstName, EditText lastName) {
        this.firstName = firstName.getText().toString();
        this.lastName = lastName.getText().toString();
    }

    public void setStud(String stud) {
        this.stud = stud;
    }

    public Station getFrom() {
        return from;
    }

    public Station getTill() {
        return till;
    }
}

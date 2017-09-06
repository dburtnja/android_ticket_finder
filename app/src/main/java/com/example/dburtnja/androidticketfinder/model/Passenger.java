package com.example.dburtnja.androidticketfinder.model;

import android.content.ContentValues;

import com.example.dburtnja.androidticketfinder.MainActivity;
import com.example.dburtnja.androidticketfinder.R;
import com.example.dburtnja.androidticketfinder.view.MainView.Station;

/**
 * Created by denys on 9/3/17.
 */

public class Passenger {
    public static final int         PLACE_P = 1;
    public static final int         PLACE_K = 2;
    public static final int         PLACE_C1 = 4;
    public static final int         PLACE_C2 = 8;

    private Station                 from;
    private Station                 till;
    private long                    dateStart;
    private long                    dateEnd;
    private int                     places;
    private String                  firstName;
    private String                  lastName;
    private String                  stud;

    public void setPlaces(int places) {
        this.places = places;
    }

    public void setDate(long dateStart, long dateEnd) {
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
    }

    public void setName(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void setStud(String stud) {
        this.stud = stud;
    }

    public void setFrom(Station from) {
        this.from = from;
    }

    public void setTill(Station till) {
        this.till = till;
    }

    public boolean isAllSet(MainActivity activity) {
        if (!from.isSet())
            return activity.toast(R.string.emptyStationFrom, true);
        else if (!till.isSet())
            return activity.toast(R.string.emptyStationTill, true);
        else if (places < 1)
            return activity.toast(R.string.emptyPlaceType, true);
        else if (firstName.matches(""))
            return activity.toast(R.string.emptyFirstName, true);
        else if (lastName.matches(""))
            return activity.toast(R.string.emptyLastName, true);
        return true;
    }

    public ContentValues getAsContentValues() {
        ContentValues   passenger;

        passenger = new ContentValues();
        passenger.put(DbHelper.KEY_STATION_FROM, from.getName());
        passenger.put(DbHelper.KEY_STATION_FROM_VALUE, from.getValue());
        passenger.put(DbHelper.KEY_STATION_TILL, till.getName());
        passenger.put(DbHelper.KEY_STATION_TILL_VALUE, till.getValue());
        passenger.put(DbHelper.KEY_DATE_FROM, dateStart);
        passenger.put(DbHelper.KEY_DATE_TILL, dateEnd);
        passenger.put(DbHelper.KEY_PLACE, places);
        passenger.put(DbHelper.KEY_FIRST_NAME, firstName);
        passenger.put(DbHelper.KEY_LAST_NAME, lastName);
        if (stud != null && stud.matches(".{3,}"))
            passenger.put(DbHelper.KEY_STUD, stud);
        return passenger;
    }

    public Station getFrom() {
        return from;
    }

    public Station getTill() {
        return till;
    }

    public long getDateStart() {
        return dateStart;
    }

    public long getDateEnd() {
        return dateEnd;
    }

    public int getPlaces() {
        return places;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getStud() {
        return stud;
    }
}

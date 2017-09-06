package com.example.dburtnja.androidticketfinder.model;

import com.example.dburtnja.androidticketfinder.MainActivity;
import com.example.dburtnja.androidticketfinder.R;
import com.example.dburtnja.androidticketfinder.view.Station;

import java.util.Map;

/**
 * Created by denys on 9/3/17.
 */

public class Passenger {
    private Station                 from;
    private Station                 till;
    private long                    dateStart;
    private long                    dateEnd;
    private Map<String, Boolean>    places;
    private String                  firstName;
    private String                  lastName;
    private String                  stud;

    public void setPlaces(Map<String, Boolean> places) {
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
        else if (places.size() < 1)
            return activity.toast(R.string.emptyPlaceType, true);
        else if (firstName.matches(""))
            return activity.toast(R.string.emptyFirstName, true);
        else if (lastName.matches(""))
            return activity.toast(R.string.emptyLastName, true);
        return true;
    }
}

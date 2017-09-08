package com.example.dburtnja.androidticketfinder.view.MainView;

import android.app.Activity;
import android.view.View;
import android.widget.ImageButton;

import com.example.dburtnja.androidticketfinder.model.Passenger;

/**
 * Created by denys on 9/6/17.
 */

public class StationViewArea {
    private ImageButton replaceStations;
    private Station     from;
    private Station     till;

    public StationViewArea(Activity activity, int fromId, int tillId, int replaceId) {
        this.replaceStations = activity.findViewById(replaceId);
        this.from = new Station(activity.findViewById(fromId));
        this.till = new Station(activity.findViewById(tillId));
    }

    public void setListeners(View.OnFocusChangeListener stationListener) {
        this.from.getStationView().setOnFocusChangeListener(stationListener);
        this.till.getStationView().setOnFocusChangeListener(stationListener);
        this.replaceStations.setOnClickListener(view -> replaceStations());
    }

    public Station getFrom() {
        return from;
    }

    public Station getTill() {
        return till;
    }

    private void replaceStations() {
        Station buff;

        buff = new Station(this.from);
        this.from.setStation(this.till);
        this.till.setStation(buff);
    }

    public void setStations(Passenger passenger) {
        this.from.setStation(passenger.getFrom().getName(), passenger.getFrom().getValue());
        this.till.setStation(passenger.getTill().getName(), passenger.getTill().getValue());
    }
}

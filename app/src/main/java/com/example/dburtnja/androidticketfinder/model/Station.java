package com.example.dburtnja.androidticketfinder.model;

import android.widget.EditText;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by denys on 9/3/17.
 */

public class Station {
    private String  name;
    private int     value;

    public Station(EditText station) {
        VolleyJSONArrayRequest  request;
        String                  url;

        url = "http://booking.uz.gov.ua/mobile/train_search/station/?term=";
        try {
            url = url + URLEncoder.encode(String.valueOf(station.getText()), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }
}

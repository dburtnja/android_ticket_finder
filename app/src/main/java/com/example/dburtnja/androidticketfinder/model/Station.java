package com.example.dburtnja.androidticketfinder.model;

import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.example.dburtnja.androidticketfinder.controller.volley.VolleyStringRequest;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by denys on 9/3/17.
 */

public class Station {
    private static final String STATION_GET_URL = "http://booking.uz.gov.ua/mobile/train_search/station/?term=";

    private String  name;
    private int     value;

    public Station() {
        this.name = null;
        this.value = 0;
    }

    public void getStationValue(RequestQueue queue, EditText station) {
        VolleyStringRequest request;
        String              url;

        if (this.name != null && station.getText().toString().matches(this.name) && this.value != 0)
            return ;
        try {
            url = STATION_GET_URL + URLEncoder.encode(String.valueOf(station.getText()), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            //TODO add info message
            return;
        }
        request = new VolleyStringRequest(Request.Method.GET, url, response -> this.onResponse(response, station));
        queue.add(request);
    }

    private void onResponse(String response, EditText station) {
        JSONArray   array;

        if (response.contains("error")) {
            //TODO info message
            return;
        }

        try {
            array = new JSONArray(response);
            this.name = array.getJSONObject(0).getString("title");
            this.value = array.getJSONObject(0).getInt("value");
            station.setText(this.name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    boolean isAllSet() {
        return !(this.name == null || this.value == 0);
    }
}

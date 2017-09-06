package com.example.dburtnja.androidticketfinder.controller.volley;

import android.content.Context;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.dburtnja.androidticketfinder.view.Station;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by denys on 9/6/17.
 */

public class StationReceiver {
    private static final String STATION_GET_URL = "http://booking.uz.gov.ua/mobile/train_search/station/?term=";

    public static void get(Context context, String name, Station station) {
        RequestQueue queue;

        queue = Volley.newRequestQueue(context);
        getStationValue(queue, name, station);
    }

    public static void getStationValue(RequestQueue queue, String name, Station station) {
        VolleyStringRequest request;
        String              url;

        try {
            url = STATION_GET_URL + URLEncoder.encode(name, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            //TODO add info message
            return;
        }
        request = new VolleyStringRequest(Request.Method.GET, url, response -> onResponse(response, station));
        queue.add(request);
    }

    private static void onResponse(String response, Station station) {
        JSONArray   array;
        String      name;
        int         value;

        if (response.contains("[]") || response.contains("error")) {
            station.setStation(null, 0);
            return;
        }

        try {
            array = new JSONArray(response);
            name = array.getJSONObject(0).getString("title");
            value = array.getJSONObject(0).getInt("value");
            station.setStation(name, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

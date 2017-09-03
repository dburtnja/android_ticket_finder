package com.example.dburtnja.androidticketfinder.controller.volley;

import com.android.volley.Response;

/**
 * Created by denys on 9/3/17.
 */

public class GETStringRequest extends VolleyStringRequest {

    public GETStringRequest(String url, Response.Listener<String> listener) {
        super(Method.GET, url, listener);
    }
}

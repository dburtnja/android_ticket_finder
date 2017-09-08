package com.example.dburtnja.androidticketfinder.controller.volley;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by denys on 9/3/17.
 */

public class VolleyStringRequest extends StringRequest {
    private static int  errorCounter;

    static {
        errorCounter = 0;
    }

    public VolleyStringRequest(int method, String url, Response.Listener<String> response) {
        super(method, url, response, error -> {
            errorCounter++;
            Log.e("Volley ERROR", error.getMessage());
        });
        this.setRetryPolicy(new DefaultRetryPolicy(3000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        this.setShouldCache(false);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers;

        headers = new HashMap<>();
        headers.put("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36");
        return headers;
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        String  stringResponse;

        try {
            stringResponse = new String(response.data, "UTF-8");
            return Response.success(stringResponse, HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return Response.error(new ParseError(e));
        }

    }
}

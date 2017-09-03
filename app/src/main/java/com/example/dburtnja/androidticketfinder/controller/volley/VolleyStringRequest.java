package com.example.dburtnja.androidticketfinder.controller.volley;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by denys on 9/3/17.
 */

public abstract class VolleyStringRequest extends StringRequest {
    private static int  errorCounter;

    static {
        errorCounter = 0;
    }

    public VolleyStringRequest(int method, String url) {
        super(method, url,
                response -> {
                    if (response != null && response.contains("error"))
                        this.onResponse();
                },
                error -> {
                    errorCounter++;
                    Log.e("Volley ERROR", error.getMessage());
        });

        this.setRetryPolicy(new DefaultRetryPolicy(3000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        this.setShouldCache(false);
    }

    abstract public void onResponse();

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers;

        headers = new HashMap<>();
        headers.put("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36");
        return headers;
    }
}

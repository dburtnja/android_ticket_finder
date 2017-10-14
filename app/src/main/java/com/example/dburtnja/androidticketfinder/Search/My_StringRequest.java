package com.example.dburtnja.androidticketfinder.Search;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.dburtnja.androidticketfinder.TicketInfo.Ticket;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dburtnja on 13.07.17.
 * My Volley request
 */

public class My_StringRequest extends StringRequest {
    private Ticket                  ticket;
    private Map<String, String>     param;

    public My_StringRequest(final String url, final Ticket ticket, Map<String, String> param, Response.Listener<String> listener) {
        super(Method.POST, url, listener, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Помилка", url + error.getMessage() + "");
                ticket.setError("Помилка: " + url);
                ticket.notificator.postNotification(ticket);
            }
        });
        this.ticket = ticket;
        this.param = param;
        this.setRetryPolicy(new DefaultRetryPolicy(3000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        this.setShouldCache(false);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers;

        headers = new HashMap<>();
        headers.put("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36");
        headers.put("Cookie", "_gv_lang=uk; _gat=1");
        return headers;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return this.param;
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        System.out.println(response.headers);



        System.out.println(response);
        System.out.println(response.headers);
        return super.parseNetworkResponse(response);
    }


}

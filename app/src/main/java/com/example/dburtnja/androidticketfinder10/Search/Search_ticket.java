package com.example.dburtnja.androidticketfinder10.Search;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dburtnja.androidticketfinder10.TicketInfo.Ticket;
import com.example.dburtnja.androidticketfinder10.TicketInfo.TicketDate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by dburtnja on 12.07.17.
 * Search class
 */

public class Search_ticket {
    private final static long           DAY = 86400000;
    private Ticket                      ticket;
    private Context                     service;
    private RequestQueue                queue;
    public Response.ErrorListener       errorListener;
    private int                         counter;

    public Search_ticket(final Ticket ticket, Context service) {
        this.ticket = ticket;
        this.service = service;
        queue = Volley.newRequestQueue(service);
    }

    public void checkForTrain(){
        int     responseCounter;

        responseCounter = 0;
        ticket.bufDateFromStart = new TicketDate(ticket.dateFromStart.getDate());
        while (ticket.bufDateFromStart.getDate() < ticket.dateFromEnd.getDate()){
            findTicket();
            Log.d("test", ticket.bufDateFromStart.getStrDate() + " " + ticket.bufDateFromStart.getStrTime());
            ticket.bufDateFromStart = new TicketDate(ticket.bufDateFromStart.getNextDayTime());
            responseCounter++;
        }
        Log.d("response Counter", responseCounter + "");
    }

    public void findTicket(){
        StringRequest   request;
        String          url;

        url = "http://booking.uz.gov.ua/purchase/search/";
        request = new My_StringRequest(url, ticket, ticket.getSearchParam(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject  trainList;

                        if ((trainList = responseToJson(response)) != null)
                            findPlace(trainList);
                    }
        });
        queue.add(request);
    }

    private JSONObject responseToJson(String response){
        String      jsonStr;
        JSONObject  jsonObj;

        try {
            jsonStr = new String(response.getBytes("ISO-8859-1"), "UTF-8");
            Log.d("response:88", jsonStr);
            jsonObj = new JSONObject(jsonStr);
            if (jsonObj.has("error") && jsonObj.getString("error").equals("true")) {
                ticket.setError(jsonObj.getString("value"));
                return null;
            }
            //TODO: need add captcha check!!!
            return jsonObj;
        } catch (UnsupportedEncodingException e) {
            ticket.setError("Кодування не підтримується");
            e.printStackTrace();
        } catch (JSONException e) {
            ticket.setError("Помилка створення JSON об'єкту");
            e.printStackTrace();
        }
        return null;
    }

    private void findPlace(JSONObject dataObj){
        try {
            JSONArray           trainList;
            JSONObject          train;
            long                depDate;
            String              place;

            trainList = dataObj.getJSONArray("value");
            label: for (int i = 0; i < trainList.length(); i++) {
                train = trainList.getJSONObject(i);
                depDate = train.getJSONObject("from").getLong("date");
                if (depDate <= ticket.dateFromEnd.getDateMS()) {
                    for (int j = 0; j < train.getJSONArray("types").length(); j++){
                        place = train.getJSONArray("types").getJSONObject(j).getString("letter");
                        if (ticket.getPlaces().isSuitable(place)) {
                            ticket.setMyTrain(train.getString("num"), depDate, place, train.getString("model"));
                            //sending Coaches request
                            findCoaches(ticket.getCoachesParam());
                            break label;
                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void findCoaches(Map<String, String> param){
        String          url;
        StringRequest   request;

        url = "http://booking.uz.gov.ua/purchase/coaches/";
        request = new My_StringRequest(url, ticket, param, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject  coaches;
                JSONObject  coach;

                if ((coaches = responseToJson(response)) != null) {
                    try {
                        coach = coaches.getJSONArray("coaches").getJSONObject(0);
                        ticket.setMyTrainCoach(coach.getInt("num"), coach.getString("coach_class"), coach.getInt("coach_type_id"));
                        //sending Coach request
                        findCoach(ticket.getCoachParam());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        queue.add(request);
    }

    private void findCoach(Map<String, String> param){
        String          url;
        StringRequest   request;

        url = "http://booking.uz.gov.ua/purchase/coach/";
        request = new My_StringRequest(url, ticket, param, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject  coach;

                Log.d("coach", response);
                if ((coach = responseToJson(response)) != null) {
                    try {
                       Log.d("coach", coach.getJSONObject("value").getJSONObject("places").has('Б)));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        queue.add(request);
    }
}

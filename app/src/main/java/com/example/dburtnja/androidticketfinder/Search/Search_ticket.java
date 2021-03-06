package com.example.dburtnja.androidticketfinder.Search;

import android.app.AlarmManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dburtnja.androidticketfinder.Main3Activity;
import com.example.dburtnja.androidticketfinder.TicketInfo.Ticket;
import com.example.dburtnja.androidticketfinder.TicketInfo.TicketDate;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.Map;

/**
 * Created by dburtnja on 12.07.17.
 * Search class
 */

public class Search_ticket {
    private final static long           DAY = 86400000;
    private Ticket                      ticket;
    private Service                     service;
    private RequestQueue                queue;
    public boolean                      find;
    CookieManager                       cookieManager;



    public Search_ticket(final Ticket ticket, Service service) {
        this.find = false;
        this.ticket = ticket;
        this.service = service;

        queue = Volley.newRequestQueue(service);
        ticket.notificator = new MyNotification(service, ticket);
    }

    public void checkForTrain(){
        int     responseCounter;

        responseCounter = 0;
        ticket.notificator.postNotification(ticket);
        ticket.bufDateFromStart = new TicketDate(ticket.dateFromStart.getDate());
//        while (ticket.bufDateFromStart.getDate() < ticket.dateFromEnd.getDate()){
            findTicket();
            Log.d("test", ticket.bufDateFromStart.getStrDate() + " " + ticket.bufDateFromStart.getStrTime());
//            ticket.bufDateFromStart = new TicketDate(ticket.bufDateFromStart.getNextDayTime());
//            responseCounter++;
//        }
        Log.d("response Counter", responseCounter + "");
    }

    public void findTicket(){
        StringRequest   request;
        String          url;

        url = "https://booking.uz.gov.ua/train_search/";
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
                ticket.notificator.postNotification(ticket);
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

            trainList = dataObj.getJSONObject("data").getJSONArray("list");
            label: for (int i = 0; i < trainList.length(); i++) {
                train = trainList.getJSONObject(i);
                depDate = train.getJSONObject("from").getLong("sortTime");
                if (depDate <= ticket.dateFromEnd.getDateMS()) {
                    for (int j = 0; j < train.getJSONArray("types").length(); j++){
                        place = train.getJSONArray("types").getJSONObject(j).getString("letter");
                        if (ticket.getPlaces().isSuitable(place)) {
                            ticket.setMyTrain(train.getString("num"), depDate, place, null);
                            //sending Coaches request
                            findCoaches(ticket.getCoachesParam());
                            break label;
                        }
                    }
                }
            }
        } catch (JSONException e) {
            Log.d("134", e.getMessage());
            e.printStackTrace();
        }
    }

    private void findCoaches(Map<String, String> param){
        String          url;
        StringRequest   request;

        url = "https://booking.uz.gov.ua/train_wagons/";
        request = new My_StringRequest(url, ticket, param, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject  coaches;
                JSONObject  coach;

                if ((coaches = responseToJson(response)) != null) {
                    try {
                        coach = coaches.getJSONObject("data").getJSONArray("wagons").getJSONObject(0);
                        ticket.setMyTrainCoach(coach.getInt("num"), coach.getString("class"), coach.getString("type"));
                        //sending Coach request
                        findCoach(ticket.getCoachParam());
                    } catch (JSONException e) {
                        Log.d("157", e.getMessage());
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

        url = "https://booking.uz.gov.ua/train_wagon/";
        request = new My_StringRequest(url, ticket, param, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject  coach;

                cookieManager = new CookieManager(null, CookiePolicy.ACCEPT_ALL);
                CookieHandler.setDefault(cookieManager);

                if ((coach = responseToJson(response)) != null) {
                    try {
                        do {
                            ticket.getMyTrain().findPlaceNbr(coach.getJSONObject("data").getJSONObject("places"));
                        }while (ticket.getMyTrain().getPlaceNbr() > 0 && addToCard(ticket.getAddParam()));
                    } catch (JSONException e) {
                        Log.d("185", e.getMessage());
                        e.printStackTrace();
                    }
                }
            }
        });
        queue.add(request);
    }

    private boolean addToCard(Map<String, String> param){
        String          url;
        StringRequest   request;

        Log.d("placeNbr", ticket.getMyTrain().getPlaceNbr() + "");
        stopSearch();
        url = "https://booking.uz.gov.ua/cart/add/";
        request = new My_StringRequest(url, ticket, param, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject  add;
                Intent      intent;
                Gson        gson;


                if (ticket.getCookie() == null || ticket.getCookie().isEmpty())
                    ticket.setCookie(cookieManager);
                System.out.println(ticket.getCookie());

                System.out.println(cookieManager.getCookieStore().getCookies());

                gson = new Gson();
                intent = new Intent(service, Main3Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if ((add = responseToJson(response)) != null) {
                    try {
                        ticket.status = add.getJSONObject("data").getString("page");
                        Log.d("Page", add.getJSONObject("data").getString("page"));
                        ticket.haveTicket = true;
                    } catch (JSONException e) {
                        Log.d("223", e.getMessage());
                        e.printStackTrace();
                    }
                }
                intent.putExtra("ticket", gson.toJson(ticket));
                service.startActivity(intent);
            }
        });
        queue.add(request);
        return false;
    }

    private void stopSearch(){
        AlarmManager    alarmManager;

        this.find = true;
        alarmManager = (AlarmManager) service.getSystemService(Context.ALARM_SERVICE);
        if (ticket.pendingIntent != null)
            alarmManager.cancel(ticket.pendingIntent);
        ticket.pendingIntent = null;
        ticket.notificator = null;
    }
}

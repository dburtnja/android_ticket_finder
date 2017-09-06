//package com.example.dburtnja.androidticketfinder.Search;
//
//import android.app.AlarmManager;
//import android.app.PendingIntent;
//import android.content.Context;
//import android.content.Intent;
//import android.util.Log;
//
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.toolbox.StringRequest;
//import com.android.volley.toolbox.Volley;
//import com.example.dburtnja.androidticketfinder.Main3Activity;
//import com.example.dburtnja.androidticketfinder.TicketInfo.Ticket;
//import com.example.dburtnja.androidticketfinder.TicketInfo.TicketDate;
//import com.google.gson.Gson;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.UnsupportedEncodingException;
//import java.net.CookieHandler;
//import java.net.CookieManager;
//import java.net.CookiePolicy;
//import java.util.Map;
//
///**
// * Created by dburtnja on 12.07.17.
// * Search class
// */
//
//public class Search_ticket {
//    private final static long           DAY = 86400000;
//    private Ticket                      ticket;
//    private Context                     service;
//    private RequestQueue                queue;
//    private CookieManager               cookieManager;
//
//
//    public Search_ticket(final Ticket ticket, Context service) {
//        this.ticket = ticket;
//        this.service = service;
//        cookieManager =  new CookieManager(null, CookiePolicy.ACCEPT_ALL);
//        CookieHandler.setDefault(cookieManager);
//        queue = Volley.newRequestQueue(service);
//        ticket.notificator = new MyNotification(service, ticket);
//    }
//
//    public void checkForTrain(){
//        ticket.notificator.postNotification(ticket);
//        ticket.bufDateFromStart = new TicketDate(ticket.dateFromStart.getDate());
//        while (ticket.bufDateFromStart.getDate() < ticket.dateFromEnd.getDate()){
//            findTicket();
//            Log.d("test", ticket.bufDateFromStart.getStrDate() + " " + ticket.bufDateFromStart.getStrTime());
//            ticket.bufDateFromStart = new TicketDate(ticket.bufDateFromStart.getNextDayTime());
//        }
//    }
//
//    public void findTicket(){
//        StringRequest   request;
//        String          url;
//
//        url = "http://booking.uz.gov.ua/purchase/search/";
//        request = new My_StringRequest(url, ticket, ticket.getSearchParam(),
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        JSONObject  trainList;
//sleep();
//                        if ((trainList = responseToJson(response)) != null)
//                            findPlace(trainList);
//                    }
//        });
//        queue.add(request);
//    }
//
//    private JSONObject responseToJson(String response){
//        String      jsonStr;
//        JSONObject  jsonObj;
//
//        try {
//            jsonStr = new String(response.getBytes("ISO-8859-1"), "UTF-8");
//            Log.d("response:88", jsonStr);
//            jsonObj = new JSONObject(jsonStr);
//            if (jsonObj.has("error") && jsonObj.getString("error").equals("true")) {
//                ticket.setError(jsonObj.getString("value"));
//                ticket.notificator.postNotification(ticket);
//                return null;
//            }
//            //TODO: need add captcha check!!!
//            return jsonObj;
//        } catch (UnsupportedEncodingException e) {
//            ticket.setError("Кодування не підтримується");
//            e.printStackTrace();
//        } catch (JSONException e) {
//            ticket.setError("Помилка створення JSON об'єкту");
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    private void findPlace(JSONObject dataObj){
//        try {
//            JSONArray           trainList;
//            JSONObject          train;
//            long                depDate;
//            String              place;
//
//            trainList = dataObj.getJSONArray("value");
//            label: for (int i = 0; i < trainList.length(); i++) {
//                train = trainList.getJSONObject(i);
//                depDate = train.getJSONObject("from").getLong("date");
//                if (depDate <= ticket.dateFromEnd.getDateMS()) {
//                    for (int j = 0; j < train.getJSONArray("types").length(); j++){
//                        place = train.getJSONArray("types").getJSONObject(j).getString("letter");
//                        if (ticket.getPlaces().isSuitable(place)) {
//                            ticket.setMyTrain(train.getString("num"), depDate, place, train.getString("model"));
//                            //sending Coaches request
//                            findCoaches(ticket.getCoachesParam());
//                            break label;
//                        }
//                    }
//                }
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void findCoaches(Map<String, String> param){
//        String          url;
//        StringRequest   request;
//
//        url = "http://booking.uz.gov.ua/purchase/coaches/";
//        request = new My_StringRequest(url, ticket, param, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                JSONObject  coaches;
//                JSONObject  coach;
//
//                sleep();
//
//                if ((coaches = responseToJson(response)) != null) {
//                    try {
//                        coach = coaches.getJSONArray("coaches").getJSONObject(0);
//                        ticket.setMyTrainCoach(coach.getInt("num"), coach.getString("coach_class"), coach.getString("type"));
//                        //sending Coach request
//                        findCoach(ticket.getCoachParam());
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });
//        queue.add(request);
//    }
//
//    private void findCoach(Map<String, String> param){
//        String          url;
//        StringRequest   request;
//
//        url = "http://booking.uz.gov.ua/purchase/coach/";
//        request = new My_StringRequest(url, ticket, param, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                JSONObject  coach;
//
//                sleep();
//
//                if ((coach = responseToJson(response)) != null) {
//                    try {
//                        do {
//                            ticket.getMyTrain().findPlaceNbr(coach.getJSONObject("value").getJSONObject("places"));
//                        }while (ticket.getMyTrain().getPlaceNbr() > 0 && addToCard(ticket.getAddParam()));
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });
//        queue.add(request);
//    }
//
//    private boolean addToCard(Map<String, String> param){
//        String          url;
//        StringRequest   request;
//
//        Log.d("placeNbr", ticket.getMyTrain().getPlaceNbr() + "");
//        stopSearch();
//        ticket.setCookie(cookieManager);
//        Log.d("COOKIE!!!!!!", ticket.getCookie());
//        url = "http://booking.uz.gov.ua/cart/add/";
//        request = new My_StringRequest(url, ticket, param, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                JSONObject  add;
//                Intent      intent;
//                Gson        gson;
//
//                gson = new Gson();
//                intent = new Intent(service, Main3Activity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//                sleep();
//
//                if ((add = responseToJson(response)) != null) {
//                    try {
//                        ticket.status = add.getJSONObject("value").getString("page");
//                        Log.d("Page", add.getJSONObject("value").getString("page"));
//                        ticket.haveTicket = true;
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//                intent.putExtra("ticket", gson.toJson(ticket));
//                service.startActivity(intent);
//            }
//        });
//        queue.add(request);
//        return false;
//    }
//
//    private void sleep(){
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void stopSearch(){
//        AlarmManager    alarmManager;
//
//        alarmManager = (AlarmManager) service.getSystemService(Context.ALARM_SERVICE);
//        if (ticket.pendingIntent != null)
//            alarmManager.cancel(ticket.pendingIntent);
//        ticket.pendingIntent = null;
//        ticket.notificator = null;
//    }
//}

//package com.example.dburtnja.androidticketfinder.TicketInfo;
//
//import android.app.PendingIntent;
//import android.util.Log;
//import android.widget.EditText;
//import android.widget.TextView;
//
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.JsonArrayRequest;
//import com.example.dburtnja.androidticketfinder.MainActivity;
//import com.example.dburtnja.androidticketfinder.Search.MyNotification;
//import com.example.dburtnja.androidticketfinder.model.Places;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.UnsupportedEncodingException;
//import java.net.CookieManager;
//import java.net.HttpCookie;
//import java.net.URLEncoder;
//import java.text.SimpleDateFormat;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Locale;
//import java.util.Map;
//
///**
// * Created by dburtnja on 04.07.17.
// * Object keep all info
// */
//
//public class Ticket {
//    private Station                 stationFrom;
//    private Station                 stationTill;
//    public TicketDate               dateFromStart;
//    public TicketDate               dateFromEnd;
//    public TicketDate               bufDateFromStart;
//    private Places places;
//    private String                  firstName;
//    private String                  lastName;
//    private String                  stud;
//    private String                  cookie;
//    public boolean                  haveTicket;
//    private Train                   myTrain;
//    public String                   status;
//    private boolean                 error;
//    public int                      pendingCode;
//    public transient PendingIntent  pendingIntent;
//    public transient MyNotification notificator;
//
//    public Ticket(Places places) {
//        this.places = places;
//        this.haveTicket = false;
//        stud = "";
//    }
//
//    public void setCookie(CookieManager cookieManager){
//        List<HttpCookie> cookies = cookieManager.getCookieStore().getCookies();
//        for (HttpCookie cookie: cookies) {
//            if (cookie.getName().equals("_gv_sessid"))
//                this.cookie = "_gv_sessid=" + cookie.getValue();
//        }
//    }
//
//    public String getCookie() {
//        return cookie;
//    }
//
//    public void setMyTrain(String num, long depDate, String place, String model) {
//        this.myTrain = new Train(num, depDate, place, model);
//    }
//
//    public void setMyTrainCoach(int coachNum, String coachClass, String coachType){
//        myTrain.setCoach(coachNum, coachClass, coachType);
//    }
//
//    public void setError(String status){
//        error = true;
//        this.status = status;
//    }
//
//    public void setSimpleFormats(){
//        dateFromStart.setSimpleFormat();
//        dateFromEnd.setSimpleFormat();
//    }
//
//    public boolean setName(int firstName, int lastName, MainActivity mainActivity){
//        this.firstName = ((TextView) mainActivity.findViewById(firstName)).getText().toString();
//        this.lastName = ((TextView) mainActivity.findViewById(lastName)).getText().toString();
//        if (this.firstName == null || this.lastName == null){
//            mainActivity.toast("Відсутнє ім'я чи прізвище", true);
//            return false;
//        }
//        return true;
//    }
//
//    public Places getPlaces() {
//        return places;
//    }
//
//    public Station getStationFrom() {
//        return stationFrom;
//    }
//
//    public Station getStationTill() {
//        return stationTill;
//    }
//
//    public Train getMyTrain() {
//        return myTrain;
//    }
//
//    public void setStationFrom(EditText stationName, RequestQueue queue, MainActivity mainActivity){
//        this.stationFrom = new Station(stationName, queue, mainActivity);
//    }
//
//    public void setStationTill(EditText stationName, RequestQueue queue, MainActivity mainActivity){
//        this.stationTill = new Station(stationName, queue, mainActivity);
//    }
//
//    public void replaceStations(EditText sFrom, EditText sTill) {
//        Station buf;
//
//        buf = stationFrom;
//        stationFrom = stationTill;
//        stationTill = buf;
//        sFrom.setText(stationFrom != null ? stationFrom.getTitle() : "");
//        sTill.setText(stationTill != null ? stationTill.getTitle() : "");
//    }
//
//    public boolean checkIfAllSet(MainActivity mainActivity, boolean checkName){
//        if (stationFrom == null)
//            return mainActivity.toast("Відсутня станція відправлення", true);
//        else if (stationTill == null)
//            return mainActivity.toast("Відсутня станція прибуття", true);
//        else if (dateFromStart.getDate() == -1)
//            return mainActivity.toast("Відсутній час відправлення", true);
//        else if (dateFromEnd.getDate() == -1)
//            return mainActivity.toast("Відсутній кінцевий час відправлення", true);
//        else if (!places.coachIsSet(mainActivity))
//            return false;
//        else if (checkName && (firstName == null || firstName.equals("")))
//            return mainActivity.toast("Відсутнє ім'я", true);
//        else if (checkName && (lastName == null || lastName.equals("")))
//            return mainActivity.toast("Відсутнє прізвище", true);
//        return true;
//    }
//
//    public Map<String, String> getSearchParam(){
//        Map<String, String> params;
//
//        params = new HashMap<>();
//        params.put("station_id_from", stationFrom.getValue() + "");
//        params.put("station_id_till", stationTill.getValue() + "");
//        params.put("station_from", stationFrom.getTitle());
//        params.put("station_till", stationTill.getTitle());
//        params.put("date_dep", bufDateFromStart.getStrDate());
//        params.put("time_dep", bufDateFromStart.getStrTime());
//        params.put("time_dep_till", "");
//        params.put("another_ec", "0");
//        params.put("search", "");
//        return (params);
//    }
//
//    public Map<String, String> getCoachesParam(){
//        Map<String, String> params;
//
//        params = new HashMap<>();
//        params.put("station_id_from", stationFrom.getValue() + "");
//        params.put("station_id_till", stationTill.getValue() + "");
//        params.put("train", myTrain.getNum());
//        params.put("coach_type", myTrain.getPlace());
//        params.put("model", myTrain.getModel());
//        params.put("date_dep", myTrain.getDepDate() + "");
//        params.put("round_trip", "0");
//        params.put("another_ec", "0");
//        return (params);
//    }
//
//
//    /**
//     *
//     station_id_from:2218300
//     station_id_till:2200001
//     train:748Ш
//     model:4
//     coach_num:1
//     coach_type:С
//     coach_class:1
//     date_dep:1501769220
//     cached_scheme[0]:К67
//     * @return
//     */
//    public Map<String, String> getCoachParam(){
//        Map<String, String> params;
//
//        params = new HashMap<>();
//        params.put("station_id_from", stationFrom.getValue() + "");
//        params.put("station_id_till", stationTill.getValue() + "");
//        params.put("train", myTrain.getNum());
//        params.put("model", myTrain.getModel());
//        params.put("coach_num", myTrain.getCoachNum() + "");
//        params.put("coach_type", myTrain.getCoachType());
//        params.put("coach_class", myTrain.getCoachClass());
//        params.put("date_dep", myTrain.getDepDate() + "");
//        params.put("cached_scheme[0]", "К67");
//        return (params);
//    }
//
//    /**
//     *
//     * from:2218300
//     to:2200001
//     train:748Ш
//     date:2017-08-03
//
//     round_trip:0
//     places[0][ord]:0
//     places[0][charline]:А
//     places[0][wagon_num]:5
//     places[0][wagon_class]:1
//     places[0][wagon_type]:С
//     places[0][firstname]:dsv
//     places[0][lastname]:sdv
//     places[0][bedding]:0
//     places[0][child]:
//     places[0][stud]:
//     places[0][transportation]:0
//     places[0][reserve]:0
//     places[0][place_num]:32
//     * @return
//     */
//
//    public Map<String, String> getAddParam(){
//        SimpleDateFormat    simpleDateFormat;
//        Map<String, String> params;
//
//        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
//        params = new HashMap<>();
//        params.put("from", stationFrom.getValue() + "");
//        params.put("to", stationTill.getValue() + "");
//        params.put("train", myTrain.getNum());
//        params.put("date", simpleDateFormat.format(myTrain.getDepDate() * 1000));
//        params.put("round_trip", "0");
//        params.put("places[0][ord]", "0");
//        params.put("places[0][charline]", "");
//        params.put("places[0][wagon_num]", myTrain.getCoachNum() + "");
//        params.put("places[0][wagon_class]", myTrain.getCoachClass());
//        params.put("places[0][wagon_type]", myTrain.getCoachType());
//        params.put("places[0][firstname]", firstName);
//        params.put("places[0][lastname]", lastName);
//        params.put("places[0][bedding]", "0");
//        params.put("places[0][child]", "");
//        params.put("places[0][stud]", stud);
//        params.put("places[0][transportation]", "0");
//        params.put("places[0][reserve]", "0");
//        params.put("places[0][place_num]", getMyTrain().getPlaceNbr() + "");
//
//        return (params);
//    }
//
//    public String getSearchParamMobile(){
//        String  param;
//
//        param = "date=" + "2017-07-17" +
//                "&from=" + stationFrom.getValue() +
//                "&time=" + "00%3A00" +
//                "&to=" + stationTill.getValue() +
//                "&get_tpl=1";
//        return param;
//    }
//
//    public class Station {
//        private String  title;
//        private String  region;
//        private int     value;
//
//        public String getTitle() {
//            return title;
//        }
//
//        public String getRegion() {
//            return region;
//        }
//
//        public int getValue() {
//            return value;
//        }
//
//        private Station(final EditText stationName, RequestQueue queue, final MainActivity activity){
//            JsonArrayRequest    getRequest;
//            String              url;
//
//            url = "http://booking.uz.gov.ua/mobile/train_search/station/?term=";
//            try {
//                url = url + URLEncoder.encode(String.valueOf(stationName.getText()), "UTF-8");
//            } catch (UnsupportedEncodingException e) {
//                activity.toast("Помилка кодування станції", true);
//                e.printStackTrace();
//            }
//            getRequest = new JsonArrayRequest(Request.Method.GET, url, null,
//                    new Response.Listener<JSONArray>() {
//                        @Override
//                        public void onResponse(JSONArray arrayResponse) {
//                            JSONObject  response;
//
//                            try {
//                                response = arrayResponse.getJSONObject(0);
//                                title = response.getString("title");
//                                region = response.getString("region");
//                                value = response.getInt("value");
//                            } catch (JSONException e) {
//                                activity.toast("Помилка отримання станції", true);
//                                e.printStackTrace();
//                            }
//                            stationName.setText(title);
//                            activity.toast("Станція: " + title + ". Значення: " + value, false);
//                        }
//                    },
//                    new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                            activity.toast("Помилка отримання даних станції", true);
//                            Log.e("STATION_ERROR", error.getMessage() + "");
//                        }
//                    });
//            queue.add(getRequest);
//        }
//    }
//}

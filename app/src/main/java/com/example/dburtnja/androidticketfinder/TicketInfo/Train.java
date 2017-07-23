package com.example.dburtnja.androidticketfinder.TicketInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by denys on 22.07.17.
 * received train param
 */

public class Train {
    private String          num;
    private long            depDate;
    private String          place;
    private String          model;
    private int             coachNum;
    private String          coachClass;
    private int             coachTypeId;
    private int             placeNbr;
    private boolean         up;
    private List<Integer>   checkedPlaces;

    public Train(String num, long depDate, String place, String model) {
        this.num = num;
        this.depDate = depDate;
        this.place = place;
        this.model = model;
        up = false;
        checkedPlaces = new ArrayList<>();
    }

    public void setCoach(int coachNum, String coachClass, int coachTypeId){
        this.coachNum = coachNum;
        this.coachClass = coachClass;
        this.coachTypeId = coachTypeId;
    }

    public int getPlaceNbr() {
        return placeNbr;
    }

    public String getNum() {
        return num;
    }

    public long getDepDate() {
        return depDate;
    }

    public String getPlace() {
        return place;
    }

    public String getModel() {
        return model;
    }

    public int getCoachNum() {
        return coachNum;
    }

    public String getCoachClass() {
        return coachClass;
    }

    public int getCoachTypeId() {
        return coachTypeId;
    }

    private String findKeyLetter(JSONObject places){
        for (char c = 'А'; c < 'Я'; c++){
            if (places.has(c + ""))
                return c + "";
        }
        return null;
    }

    public void findPlaceNbr(JSONObject placesObj){
        JSONArray   places;

        try {
            places = placesObj.getJSONArray(findKeyLetter(placesObj));
            for (int i = 0; i < places.length(); i++){
                if (!(checkedPlaces.contains(places.getInt(i)) || places.getInt(i) % 2 == 0)){
                    placeNbr = places.getInt(i);
                    checkedPlaces.add(placeNbr);
                    return;
                }
            }
            for (int i = 0; i < places.length(); i++){
                if (!(checkedPlaces.contains(places.getInt(i)))){
                    placeNbr = places.getInt(i);
                    checkedPlaces.add(placeNbr);
                    return;
                }
            }
            placeNbr = -1;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

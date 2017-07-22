package com.example.dburtnja.androidticketfinder10.TicketInfo;

/**
 * Created by denys on 22.07.17.
 * received train param
 */

public class Train {
    private String  num;
    private long    depDate;
    private String  place;
    private String  model;
    private int     coachNum;
    private String  coachClass;
    private int     coachTypeId;

    public Train(String num, long depDate, String place, String model) {
        this.num = num;
        this.depDate = depDate;
        this.place = place;
        this.model = model;
    }

    public void setCoach(int coachNum, String coachClass, int coachTypeId){
        this.coachNum = coachNum;
        this.coachClass = coachClass;
        this.coachTypeId = coachTypeId;
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
}

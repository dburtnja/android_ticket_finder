package com.example.dburtnja.androidticketfinder10.TicketInfo;

import android.widget.CheckBox;

import com.example.dburtnja.androidticketfinder10.MainActivity;
import com.example.dburtnja.androidticketfinder10.R;

import java.util.List;

/**
 * Created by dburtnja on 06.07.17.
 * Coach type object
 */

public class Train {
    private Place[] places;

    public Train() {
        places = new Place[]{
                new Place("П", true, R.id.checkP),
                new Place("С1", false, R.id.checkC1),
                new Place("С2", false, R.id.checkC2),
                new Place("К", false, R.id.checkK)
        };
    }

    private class Place{
        private String      name;
        private boolean     value;
        private int         checkID;

        public Place(String name, boolean value, int checkID) {
            this.name = name;
            this.value = value;
            this.checkID = checkID;
        }

        public void setValue(int id, boolean value){
            if (id == checkID)
                this.value = value;
        }

        public String getName() {
            return name;
        }

        public boolean isValue() {
            return value;
        }
    }

    public boolean isSuitable(String type){
        for (Place place : places){
            if (place.getName().equals(type))
                return true;
        }
        return false;
    }

    public void changeCoach(CheckBox[] checkBoxes){
        for (CheckBox checkBox : checkBoxes){
            for (Place place : places){
                place.setValue(checkBox.getId(), checkBox.isChecked());
            }
        }
    }

    public boolean coachIsSet(MainActivity activity){

        for (Place place : places){
            if (place.isValue())
                return true;
        }
        activity.toast("Не вказаний тип вагону", true);
        return false;
    }
}

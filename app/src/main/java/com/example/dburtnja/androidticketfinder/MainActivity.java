package com.example.dburtnja.androidticketfinder;

import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dburtnja.androidticketfinder.controller.volley.StationReceiver;
import com.example.dburtnja.androidticketfinder.model.Passenger;
import com.example.dburtnja.androidticketfinder.view.MainView.MainActivityView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View.OnFocusChangeListener  stationListener;
        MainActivityView mainView;
        Passenger                   passenger;

        mainView = new MainActivityView(this);
        passenger = new Passenger();

        stationListener = (view, hasFocus) -> {
            String  stationName;

            stationName = ((EditText) view).getText().toString();
            if (!hasFocus && stationName.matches(".{3,}")) {
                if (view.getId() == R.id.stationFrom)
                    StationReceiver.get(this, stationName, mainView.getStationFrom());
                else if (view.getId() == R.id.stationTill)
                    StationReceiver.get(this, stationName, mainView.getStationTill());
            }
        };

        mainView.onStationEnter(stationListener);

        mainView.setOnDateViewListener(this);

        mainView.setOnAddClickListener(view -> {
            Intent  intent;

            passenger.setFrom(mainView.getStationFrom());
            passenger.setTill(mainView.getStationTill());
            passenger.setDate(mainView.getStartDate(), mainView.getEndDate());
            passenger.setPlaces(mainView.getPlaces());
            passenger.setName(mainView.getFirstName(), mainView.getLastName());
            passenger.setStud(mainView.getStud());
            if (passenger.isAllSet(this)) {
                intent = new Intent();
                intent.putExtra("passenger", passenger.getAsContentValues());
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    public boolean toast(int stringId, Boolean vibrate) {
        if (vibrate) {
            Toast.makeText(this, getString(stringId), Toast.LENGTH_LONG).show();
            Vibrator vibrator;
            vibrator = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(500);
            return false;
        } else {
            Toast.makeText(this, stringId, Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    public boolean toast(String msg, Boolean vibrate) {
        if (vibrate) {
            Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
            Vibrator vibrator;
            vibrator = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(500);
            return false;
        } else {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        Ringtone ringtone = RingtoneManager.getRingtone(this, notification);
        ringtone.play();
    }
}

package com.example.dburtnja.androidticketfinder;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.SystemClock;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.dburtnja.androidticketfinder.TicketInfo.Ticket;
import com.example.dburtnja.androidticketfinder.TicketInfo.TicketDate;
import com.example.dburtnja.androidticketfinder.model.Passenger;
import com.example.dburtnja.androidticketfinder.model.Places;
import com.example.dburtnja.androidticketfinder.view.MainActivityView;
import com.google.gson.Gson;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {

    private Passenger           passenger;



    private Ticket          ticket;
    private EditText        getStationFrom;
    private EditText        getStationTill;
    private CheckBox        checkBoxArray[];
    private PendingIntent   pendingIntent;
    private AlarmManager    alarmManager;
    private Gson            gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View.OnFocusChangeListener  stationListener;
        MainActivityView            mainView;
        RequestQueue                queue;

        queue = Volley.newRequestQueue(this);
        mainView = new MainActivityView(this);
        passenger = new Passenger();

        stationListener = (view, hasFocus) -> {
            if (!hasFocus && !getStationFrom.getText().toString().equals("")) {
                if (view.getId() == R.id.stationFrom)
                    this.passenger.getFrom().getStationValue(queue, (EditText) view);
                else if (view.getId() == R.id.stationTill)
                    this.passenger.getTill().getStationValue(queue, (EditText) view);
            }
        };


        //set current time

        mainView.onStationEnter(stationListener);






        ImageButton             getReplaceStations;
        final Button            startButton = (Button) findViewById(R.id.start);
        Button                  stopButton;
        Button                  lookUp;
        final RequestQueue      queue;

        queue = Volley.newRequestQueue(this);
        getReplaceStations = (ImageButton) findViewById(R.id.replaceStations);

        ticket = new Ticket(new Places());
        ticket.dateFromStart = new TicketDate(this, R.id.dateFromStart, R.id.timeFromStart);
        ticket.dateFromEnd = new TicketDate(this, R.id.dateFromEnd, R.id.timeFromEnd);

        getStationFrom.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus && !getStationFrom.getText().toString().equals("")) {
                    ticket.setStationFrom(getStationFrom, queue, MainActivity.this);
                }
            }
        });

        getStationTill.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus && !getStationTill.getText().toString().equals("")) {
                    ticket.setStationTill(getStationTill, queue, MainActivity.this);
                }
            }
        });

        getReplaceStations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ticket.replaceStations(getStationFrom, getStationTill);
            }
        });

        View.OnClickListener clickOnDate = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.dateFromStart)
                    ticket.dateFromStart.changeDate(ticket, MainActivity.this);
                else if (view.getId() == R.id.dateFromEnd)
                    ticket.dateFromEnd.changeDate(ticket, MainActivity.this);
            }
        };

        View.OnClickListener clickOnTime = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.timeFromStart)
                    ticket.dateFromStart.changeTime(ticket, MainActivity.this);
                else if (view.getId() == R.id.timeFromEnd)
                    ticket.dateFromEnd.changeTime(ticket, MainActivity.this);
            }
        };

        View.OnClickListener clickOnPlace = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ticket.getPlaces().changeCoach(checkBoxArray);
            }
        };

        findViewById(R.id.dateFromStart).setOnClickListener(clickOnDate);
        findViewById(R.id.dateFromEnd).setOnClickListener(clickOnDate);
        findViewById(R.id.timeFromStart).setOnClickListener(clickOnTime);
        findViewById(R.id.timeFromEnd).setOnClickListener(clickOnTime);

        checkBoxArray = new CheckBox[]{
                (CheckBox) findViewById(R.id.checkP),
                (CheckBox) findViewById(R.id.checkK),
                (CheckBox) findViewById(R.id.checkC1),
                (CheckBox) findViewById(R.id.checkC2)
        };

        for (CheckBox checkBox : checkBoxArray){
            checkBox.setOnClickListener(clickOnPlace);
        }

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent      intent;

                ticket.setName(R.id.firstName, R.id.lastName, MainActivity.this);
                if (ticket.checkIfAllSet(MainActivity.this, true)){
                    ticket.pendingCode = (int)SystemClock.elapsedRealtime();
                    intent.putExtra("ticket", gson.toJson(ticket));
                    pendingIntent = PendingIntent.getService(MainActivity.this, ticket.pendingCode, intent, 0);
                }
            }
        });

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
    protected void onDestroy() {
        super.onDestroy();
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        Ringtone ringtone = RingtoneManager.getRingtone(this, notification);
        ringtone.play();
    }
}

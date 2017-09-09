package com.example.dburtnja.androidticketfinder;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.dburtnja.androidticketfinder.model.DbHelper;
import com.example.dburtnja.androidticketfinder.model.Passenger;
import com.example.dburtnja.androidticketfinder.view.ListView.PassengerAdapter;

import java.util.List;


public class ListUsersActivity extends AppCompatActivity {
    public static final int         NEW_INPUT = 1;
    public static final int         UPDATE_INPUT = 2;

    private DbHelper                dbHelper;
    private ListView                passengersList;
    private PassengerAdapter        adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_users);

        this.dbHelper = new DbHelper(this);
        this.passengersList = (ListView) findViewById(R.id.passengersList);

        this.passengersList.setOnItemClickListener((adapterView, view, pos, l) -> {
            Passenger   passenger;
            Intent      intent;

            intent = new Intent(this, MainActivity.class);
            passenger = (Passenger) adapterView.getItemAtPosition(pos);
            intent.putExtra("passenger", passenger);
            startActivityForResult(intent, UPDATE_INPUT);
        });

        this.passengersList.setOnItemLongClickListener((adapterView, view, pos, l) -> {
            AlertDialog.Builder dialogBuilder;

            dialogBuilder = new AlertDialog.Builder(this)
                    .setTitle(R.string.removeDialog)
                    .setMessage(R.string.removeQuestion)
                    .setPositiveButton(R.string.answerYes, (dialogInterface, i) -> {
                        dbHelper.removeItem((Passenger) adapterView.getItemAtPosition(pos));
                        loadUserList();
                    })
                    .setNegativeButton(R.string.answerNo, (dialogInterface, i) -> dialogInterface.cancel());
            dialogBuilder.show();
            return true;
        });
        loadUserList();
    }

    private void loadUserList() {
        List<Passenger> list;

        list = dbHelper.getPassengerList();
        if (this.adapter == null) {
            this.adapter = new PassengerAdapter(this, R.layout.row, list);
            this.passengersList.setAdapter(this.adapter);
        } else {
            this.adapter.clear();
            this.adapter.addAll(list);
            this.adapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add:
                Intent  intent;

                intent = new Intent(this, MainActivity.class);
                startActivityForResult(intent, NEW_INPUT);
                return true;
            case R.id.menu_start:
                startService();
                return true;
            case R.id.menu_pause:
                ((AlarmManager)getSystemService(ALARM_SERVICE)).cancel(MyService.getServicePendingIntent(this));
        }
        return super.onOptionsItemSelected(item);
    }

    private void startService() {
        PendingIntent   pendingIntent;
        AlarmManager    alarmManager;

        pendingIntent = MyService.getServicePendingIntent(this);
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime(), 60000, pendingIntent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Passenger   passenger;

        if (requestCode == NEW_INPUT && resultCode == RESULT_OK) {
            passenger = (Passenger) data.getSerializableExtra("passenger");
            dbHelper.insertIntoDB(passenger.getAsContentValues());
            loadUserList();
        } else if (requestCode == UPDATE_INPUT && resultCode == RESULT_OK) {
            passenger = (Passenger) data.getSerializableExtra("passenger");
            dbHelper.changePassenger(passenger.getAsContentValues(), passenger.getId());
            loadUserList();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}

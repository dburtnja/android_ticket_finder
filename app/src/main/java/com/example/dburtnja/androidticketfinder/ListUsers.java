package com.example.dburtnja.androidticketfinder;

import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.dburtnja.androidticketfinder.model.DbHelper;
import com.example.dburtnja.androidticketfinder.view.ListView.ContentValuesAdapter;

import java.util.ArrayList;
import java.util.List;


public class ListUsers extends AppCompatActivity {
    private DbHelper                dbHelper;
    private ListView                passengersList;
    private ContentValuesAdapter    adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_users);

        this.dbHelper = new DbHelper(this);
        this.passengersList = (ListView) findViewById(R.id.passengersList);

        loadUserList();
    }

    private void loadUserList() {
        ArrayList<ContentValues> list;

        list = dbHelper.getPassengerList();
        if (this.adapter == null) {
            this.adapter = new ContentValuesAdapter(this, R.layout.row, list);
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
                startActivityForResult(intent, 1);
                return true;
            case R.id.menu_start:
                System.out.println("TODO +++++++++++++++++++++++++++++ realise start metod, ListUsers.java:36");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        ContentValues   values;

        if (requestCode == 1 && resultCode == RESULT_OK) {
            values = data.getParcelableExtra("passenger");
            dbHelper.insertIntoDB(values);
            loadUserList();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}

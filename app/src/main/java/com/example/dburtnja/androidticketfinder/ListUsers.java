package com.example.dburtnja.androidticketfinder;

import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class ListUsers extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_users);

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
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}

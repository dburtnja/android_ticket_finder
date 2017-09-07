package com.example.dburtnja.androidticketfinder.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.dburtnja.androidticketfinder.view.MainView.Station;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by denys on 9/3/17.
 * This class help make operation with database
 */

public class DbHelper extends SQLiteOpenHelper {
    private static final int    DB_VERSION = 2;
    private static final String DB_NAME = "TicketFinderDB";

    public static final String  USER_TABLE_NAME = "users";
    public static final String  KEY_ID = "_id";
    public static final String  KEY_STATION_FROM = "from_s";
    public static final String  KEY_STATION_FROM_VALUE = "from_val";
    public static final String  KEY_STATION_TILL = "till_s";
    public static final String  KEY_STATION_TILL_VALUE = "till_val";
    public static final String  KEY_DATE_FROM = "date_from";
    public static final String  KEY_DATE_TILL = "date_till";
    public static final String  KEY_PLACE = "place";
    public static final String  KEY_FIRST_NAME = "first_name";
    public static final String  KEY_LAST_NAME = "last_name";
    public static final String  KEY_STUD = "stud";

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String  query;

        query = "CREATE TABLE " + USER_TABLE_NAME + "(" +
                KEY_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                KEY_STATION_FROM + " TEXT NOT NULL, " +
                KEY_STATION_FROM_VALUE + " INTEGER NOT NULL, " +
                KEY_STATION_TILL + " TEXT NOT NULL, " +
                KEY_STATION_TILL_VALUE + " INTEGER NOT NULL, " +
                KEY_DATE_FROM + " INTEGER NOT NULL, " +
                KEY_DATE_TILL + " INTEGER NOT NULL, " +
                KEY_PLACE + " INTEGER NOT NULL, " +
                KEY_FIRST_NAME + " TEXT NOT NULL, " +
                KEY_LAST_NAME + " TEXT NOT NULL, " +
                KEY_STUD + " TEXT)";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public long insertIntoDB(ContentValues passenger) {
        SQLiteDatabase  database;

        database = this.getWritableDatabase();
        return database.insert(USER_TABLE_NAME, null, passenger);
    }

//    public void getPassengerList(List<ContentValues> newList) {
//        SQLiteDatabase              database;
//        Cursor                      cursor;
//        ArrayList<ContentValues>    list;
//
//        list = new ArrayList<>();
//        database = this.getReadableDatabase();
//        cursor = database.query(USER_TABLE_NAME, null, null, null, null, null, null);
//        list = DbToList(cursor, list);
//        cursor.close();
//        database.close();
//        return list;
//    }

    public List<Passenger> getPassengerList() {
        SQLiteDatabase          database;
        Cursor                  cursor;
        ArrayList<Passenger>    list;

        list = new ArrayList<>();
        database = getReadableDatabase();
        cursor = database.query(USER_TABLE_NAME, null, null, null, null, null, null);
        DbToList(cursor, list);
        cursor.close();
        database.close();
        return list;
    }

    private void DbToList(Cursor cursor, ArrayList<Passenger> list) {
        Passenger   buffer;

        while (cursor.moveToNext()) {
            buffer = new Passenger();
            buffer.setFrom(new Station(
                    cursor.getString(cursor.getColumnIndex(KEY_STATION_FROM)),
                    cursor.getInt(cursor.getColumnIndex(KEY_STATION_FROM_VALUE))
            ));
            buffer.setTill(new Station(
                    cursor.getString(cursor.getColumnIndex(KEY_STATION_TILL)),
                    cursor.getInt(cursor.getColumnIndex(KEY_STATION_TILL_VALUE))
            ));
            buffer.setDate(
                    cursor.getLong(cursor.getColumnIndex(KEY_DATE_FROM)),
                    cursor.getLong(cursor.getColumnIndex(KEY_DATE_TILL))
            );
            buffer.setPlaces(cursor.getInt(cursor.getColumnIndex(KEY_PLACE)));
            buffer.setName(
                    cursor.getString(cursor.getColumnIndex(KEY_FIRST_NAME)),
                    cursor.getString(cursor.getColumnIndex(KEY_LAST_NAME))
            );
            buffer.setStud(cursor.getString(cursor.getColumnIndex(KEY_STUD)));
            list.add(buffer);
        }
    }

//    private ArrayList<ContentValues> DbToList(Cursor cursor, ArrayList<ContentValues> values) {
//        ContentValues               value;
//
//        while (cursor.moveToNext()) {
//            value = new ContentValues();
//            value.put(KEY_ID, cursor.getInt(cursor.getColumnIndex(KEY_ID)));
//            value.put(KEY_STATION_FROM, cursor.getString(cursor.getColumnIndex(KEY_STATION_FROM)));
//            value.put(KEY_STATION_FROM_VALUE, cursor.getInt(cursor.getColumnIndex(KEY_STATION_FROM_VALUE)));
//            value.put(KEY_STATION_TILL, cursor.getString(cursor.getColumnIndex(KEY_STATION_TILL)));
//            value.put(KEY_STATION_TILL_VALUE, cursor.getInt(cursor.getColumnIndex(KEY_STATION_TILL_VALUE)));
//            value.put(KEY_DATE_FROM, cursor.getInt(cursor.getColumnIndex(KEY_DATE_FROM)));
//            value.put(KEY_DATE_TILL, cursor.getInt(cursor.getColumnIndex(KEY_DATE_TILL)));
//            value.put(KEY_PLACE, cursor.getInt(cursor.getColumnIndex(KEY_PLACE)));
//            value.put(KEY_FIRST_NAME, cursor.getString(cursor.getColumnIndex(KEY_FIRST_NAME)));
//            value.put(KEY_LAST_NAME, cursor.getString(cursor.getColumnIndex(KEY_LAST_NAME)));
//            value.put(KEY_STUD, cursor.getString(cursor.getColumnIndex(KEY_STUD)));
//            values.add(value);
//        }
//        return values;
//    }values
}

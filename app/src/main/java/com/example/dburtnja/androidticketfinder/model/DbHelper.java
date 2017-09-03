package com.example.dburtnja.androidticketfinder.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by denys on 9/3/17.
 * This class help make operation with database
 */

public class DbHelper extends SQLiteOpenHelper {
    private static final int    DB_VERSION = 1;
    private static final String DB_NAME = "TicketFinderDB";

    public static final String  USER_TABLE_NAME = "users";
    public static final String  KEY_ID = "_id";
    public static final String  KEY_STATION_FROM = "from";
    public static final String  KEY_STATION_TILL = "till";
    public static final String  KEY_DATE_FROM = "date_from";
    public static final String  KEY_DATE_TILL = "date_till";
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
                KEY_STATION_TILL + " TEXT NOT NULL, " +
                KEY_DATE_FROM + " INTEGER NOT NULL, " +
                KEY_DATE_TILL + " INTEGER NOT NULL, " +
                KEY_FIRST_NAME + " TEXT NOT NULL, " +
                KEY_LAST_NAME + " TEXT NOT NULL, " +
                KEY_STUD + " TEXT)";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXIST " + USER_TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    private long insertIntoDB(ContentValues passenger) {
        SQLiteDatabase  database;

        database = this.getWritableDatabase();
        return database.insert(USER_TABLE_NAME, null, passenger);
    }

    private List<ContentValues> getPassengerList() {
        SQLiteDatabase  database;
        Cursor          cursor;
        ArrayList       list;
        ContentValues   buf;

        database = this.getReadableDatabase();
        cursor = database.query(USER_TABLE_NAME, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            list = DbToList(cursor);
        }
        cursor.close();
        database.close();
        return list;
    }

    private ArrayList<ContentValues> DbToList(Cursor cursor) {
        int id;
        int from;
        int till;
        int dateFrom;
        int dateTill;
        int firstName;
        int lastName;
        int stud;

        id = cursor.getColumnIndex(KEY_ID);
        from = cursor.getColumnIndex(KEY_STATION_FROM);
        till = cursor.getColumnIndex(KEY_STATION_TILL);
        dateFrom = cursor.getColumnIndex(KEY_DATE_FROM);
        dateTill = cursor.getColumnIndex(KEY_DATE_TILL);
        firstName = cursor.getColumnIndex(KEY_FIRST_NAME);
        lastName = cursor.getColumnIndex(KEY_LAST_NAME);
        stud = cursor.getColumnIndex(KEY_STUD);

        while (cursor.moveToNext()) {

        }
    }
}

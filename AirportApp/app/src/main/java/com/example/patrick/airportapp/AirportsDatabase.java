package com.example.patrick.airportapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by patrick on 22-10-2015.
 */
public class AirportsDatabase extends SQLiteAssetHelper {
    private static final String DATABASE_NAME = "airports.db";
    private static final int DATABASE_VERSION = 1;

    public AirportsDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    // Hier de CRUD methoden
    public Cursor getAirports() {
        SQLiteDatabase db = getReadableDatabase();

        //String query = "SELECT icao, name FROM airports WHERE iso_country = \"NL\"";
        String query = "SELECT * FROM airports WHERE iso_country = \"NL\"";
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        db.close();
        return c;
    }
}

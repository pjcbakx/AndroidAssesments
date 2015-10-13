package com.appsfromholland.contactcard;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Vincent on 4-10-2015.
 */
public class ContactCardDatabase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_TABLE_NAME = "persons";
    private static final String DATABASE_NAME = "contactcard.db";

    private static final String COLOMN_ID = "id";
    private static final String COLOMN_GENDER = "gender";
    private static final String COLOMN_TITLE = "title";
    private static final String COLOMN_FIRSTNAME = "firstname";
    private static final String COLOMN_LASTNAME = "lastname";
    private static final String COLOMN_STREET = "street";
    private static final String COLOMN_CITY = "city";
    private static final String COLOMN_STATE = "state";
    private static final String COLOMN_ZIPCODE = "zipcode";
    private static final String COLOMN_EMAIL = "email";
    private static final String COLOMN_USERNAME = "username";
    private static final String COLOMN_PHONE = "phone";
    private static final String COLOMN_CELL = "cell";
    private static final String COLOMN_NATIONALITY = "nationality";

    public ContactCardDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PERSON_TABLE = "CREATE TABLE " + DATABASE_TABLE_NAME +
        "(" +
                COLOMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLOMN_GENDER + " TEXT, " +
                COLOMN_TITLE + " TEXT, " +
                COLOMN_FIRSTNAME + " TEXT," +
                COLOMN_LASTNAME + " TEXT," +
                COLOMN_STREET + " TEXT, " +
                COLOMN_CITY + " TEXT, " +
                COLOMN_STATE + " TEXT, " +
                COLOMN_ZIPCODE + " TEXT, " +
                COLOMN_EMAIL + " TEXT, " +
                COLOMN_USERNAME + " TEXT, " +
                COLOMN_PHONE + " TEXT, " +
                COLOMN_CELL + " TEXT, " +
                COLOMN_NATIONALITY + " TEXT " +
        ")";
        db.execSQL(CREATE_PERSON_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_NAME);
        onCreate(db);
    }

    public void addCard(Person person) {
        ContentValues values = new ContentValues();
        values.put(COLOMN_GENDER, person.gender);
        values.put(COLOMN_TITLE, person.title);
        values.put(COLOMN_FIRSTNAME, person.firstname);
        values.put(COLOMN_LASTNAME, person.lastname);
        values.put(COLOMN_STREET, person.street);
        values.put(COLOMN_CITY, person.city);
        values.put(COLOMN_STATE, person.state);
        values.put(COLOMN_ZIPCODE, person.zipcode);
        values.put(COLOMN_EMAIL, person.email);
        values.put(COLOMN_USERNAME, person.username);
        values.put(COLOMN_PHONE, person.phone);
        values.put(COLOMN_CELL, person.cell);
        values.put(COLOMN_NATIONALITY, person.nationality);

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(DATABASE_TABLE_NAME, null, values);
        db.close();
    }

    public Cursor getCards() {
        SQLiteDatabase db = getReadableDatabase();

        String query = "SELECT * FROM persons";
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        db.close();
        return c;
    }

}

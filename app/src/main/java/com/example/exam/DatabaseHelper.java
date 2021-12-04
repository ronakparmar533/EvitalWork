package com.example.exam;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static android.content.ContentValues.TAG;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TABLE_NAME = "users";

    private static final String ID = "id";
    private static final String NAME = "names";
    private static final String NUMBERS = "numbers";
    private static final String CITY = "city";
    private static final String MONEY = "money";

    public DatabaseHelper(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NAME +" TEXT,"
               + NUMBERS +" TEXT,"
                + CITY +" TEXT,"
                + MONEY +" TEXT)";

        db.execSQL(createTable);
    }

    public boolean addData(String name , String number , String city , String money) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, name);
        contentValues.put(NUMBERS, number);
        contentValues.put(CITY, city);
        contentValues.put(MONEY, money);

        Log.d(TAG, "addData: Adding " + name + " to " + TABLE_NAME);

        long result = db.insert(TABLE_NAME, null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public boolean update(Long id, String money) {

        Log.d("msg" , id + "" + money);

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MONEY,money);
        long result = db.update(TABLE_NAME, contentValues, ID + "=?" ,
                new String[]{String.valueOf(id)});

        if (result == -1) {
            return false;
        } else {
            return true;
        }

    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

}

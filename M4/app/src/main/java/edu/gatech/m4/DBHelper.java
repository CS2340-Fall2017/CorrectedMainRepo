package edu.gatech.m4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Cesar Porcayo on 10/24/2017.
 */

class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "SQLiteRatReport1.db";
    private static final int DATABASE_VERSION = 13;
    private static final String REPORT_TABLE_NAME = "report";
    public static final String REPORT_COLUMN_ID = "_id";
    public static final String REPORT_COLUMN_NAME = "name";
    public static final String REPORT_COLUMN_DATE = "date";
    public static final String REPORT_COLUMN_LOCATIONTYPE = "locationType";
    public static final String REPORT_COLUMN_LONGITUDE = "longitude";
    public static final String REPORT_COLUMN_LATITUDE = "latitude";
    public static final String REPORT_COLUMN_ZIPCODE = "zipCode";
    public static final String REPORT_COLUMN_ADDRESS = "address";
    public static final String REPORT_COLUMN_CITY = "city";
    public static final String REPORT_COLUMN_BOROUGH = "borough";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + REPORT_TABLE_NAME + "(" +
                REPORT_COLUMN_ID + " INTEGER PRIMARY KEY, " +
                REPORT_COLUMN_NAME + " TEXT, " +
                REPORT_COLUMN_DATE + " TEXT, " +
                REPORT_COLUMN_LOCATIONTYPE + " TEXT, " +
                REPORT_COLUMN_LONGITUDE  + " TEXT, " +
                REPORT_COLUMN_LATITUDE   + " TEXT, " +
                REPORT_COLUMN_ZIPCODE    + " TEXT, " +
                REPORT_COLUMN_ADDRESS    + " TEXT, " +
                REPORT_COLUMN_CITY + " TEXT, " +
                REPORT_COLUMN_BOROUGH + " TEXT)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + REPORT_TABLE_NAME);
        onCreate(db);
    }

    public boolean insertReport(String name, String date, String locationType, String longitude, String latitude, String zipCode, String address, String city, String borough) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(REPORT_COLUMN_NAME, name);
        contentValues.put(REPORT_COLUMN_DATE, date);
        contentValues.put(REPORT_COLUMN_LOCATIONTYPE, locationType  );
        contentValues.put(REPORT_COLUMN_LONGITUDE, longitude    );
        contentValues.put(REPORT_COLUMN_LATITUDE, latitude  );
        contentValues.put(REPORT_COLUMN_ZIPCODE, zipCode    );
        contentValues.put(REPORT_COLUMN_ADDRESS, address    );
        contentValues.put(REPORT_COLUMN_CITY, city);
        contentValues.put(REPORT_COLUMN_BOROUGH, borough);
        db.insert(REPORT_TABLE_NAME, null, contentValues);
        return true;
    }



    public Cursor getReport(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res;
        res = db.rawQuery( "SELECT * FROM " + REPORT_TABLE_NAME + " WHERE " +
                REPORT_COLUMN_NAME + "=?", new String[] { id } );
        //res = db.rawQuery( "SELECT * FROM %s WHERE %s =?".format(REPORT_TABLE_NAME, REPORT_COLUMN_NAME), new String[] { id } );
        return res;
    }

    //Sam Test Method
    public Cursor getAllReports() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res;
        res = db.rawQuery( "SELECT * FROM " + REPORT_TABLE_NAME, null );
        return res;
    }

    public Cursor getDateRange(String startDate, String endDate) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res;
        res = db.rawQuery( "SELECT * FROM " + REPORT_TABLE_NAME + " WHERE " +
                            REPORT_COLUMN_DATE + " BETWEEN '" + startDate + "' AND '" + endDate + "'", null);
//        res = db.rawQuery( "SELECT * FROM " + REPORT_TABLE_NAME, null );
        return res;
    }

}

package com.fabian_nico_uni.youarewhatyoueat.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Singleton based DataBase connection and helper
 */
public class DBH extends SQLiteOpenHelper {
    private static DBH instance;

    private static final String LOG_TAG = DBH.class.getSimpleName();

    public static final String DB_NAME = "YouAreWhatYouEat.db";
    public static final int DB_VERSION = 7;

    private Context context;

    private DBH(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        Log.d(LOG_TAG, "DBHelper hat die Datenbank: "+getDatabaseName()+ " erzeugt.");
        this.context = context;
    }

    /**
     * returns the singleton
     */
    public static DBH getInstance (Context context) {
        if(DBH.instance == null) {
            DBH.instance = new DBH(context.getApplicationContext());
            DBH.instance.context = context;
        }
        return DBH.instance;
    }

    /**
     * returns the singleton only if there is one already
     */
    public static DBH getInstance() {
        if(DBH.instance == null) return null;
        return DBH.instance;
    }

    /**
     * Creates all DataBase tables
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            Log.d(LOG_TAG, "Die Tabelle: " + ProfileDBHelper.TABLE_NAME + " wird angelegt.");
            db.execSQL(ProfileDBHelper.SQL_CREATE);
            Log.d(LOG_TAG, "Die Tabelle: " + WeightDBHelper.TABLE_NAME + " wird angelegt.");
            db.execSQL(WeightDBHelper.SQL_CREATE);
            Log.d(LOG_TAG, "Die Tabelle: " + CaloriesDBHelper.TABLE_NAME + " wird angelegt.");
            db.execSQL(CaloriesDBHelper.SQL_CREATE);
        }
        catch (Exception ex) {
            Log.e(LOG_TAG, "Fehler beim Anlegen der Tabellen: " + ex.getMessage());
        }
    }

    /**
     * On a Version update all tables are regenerated
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try{
            Log.d(LOG_TAG, "Die Tabelle: " + ProfileDBHelper.TABLE_NAME + " wird gelöscht.");
            db.execSQL(ProfileDBHelper.SQL_DROP);
            Log.d(LOG_TAG, "Die Tabelle: " + WeightDBHelper.TABLE_NAME + " wird gelöscht.");
            db.execSQL(WeightDBHelper.SQL_DROP);
            Log.d(LOG_TAG, "Die Tabelle: " + CaloriesDBHelper.TABLE_NAME + " wird gelöscht.");
            db.execSQL(CaloriesDBHelper.SQL_DROP);
            onCreate(db);
        } catch (Exception e) {
            Log.e(LOG_TAG, "Fehler beim Löschen der Tabellen: " + e.getMessage());
        }
    }
}

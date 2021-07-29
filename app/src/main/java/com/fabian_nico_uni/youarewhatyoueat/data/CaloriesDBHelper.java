package com.fabian_nico_uni.youarewhatyoueat.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CaloriesDBHelper {
    public static final String LOG_TAG = CaloriesDBHelper.class.getSimpleName();

    public static final String TABLE_NAME = "calories";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_ID_PROFILE = "profile_id";
    public static final String COLUMN_CALORIES = "calories";
    public static final String COLUMN_TIMESTAMP = "ts";

    public static final String SQL_CREATE =
            "CREATE TABLE " + TABLE_NAME +
                    "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_ID_PROFILE + " INTEGER NOT NULL, " +
                    COLUMN_CALORIES + " INTEGER NOT NULL, " +
                    COLUMN_TIMESTAMP + " TEXT NOT NULL);";

    public static final String SQL_DROP = "DROP TABLE IF EXISTS "+TABLE_NAME;

    /**
     * Finds all Calories entries for a specific profile for the current Day
     * @param profileID The Profile's id
     * @return A List of Calorie objects
     */
    public static synchronized List<Calories> findAllForProfile(long profileID){
        Log.d(LOG_TAG, "Finding all calories for profile "+profileID);
        String today = LocalDate.now().toString()+"%";
        Cursor resultSet = DBH.getInstance().getReadableDatabase().query(TABLE_NAME, null, COLUMN_ID_PROFILE+" = ? AND "+COLUMN_TIMESTAMP+" LIKE ?", new String[]{Long.toString(profileID), today}, null, null, COLUMN_ID+" DESC", null);
        if(resultSet.getCount() > 0) {
            List<Calories> result = new ArrayList<Calories>();
            resultSet.moveToFirst();
            for(int i = 0;i < resultSet.getCount();i++) {
                long _id = resultSet.getLong(0);
                long profile_id = resultSet.getLong(1);
                int calories = resultSet.getInt(2);
                String timestamp = resultSet.getString(3);
                result.add(new Calories(_id, profile_id, calories, timestamp));
                resultSet.moveToNext();
            }
            return result;
        }
        return new ArrayList<Calories>();
    }

    /**
     * Adds a single Calories entry for a specific profile
     * @param profileID The Profile's id
     * @param calories the calorie value as integer
     * @return the id of the new entry (-1 if the query failed)
     */
    public static synchronized long addCalories(long profileID, int calories){
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID_PROFILE, profileID);
        values.put(COLUMN_CALORIES, calories);
        values.put(COLUMN_TIMESTAMP, LocalDateTime.now().toString());
        long result = DBH.getInstance().getWritableDatabase().insert(TABLE_NAME, null, values);
        Log.d(LOG_TAG, "Created Calories for profile"+profileID+" with id "+result);
        return result;
    }
}

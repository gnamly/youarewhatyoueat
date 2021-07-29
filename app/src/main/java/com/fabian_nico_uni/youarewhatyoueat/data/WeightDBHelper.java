package com.fabian_nico_uni.youarewhatyoueat.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class WeightDBHelper {
    public static final String LOG_TAG = WeightDBHelper.class.getSimpleName();

    public static final String TABLE_NAME = "weights";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_ID_PROFILE = "profile_id";
    public static final String COLUMN_WEIGHT = "weight";
    public static final String COLUMN_TIMESTAMP = "ts";

    public static final String SQL_CREATE =
            "CREATE TABLE " + TABLE_NAME +
                    "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_ID_PROFILE + " INTEGER NOT NULL, " +
                    COLUMN_WEIGHT + " INTEGER NOT NULL, " +
                    COLUMN_TIMESTAMP + " TEXT NOT NULL);";

    public static final String SQL_DROP = "DROP TABLE IF EXISTS "+TABLE_NAME;

    public static synchronized List<Weight> findAllForProfile(Context context, long profileID){
        Log.d(LOG_TAG, "Finding all weights for profile "+profileID);
        Cursor resultSet = DBH.getInstance(context).getReadableDatabase().query(TABLE_NAME, null, COLUMN_ID_PROFILE+" = ?", new String[]{Long.toString(profileID)}, null, null, COLUMN_ID+" DESC", null);
        if(resultSet.getCount() > 0) {
            List<Weight> result = new ArrayList<Weight>();
            resultSet.moveToFirst();
            for(int i = 0;i < resultSet.getCount();i++) {
                long _id = resultSet.getLong(0);
                long profile_id = resultSet.getLong(1);
                int weight = resultSet.getInt(2);
                String timestamp = resultSet.getString(3);
                result.add(new Weight(_id, profile_id, weight, timestamp));
                resultSet.moveToNext();
            }
            return result;
        }
        return new ArrayList<Weight>();
    }

    public static synchronized long addWeight(long profileID, int weight){
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID_PROFILE, profileID);
        values.put(COLUMN_WEIGHT, weight);
        values.put(COLUMN_TIMESTAMP, LocalDateTime.now().toString());
        long result = DBH.getInstance().getWritableDatabase().insert(TABLE_NAME, null, values);
        Log.d(LOG_TAG, "Created Weight for profile"+profileID+" with id "+result);
        return result;
    }
}

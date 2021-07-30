package com.fabian_nico_uni.youarewhatyoueat.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;

public class ProfileDBHelper {
    public static final String LOG_TAG = ProfileDBHelper.class.getSimpleName();

    public static final String TABLE_NAME = "profiles";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_NICKNAME = "nickname";
    public static final String COLUMN_BIRTHDAY = "birthday";
    public static final String COLUMN_HEIGHT = "height";
    public static final String COLUMN_COLOR = "color";
    public static final String COLUMN_ICON = "icon";
    public static final String COLUMN_MALE = "male";

    private static String allColumns[] = {COLUMN_ID, COLUMN_NAME, COLUMN_NICKNAME, COLUMN_BIRTHDAY, COLUMN_HEIGHT, COLUMN_COLOR, COLUMN_ICON};

    public static final String SQL_CREATE =
            "CREATE TABLE " + TABLE_NAME +
                    "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT NOT NULL, " +
                    COLUMN_NICKNAME + " TEXT NOT NULL, " +
                    COLUMN_BIRTHDAY + " TEXT NOT NULL, " +
                    COLUMN_HEIGHT + " INTEGER NOT NULL, " +
                    COLUMN_COLOR + " TEXT NOT NULL, " +
                    COLUMN_ICON + " TEXT NOT NULL," +
                    COLUMN_MALE + " INTEGER NOT NULL);";

    public static final String SQL_DROP = "DROP TABLE IF EXISTS "+TABLE_NAME;

    private static Lock dblock;

    public static synchronized long createProfile(Context context, String name, String nick, String birth, int height, int weight, boolean male) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_NICKNAME, nick);
        values.put(COLUMN_BIRTHDAY, birth);
        values.put(COLUMN_HEIGHT, height);
        values.put(COLUMN_COLOR, "#f78707");
        values.put(COLUMN_ICON, "ic_launcher_round.png");
        values.put(COLUMN_MALE, male ? 1 : 0);
        long result = DBH.getInstance(context).getWritableDatabase().insert(TABLE_NAME, null, values);
        Log.d(LOG_TAG, "Created Profile with id "+result);
        WeightDBHelper.addWeight(result, weight);
        return result;
    }

    public static synchronized Profile getById(long id, Context context) {
        Log.d(LOG_TAG, "Get By ID with id: "+id);
        Cursor resultSet = DBH.getInstance(context).getReadableDatabase().query(TABLE_NAME, null, "id = ?", new String[]{Long.toString(id)}, null, null, null, null);
        Log.d(LOG_TAG, "Get By ID with id: "+id+" found "+resultSet.getCount()+" results");
        if(resultSet.getCount() > 0) {
            resultSet.moveToFirst();
            long _id = resultSet.getLong(0);
            String username = resultSet.getString(1);
            String nickname = resultSet.getString(2);
            String birthday = resultSet.getString(3);
            int height = resultSet.getInt(4);
            String color = resultSet.getString(5);
            String icon = resultSet.getString(6);
            boolean male = resultSet.getInt(7) == 1;
            return new Profile(context, _id, username, nickname, birthday, height, color, icon, male);
        }
        return null;
    }

    public static synchronized List<SimpleProfile> getAll(Context context) {
        Cursor resultSet = DBH.getInstance().getReadableDatabase().query(TABLE_NAME, new String[]{COLUMN_ID, COLUMN_NAME, COLUMN_NICKNAME}, null, null, null, null, null, null);
        List<SimpleProfile> result = new ArrayList<>();
        if(resultSet.getCount() > 0){
            resultSet.moveToFirst();
            for(int i = 0;i < resultSet.getCount();i++) {
                long _id = resultSet.getLong(0);
                String username = resultSet.getString(1);
                String nickname = resultSet.getString(2);
                result.add(new SimpleProfile(context, _id, username, nickname));
                resultSet.moveToNext();
            }
        }
        return result;
    }

    public static synchronized boolean deleteProfile(long id) {
        int result = DBH.getInstance().getWritableDatabase().delete(TABLE_NAME, "id = ?", new String[] {Long.toString(id)});
        return result > 0;
    }

    public static synchronized Profile getLast(Context context) {
        Cursor resultSet = DBH.getInstance(context).getReadableDatabase().query(TABLE_NAME, null, null, null, null, null, COLUMN_ID+" DESC", "1");
        if(resultSet.getCount() > 0) {
            resultSet.moveToFirst();
            long _id = resultSet.getLong(0);
            String username = resultSet.getString(1);
            String nickname = resultSet.getString(2);
            String birthday = resultSet.getString(3);
            int height = resultSet.getInt(4);
            String color = resultSet.getString(5);
            String icon = resultSet.getString(6);
            boolean male = resultSet.getInt(7) == 1;
            return new Profile(context, _id, username, nickname, birthday, height, color, icon, male);
        }
        return null;
    }
}

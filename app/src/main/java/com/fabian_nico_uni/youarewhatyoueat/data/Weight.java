package com.fabian_nico_uni.youarewhatyoueat.data;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;

public class Weight {
    public static final String LOG_TAG = Weight.class.getSimpleName();

    public long id;
    public long profile_id;
    public int weight;
    public LocalDateTime timestamp;

    SimpleDateFormat dateFormat;

    public Weight(long id, long profile_id, int weight, String timestamp){
        dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss", Locale.GERMANY);

        this.id = id;
        this.profile_id = profile_id;
        this.weight = weight;
        try {
            this.timestamp = dateFormat.parse(timestamp).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        } catch (ParseException e){
            Log.e(LOG_TAG, e.getMessage());
        }
    }
}

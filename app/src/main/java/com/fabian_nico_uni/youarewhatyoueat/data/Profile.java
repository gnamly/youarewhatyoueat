package com.fabian_nico_uni.youarewhatyoueat.data;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * A Model of the DataBase table Profile
 */
public class Profile {
    public static final String LOG_TAG = Profile.class.getSimpleName();

    private Context ctx;

    public long id;
    public String name;
    public String nickname;
    public LocalDate birthday;
    public int height;
    public int color;
    public String icon;
    public boolean male;
    public int drink;
    public LocalDate drinkDate = null;
    public int age;
    public int maxDaily;

    public List<Weight> weights;
    public List<Calories> calories;
    public int caloriesToday = 0;

    SimpleDateFormat dateFormat;
    SimpleDateFormat dateFormat2;

    public Profile(Context context, long id, String name, String nick, String birthday, int height, String color, String icon, boolean male, int drink, String drinkTS){
        dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMANY);
        dateFormat2 = new SimpleDateFormat("yyyy-MM-dd", Locale.GERMANY);

        this.ctx = context;

        this.id = id;
        this.name = name;
        this.nickname = nick;
        try {
            this.birthday = dateFormat.parse(birthday).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        } catch (ParseException e) {
            Log.e(LOG_TAG, e.getMessage());
        }
        this.height = height;
        this.color = Color.parseColor(color);
        this.icon = icon;
        this.male = male;

        this.drink = drink;
        if(drinkTS != null) {
            try {
                this.drinkDate = dateFormat2.parse(drinkTS).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            } catch (ParseException e) {
                Log.e(LOG_TAG, e.getMessage());
            }
        }
        //Check if drinks are from this day and reset if not
        if(drinkDate != null){
            LocalDate today = LocalDate.now();
            if(drinkDate.getYear() != today.getYear() || drinkDate.getMonth() != today.getMonth() || drinkDate.getDayOfMonth() != today.getDayOfMonth()) {
                this.drink = 0;
                this.drinkDate = today;
                ProfileDBHelper.updateField(ProfileDBHelper.COLUMN_DRINK, this.drink, this.id);
                ProfileDBHelper.updateField(ProfileDBHelper.COLUMN_DRINK_TS, this.drinkDate.toString(), this.id);
            }
        }

        //calculate the age based on birthday
        this.age = Period.between(this.birthday, LocalDate.now()).getYears();

        //using https://www.tk.de/techniker/magazin/ernaehrung/uebergewicht-und-diaet/wie-viele-kalorien-pro-tag-2006758
        //as table for the daily used calories for each age group
        if(age < 19) maxDaily = male ? 2500 : 2000;
        else if (age < 25) maxDaily = male ? 2500 : 1900;
        else if (age < 51) maxDaily = male ? 2400 : 1900;
        else if (age < 65) maxDaily = male ? 2200 : 1800;
        else maxDaily = male ? 2000 : 1600;

        //get all Weight Models for this profile
        weights = WeightDBHelper.findAllForProfile(context, this.id);

        //get all calory models for this profile
        calories = CaloriesDBHelper.findAllForProfile(this.id);
        for(Calories cal : calories){
            caloriesToday += cal.calories;
        }
    }

    //conversion to SimpleProfile
    public SimpleProfile getSimple(){
        return new SimpleProfile(this.ctx, this.id, this.name, this.nickname);
    }
}

package com.fabian_nico_uni.youarewhatyoueat.data;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import com.fabian_nico_uni.youarewhatyoueat.ui.profile.CreateFragment;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

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
    public int age;
    public int maxDaylie;

    public List<Weight> weights;
    public List<Calories> calories;
    public int caloriesToday = 0;

    SimpleDateFormat dateFormat;

    public Profile(Context context, long id, String name, String nick, String birthday, int height, String color, String icon, boolean male){
        dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMANY);

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

        this.age = Period.between(this.birthday, LocalDate.now()).getYears();

        if(age < 19) maxDaylie = male ? 2500 : 2000;
        else if (age < 25) maxDaylie = male ? 2500 : 1900;
        else if (age < 51) maxDaylie = male ? 2400 : 1900;
        else if (age < 65) maxDaylie = male ? 2200 : 1800;
        else maxDaylie = male ? 2000 : 1600;

        weights = WeightDBHelper.findAllForProfile(context, this.id);

        calories = CaloriesDBHelper.findAllForProfile(this.id);
        for(Calories cal : calories){
            caloriesToday += cal.calories;
        }
    }

    public SimpleProfile getSimple(){
        return new SimpleProfile(this.ctx, this.id, this.name, this.nickname);
    }
}

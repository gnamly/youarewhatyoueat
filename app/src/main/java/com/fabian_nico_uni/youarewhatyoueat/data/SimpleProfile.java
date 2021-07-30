package com.fabian_nico_uni.youarewhatyoueat.data;

import android.content.Context;

/**
 * A Profile Model but with less attributes
 */
public class SimpleProfile {
    public static final String LOG_TAG = SimpleProfile.class.getSimpleName();

    public long id;
    public String name;
    public String nickname;

    private Context ctx;

    public SimpleProfile(Context context, long id, String name, String nick) {
        this.id = id;
        this.name = name;
        this.nickname = nick;

        this.ctx = context;
    }

    public Profile getFullProfile() {
        return ProfileDBHelper.getById(this.id, ctx);
    }

    @Override
    public String toString() {
        return nickname;
    }
}

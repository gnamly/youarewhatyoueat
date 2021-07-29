package com.fabian_nico_uni.youarewhatyoueat.data;

public interface CurrentProfileUpdateEvent {
    void onProfileUpdated(Profile current, boolean newProfile);
}

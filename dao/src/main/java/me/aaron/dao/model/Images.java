package me.aaron.dao.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by aaronchan on 16/4/30.
 */
public class Images {
    @SerializedName("small")
    private String mSmall;
    @SerializedName("large")
    private String mLarge;
    @SerializedName("medium")
    private String mMedium;

    public String getSmall() {
        return mSmall;
    }

    public void setSmall(String small) {
        mSmall = small;
    }

    public String getLarge() {
        return mLarge;
    }

    public void setLarge(String large) {
        mLarge = large;
    }

    public String getMedium() {
        return mMedium;
    }

    public void setMedium(String medium) {
        mMedium = medium;
    }
}

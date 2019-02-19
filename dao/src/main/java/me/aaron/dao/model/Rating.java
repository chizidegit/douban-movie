package me.aaron.dao.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by aaronchan on 16/4/30.
 */
public class Rating {
    @SerializedName("max")
    private int mMax;
    @SerializedName("min")
    private int mMin;
    @SerializedName("average")
    private float mAverage;

    public int getMax() {
        return mMax;
    }

    public void setMax(int max) {
        mMax = max;
    }

    public int getMin() {
        return mMin;
    }

    public void setMin(int min) {
        mMin = min;
    }

    public float getAverage() {
        return mAverage;
    }

    public void setAverage(float average) {
        mAverage = average;
    }
}

package me.aaron.dao.api.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by aaronchan on 16/4/30.
 */
public class BaseResponse {
    @SerializedName("start")
    private int mStart;
    @SerializedName("count")
    private int mCount;
    @SerializedName("total")
    private int mTotal;

    public int getStart() {
        return mStart;
    }

    public void setStart(int start) {
        mStart = start;
    }

    public int getCount() {
        return mCount;
    }

    public void setCount(int count) {
        mCount = count;
    }

    public int getTotal() {
        return mTotal;
    }

    public void setTotal(int total) {
        mTotal = total;
    }
}

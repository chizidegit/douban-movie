package me.aaron.dao.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by aaronchan on 16/5/2.
 */
public class Avatar implements Parcelable {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mSmall);
        dest.writeString(this.mLarge);
        dest.writeString(this.mMedium);
    }

    public Avatar() {
    }

    protected Avatar(Parcel in) {
        this.mSmall = in.readString();
        this.mLarge = in.readString();
        this.mMedium = in.readString();
    }

    public static final Creator<Avatar> CREATOR = new Creator<Avatar>() {
        @Override
        public Avatar createFromParcel(Parcel source) {
            return new Avatar(source);
        }

        @Override
        public Avatar[] newArray(int size) {
            return new Avatar[size];
        }
    };
}

package me.aaron.dao.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by aaronchan on 16/5/2.
 */
public class Celebrity implements Parcelable {
    @SerializedName("id")
    private String mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("avatars")
    private Avatar mAvatar;

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public Avatar getAvatar() {
        return mAvatar;
    }

    public void setAvatar(Avatar avatar) {
        mAvatar = avatar;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mId);
        dest.writeString(this.mName);
        dest.writeParcelable(this.mAvatar, flags);
    }

    public Celebrity() {
    }

    protected Celebrity(Parcel in) {
        this.mId = in.readString();
        this.mName = in.readString();
        this.mAvatar = in.readParcelable(Avatar.class.getClassLoader());
    }

    public static final Creator<Celebrity> CREATOR = new Creator<Celebrity>() {
        @Override
        public Celebrity createFromParcel(Parcel source) {
            return new Celebrity(source);
        }

        @Override
        public Celebrity[] newArray(int size) {
            return new Celebrity[size];
        }
    };
}

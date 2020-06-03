package com.stambulo.weatherfragment;

import android.os.Parcel;
import android.os.Parcelable;

public class WeatherContainer implements Parcelable {
    private int position = 0;
    private String cityName = "";

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public WeatherContainer() {
    }

    protected WeatherContainer(Parcel in) {
        position = in.readInt();
        cityName = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(position);
        dest.writeString(cityName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<WeatherContainer> CREATOR = new Creator<WeatherContainer>() {
        @Override
        public WeatherContainer createFromParcel(Parcel in) {
            return new WeatherContainer(in);
        }

        @Override
        public WeatherContainer[] newArray(int size) {
            return new WeatherContainer[size];
        }
    };
}
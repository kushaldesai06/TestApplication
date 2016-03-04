package com.example.kushaldesai.Parser;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kushal.desai on 3/3/2016.
 */
public class DataObject implements  Parcelable {
    public double lat,lng;
    public  int id;
    public String modeOfTransport,modeOfTransportTrain;
    public String name;

    public DataObject(){

    }

    public DataObject(Parcel in){
        lat =in.readDouble();
        lng =in.readDouble();
        id =in.readInt();
        modeOfTransport =in.readString();
        modeOfTransportTrain =in.readString();
        name = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(lat);
        dest.writeDouble(lng);
        dest.writeInt(id);
        dest.writeString(modeOfTransport);
        dest.writeString(modeOfTransportTrain);
        dest.writeString(name);
    }

    public static final Parcelable.Creator<DataObject> CREATOR = new Parcelable.Creator<DataObject>() {
        public DataObject createFromParcel(Parcel in) {
            return new DataObject(in);
        }

        public DataObject[] newArray(int size) {
            return new DataObject[size];
        }
    };
}

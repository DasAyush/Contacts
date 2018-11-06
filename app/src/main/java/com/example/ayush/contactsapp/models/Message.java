package com.example.ayush.contactsapp.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Message implements Parcelable {

    private String first;
    private String last;
    private String otp;
    private String time;

    public Message() {
    }

    public Message(String first, String last, String otp, String time) {
        this.first = first;
        this.last = last;
        this.otp = otp;
        this.time = time;
    }

    protected Message(Parcel in) {
        first = in.readString();
        last = in.readString();
        otp = in.readString();
        time = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(first);
        dest.writeString(last);
        dest.writeString(otp);
        dest.writeString(time);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Message> CREATOR = new Creator<Message>() {
        @Override
        public Message createFromParcel(Parcel in) {
            return new Message(in);
        }

        @Override
        public Message[] newArray(int size) {
            return new Message[size];
        }
    };

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

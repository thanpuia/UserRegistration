package com.lalthanpuiachhangte.userregistration.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {

    int id;
    String userName;
    String password;
    String email;
    String phone;

    public User() {
    }

    public User(int id, String userName, String password, String email, String phone) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.phone = phone;
    }

    protected User(Parcel in) {
        id = in.readInt();
        userName = in.readString();
        password = in.readString();
        email = in.readString();
        phone = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(userName);
        dest.writeString(password);
        dest.writeString(email);
        dest.writeString(phone);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}

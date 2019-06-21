package com.lalthanpuiachhangte.userregistration.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {

   String email;
   String username;
   String password;
   String phoneno;

    public User() {
    }

    public User(String email, String username, String password, String phoneno) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.phoneno = phoneno;
    }

    protected User(Parcel in) {
        email = in.readString();
        username = in.readString();
        password = in.readString();
        phoneno = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(email);
        dest.writeString(username);
        dest.writeString(password);
        dest.writeString(phoneno);
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }


}

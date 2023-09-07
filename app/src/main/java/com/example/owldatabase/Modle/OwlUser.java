package com.example.owldatabase.Modle;

import android.content.Context;
import android.graphics.Bitmap;

import com.example.owldatabase.SQLiteHandler_DataBase.SQLiteHandler;

import java.util.ArrayList;

public class OwlUser {
    private int userId;
    private String name;
    private String password;
    private String birth_Date;
    private String Joined_date;
    private Bitmap userProfilePic;


    public OwlUser() {
    }


    public OwlUser(int userId, String name, String password, String birth_Date, String joined_date, Bitmap userProfilePic) {
        this.userId = userId;
        this.name = name;
        this.password = password;
        this.birth_Date = birth_Date;
        this.Joined_date = joined_date;
        this.userProfilePic = userProfilePic;
    }

    public OwlUser( String name, String password, String birth_Date, String joined_date, Bitmap userProfilePic) {
        this.name = name;
        this.password = password;
        this.birth_Date = birth_Date;
        this.Joined_date = joined_date;
        this.userProfilePic = userProfilePic;
    }
    public OwlUser( String name, String password, String birth_Date, Bitmap userProfilePic) {
        this.name = name;
        this.password = password;
        this.birth_Date = birth_Date;
        this.userProfilePic = userProfilePic;
    }
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBirth_Date() {
        return birth_Date;
    }

    public void setBirth_Date(String birth_Date) {
        this.birth_Date = birth_Date;
    }

    public String getJoined_date() {
        return Joined_date;
    }

    public void setJoined_date(String joined_date) {
        Joined_date = joined_date;
    }

    public Bitmap getUserProfilePicBitMap() {
        return userProfilePic;
    }

    public void setUserProfilePicBitMap(Bitmap userProfilePic) {
        this.userProfilePic = userProfilePic;
    }

    private static OwlUser owlUser_static;
    public OwlUser getOwlUser_static(int UserID, Context context){
      if (owlUser_static ==null) {
          owlUser_static =new SQLiteHandler(context.getApplicationContext()).getUser(UserID);
          return owlUser_static;
      }
      return owlUser_static;
    }
}

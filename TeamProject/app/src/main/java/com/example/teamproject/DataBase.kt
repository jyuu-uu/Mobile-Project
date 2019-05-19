package com.example.teamproject

import android.provider.BaseColumns

class DataBases {

    class CreateDB : BaseColumns {
        companion object {
            val USERID = "userid"
            val NAME = "name"
            val AGE = "age"
            val GENDER = "gender"
            val _TABLENAME0 = "usertable"
            val _CREATE0 = ("create table if not exists " + _TABLENAME0 + "("
                    + BaseColumns._ID + " integer primary key autoincrement, "
                    + USERID + " text not null , "
                    + NAME + " text not null , "
                    + AGE + " integer not null , "
                    + GENDER + " text not null );")
        }
    }
}
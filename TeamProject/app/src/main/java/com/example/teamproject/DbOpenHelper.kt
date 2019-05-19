package com.example.teamproject

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.CursorFactory
import android.database.sqlite.SQLiteOpenHelper
import java.sql.SQLException


class DbOpenHelper(private val mCtx: Context) {
    private var mDBHelper: DatabaseHelper? = null

    private inner class DatabaseHelper(context: Context, name: String, factory: CursorFactory, version: Int) :
        SQLiteOpenHelper(context, name, factory, version) {

        override fun onCreate(db: SQLiteDatabase) {
            db.execSQL(DataBases.CreateDB._CREATE0)
        }

        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            db.execSQL("DROP TABLE IF EXISTS " + DataBases.CreateDB._TABLENAME0)
            onCreate(db)
        }
    }

    @Throws(SQLException::class)
    fun open(): DbOpenHelper {
  //      mDBHelper = DatabaseHelper(mCtx, DATABASE_NAME, null, DATABASE_VERSION)
        mDB = mDBHelper!!.writableDatabase
        return this
    }

    fun create() {
        mDBHelper!!.onCreate(mDB)
    }

    fun close() {
        mDB.close()
    }

    companion object {

        private val DATABASE_NAME = "InnerDatabase(SQLite).db"
        private val DATABASE_VERSION = 1
        lateinit var mDB: SQLiteDatabase
    }

    fun insertColumn(userid: String, name: String, age: Long, gender: String): Long {
        val values = ContentValues()
        values.put(DataBases.CreateDB.USERID, userid)
        values.put(DataBases.CreateDB.NAME, name)
        values.put(DataBases.CreateDB.AGE, age)
        values.put(DataBases.CreateDB.GENDER, gender)
        return mDB.insert(DataBases.CreateDB._TABLENAME0, null, values)
    }
}
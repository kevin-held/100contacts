package com.example.a100contacts

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?): SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        val query = ("CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY, " +
                CONTACT + " TEXT" + ")")
                /*
                FIRST_NAME + " TEXT," +
                LAST_NAME + " TEXT," +
                COMPANY_NAME + " TEXT," +
                ADDRESS + " TEXT," +
                CITY + " TEXT," +
                COUNTY + " TEXT," +
                STATE + " TEXT," +
                ZIP + " TEXT," +
                PHONE_ONE + " TEXT," +
                PHONE_TWO + " TEXT," +
                EMAIL + " TEXT" + ")")*/
        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    fun addLine(line: String) {
        val values = ContentValues()
        values.put(CONTACT, line)
        val db = this.writableDatabase
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun getLine(id: String): Cursor?{
        val db = this.readableDatabase
        return db.rawQuery("SELECT " + CONTACT + " FROM " + TABLE_NAME + " WHERE " + ID_COL + " = " + id, null)
    }
    companion object{
        private val DATABASE_NAME = "ONEHUNDREDCONTACTS"
        private val DATABASE_VERSION = 1
        val TABLE_NAME = "contacts"
        val ID_COL =  "id"
        val CONTACT = "contact"
        /*
        val FIRST_NAME = "first_name"
        val LAST_NAME = "last_name"
        val COMPANY_NAME = "company_name"
        val ADDRESS = "address"
        val CITY = "city"
        val COUNTY = "county"
        val STATE = "state"
        val ZIP = "zip"
        val PHONE_ONE = "phone_one"
        val PHONE_TWO = "phone_two"
        val EMAIL = "email"*/
    }
}
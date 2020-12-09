package com.example.homework5_2.DataBase

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.homework5_2.DataBase.DBService.Companion.BASE_NAME
import com.example.homework5_2.DataBase.DBService.Companion.BASE_VERSION
import com.example.homework5_2.DataBase.DBService.Companion.TABLE_COMMUNICATION
import com.example.homework5_2.DataBase.DBService.Companion.TABLE_CONNECT_TYPE
import com.example.homework5_2.DataBase.DBService.Companion.TABLE_ID
import com.example.homework5_2.DataBase.DBService.Companion.TABLE_NAME

class DBHelper(context: Context) : SQLiteOpenHelper(context, BASE_NAME, null, BASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE $BASE_NAME ( $TABLE_ID Integer Primary key autoincrement, $TABLE_NAME Text, $TABLE_COMMUNICATION Text, $TABLE_CONNECT_TYPE Integer)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
}
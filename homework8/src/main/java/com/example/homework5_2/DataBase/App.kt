package com.example.homework5_2.DataBase

import android.app.Application

class App : Application() {

    val dbHelper : DBHelper = DBHelper(this)
}
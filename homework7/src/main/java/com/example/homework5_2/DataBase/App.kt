package com.example.homework5_2.DataBase

import android.app.Application
import android.content.ContentValues
import com.example.homework5_2.Contact.ConnectType
import com.example.homework5_2.Contact.Contact

class App : Application() {
    val dbHelper : DBHelper? = DBHelper(this)
}
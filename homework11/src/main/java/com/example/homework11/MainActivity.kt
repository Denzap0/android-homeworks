package com.example.homework11

import android.annotation.SuppressLint
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.security.Permission
import java.security.Permissions
import java.util.*

class MainActivity : AppCompatActivity() {
    @SuppressLint("Recycle")
    private val contactsList = mutableListOf<Contact>()
    private lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recyclerView)
        val cursor = contentResolver.query(Uri.parse("content://com.homework11.contentprovider/data"), null,null,null,null)
        Log.d("AAAA", cursor.toString())
        if (cursor != null) {
            while (cursor.moveToNext()){
                contactsList.add(Contact(cursor.getString(1), cursor.getString(2),
                    cursor.getInt(3) != 0
                ))
            }
        }
        cursor?.close()
        recyclerView.apply {
            adapter = ContactsAdapter(contactsList)
            layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
        }

    }
}
package com.example.homework6

import FileRecyclerAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var files : MutableList<File> = ArrayList()
    private val listItemActionListener : ListItemActionListener = object : ListItemActionListener{
        override fun onItemClicked(number: File) {
            TODO("Not yet implemented")

        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        files.add(File("file", "eegeg"))
        Log.d("AAA", files.toString())
        filesRecyclerView.adapter = FileRecyclerAdapter(files, listItemActionListener)
        filesRecyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
    }
}

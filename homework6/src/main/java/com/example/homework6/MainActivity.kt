package com.example.homework6

import FileRecyclerAdapter
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.io.FileOutputStream
import java.util.zip.Inflater

class MainActivity : AppCompatActivity() {

    private var files : MutableList<File> = ArrayList()
    private var fileNames : MutableList<String> = ArrayList()
    private val listItemActionListener : ListItemActionListener = object : ListItemActionListener{
        override fun onItemClicked(file: File) {

        }

    }
    private val namesFile = "nameFile"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fos : FileOutputStream = applicationContext.openFileOutput(namesFile, Context.MODE_PRIVATE)
        for(i in 0..5){
            fileNames.add("Hello")
        }
        for (i in 0..5){
            FileOutputStream(File(filesDir, namesFile), true)
                .bufferedWriter()
                .use { out ->
                    out.append("text")
                    out.newLine()
                }

        }
//        val file : File = File(applicationContext.filesDir, "fileNames")

        Log.d("RESSSSS", String(file.readBytes()))
        filesRecyclerView.adapter = FileRecyclerAdapter(files, listItemActionListener)
        filesRecyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.add_file, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.menu.add_file){

        }
        return super.onOptionsItemSelected(item)
    }
}

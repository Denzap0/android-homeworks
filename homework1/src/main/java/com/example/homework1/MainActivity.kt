package com.example.homework1

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        if(resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
            val txtView: TextView = findViewById(R.id.horizont)
            txtView.visibility = View.VISIBLE
        }
    }


    
}

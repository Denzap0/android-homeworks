package com.example.homework10

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val mediaPlayer = MediaPlayer.create(this, R.raw.phonk)
        mediaPlayer.start()
        Thread.sleep(10000)
        mediaPlayer.stop()
    }
}
package com.example.homework4_2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;


import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Switch sw = findViewById(R.id.switchBar);
        Log.d("BBBB", sw.getTextOn().toString());
        Log.d("CCCC", sw.getTextOff().toString());
        final Circle circle = findViewById(R.id.circle);
        circle.setListener(new Circle.Listener() {
            @Override
            public void onEvent(float x, float y, Paint paint) {
                if(!sw.isChecked() ){
                    Toast toast = Toast.makeText(getApplicationContext(), "x: " + x + " y: " + y, Toast.LENGTH_SHORT);
                    toast.show();
                }else{
                    Snackbar snackbar = Snackbar.make(circle, "x: " + x + " y: " + y, Snackbar.LENGTH_SHORT)
                            .setTextColor(paint.getColor());
                    snackbar.show();
                }
            }
        });
    }
}

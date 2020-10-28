package com.example.homework22.Part1;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.homework22.R;

import java.util.ArrayList;

import static java.lang.String.valueOf;

public class Activity2 extends AppCompatActivity {

    private ArrayList<Integer> values = new ArrayList<>();

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        if(getIntent() != null) {
            Bundle arguments = getIntent().getExtras();

            if (arguments != null) {
                values = arguments.getIntegerArrayList("values");
            }
        }


        final ArrayList<String> resValues = new ArrayList<>();

        if(!values.isEmpty()) {
            resValues.add(valueOf(Calculations.sum(values)));
            resValues.add(valueOf(Calculations.sumArithmetic(values)));
            resValues.add(valueOf(Calculations.func(values)));

            TextView resultNotify = (TextView) findViewById(R.id.resultNotify);
            resultNotify.setText("Result is ready");
        }

        Button btn = (Button) findViewById(R.id.seeResult);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("result", resValues);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });

    }




}
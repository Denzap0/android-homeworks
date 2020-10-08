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

    ArrayList<Integer> values = new ArrayList<>();

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        Bundle arguments = getIntent().getExtras();
        if (arguments != null) {
            values = arguments.getIntegerArrayList("values");
        }


        final ArrayList<Double> resValues = new ArrayList<>();

        resValues.add((double) sum(values));
        resValues.add(sumArithmetic(values));
        resValues.add(func(values));

        TextView resultNotify = (TextView) findViewById(R.id.resultNotify);
        resultNotify.setText("Result is ready");

        Button btn = (Button) findViewById(R.id.seeResult);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity2.this, MainActivity.class);
                MyObserver.getINSTANCE().notify(resValues);
                finish();
            }
        });


    }

    protected double sumArithmetic(ArrayList<Integer> values) {
        return (double) sum(values) / values.size();
    }

    protected int sum(ArrayList<Integer> values) {

        int sum = 0;
        for (int i = 0; i < values.size(); i++) {
            sum += values.get(i);
        }
        return sum;
    }

    protected double func(ArrayList<Integer> values) {
        double sum = 0;
        for (int i = 0; i < values.size() / 2; i++) {
            sum += values.get(i);
        }
        double residual = values.get(values.size() / 2);
        for (int i = values.size() / 2 + 1; i < values.size(); i++) {
            residual -= values.get(i);
        }
        return sum / residual;
    }


}
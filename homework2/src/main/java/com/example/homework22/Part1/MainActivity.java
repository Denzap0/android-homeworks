package com.example.homework22.Part1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.homework22.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Integer> values = new ArrayList<>();
    private ArrayList<Double> resultForObserver = new ArrayList<>();

    private TextView textViewA;
    private TextView textViewB;
    private TextView textViewC;
    private TextView textViewD;
    private TextView sumTextView;
    private TextView sumArTextView;
    private TextView funcTextView;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewA = (TextView) findViewById(R.id.textViewA);
        textViewB = (TextView) findViewById(R.id.textViewB);
        textViewC = (TextView) findViewById(R.id.textViewC);
        textViewD = (TextView) findViewById(R.id.textViewD);
        sumTextView = (TextView) findViewById(R.id.sumTextView);
        sumArTextView = (TextView) findViewById(R.id.sumArTextView);
        funcTextView = (TextView) findViewById(R.id.funcTextView);

        final LinearLayout valuesLayout = findViewById(R.id.resultsLayout);
        final Button generateNumbersButton = (Button) findViewById(R.id.generateNumbers);
        generateNumbersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < 4; i++) {
                    values.add((int) (Math.random() * 99 + 1));
                }

                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < i; j++) {
                        if (values.get(i).equals(values.get(j))) {
                            values.remove(i);
                            values.add(i, (int) (Math.random() * 99 + 1));
                        }
                    }

                }
                textViewA.setText("a: " + values.get(0));
                textViewB.setText("b: " + values.get(1));
                textViewC.setText("c: " + values.get(2));
                textViewD.setText("d: " + values.get(3));

                generateNumbersButton.setBackgroundColor(Color.GREEN);
                textViewA.setBackgroundColor(Color.GREEN);
                textViewB.setBackgroundColor(Color.GREEN);
                textViewC.setBackgroundColor(Color.GREEN);
                textViewD.setBackgroundColor(Color.GREEN);
            }
        });

        Button countButton = (Button) findViewById(R.id.count);
        countButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (values.isEmpty()) {
                    TextView resultTextView = findViewById(R.id.resultTextView);
                    resultTextView.setText("Please generate numbers(press Generate numbers)");
                    ImageView arrow = findViewById(R.id.arrow);
                    arrow.setVisibility(View.VISIBLE);
                } else {
                    Intent intent = new Intent(MainActivity.this, Activity2.class);
                    intent.putIntegerArrayListExtra("values", values);
                    startActivityForResult(intent, 1);
                }
            }
        });


    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                if (data.getStringArrayListExtra("result") != null) {
                    ArrayList<String> result = data.getStringArrayListExtra("result");
                    if (!result.isEmpty()) {
                        sumTextView.setText("sum: " + result.get(0));
                        sumArTextView.setText("Arithmetic result: " + result.get(1));
                        funcTextView.setText("Function: " + result.get(2));
                    }
                }
            }
        }

    }
}

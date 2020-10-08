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

public class MainActivity extends AppCompatActivity {

    ArrayList<Integer> values = new ArrayList<>();
    ArrayList<Double> resultForObserver = new ArrayList<>();


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final LinearLayout valuesLayout = findViewById(R.id.resultsLayout);
        final Button generateNumbersButton = (Button) findViewById(R.id.generateNumbers);
        generateNumbersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < 4; i++) {
                    values.add((int) (Math.random() * 99 + 1));
                }
                TextView textViewA = (TextView) findViewById(R.id.textViewA);
                TextView textViewB = (TextView) findViewById(R.id.textViewB);
                TextView textViewC = (TextView) findViewById(R.id.textViewC);
                TextView textViewD = (TextView) findViewById(R.id.textViewD);

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
                Intent intent = new Intent(MainActivity.this, Activity2.class);
                intent.putIntegerArrayListExtra("values", values);
                startActivityForResult(intent, 1);
            }
        });




    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            if(resultCode == Activity.RESULT_OK && data != null){

                TextView sumTextView = (TextView) findViewById(R.id.sumTextView);
                TextView sumArTextView = (TextView) findViewById(R.id.sumArTextView);
                TextView funcTextView = (TextView) findViewById(R.id.funcTextView);
                if(data.getStringArrayListExtra("result") != null) {
                    ArrayList<String> result = data.getStringArrayListExtra("result");
                    if(!result.isEmpty()) {
                        sumTextView.setText("sum: " + result.get(0));
                        sumArTextView.setText("Arithmetic result: " + result.get(1));
                        funcTextView.setText("Function: " + result.get(2));
                    }
                }
            }
        }

    }
}

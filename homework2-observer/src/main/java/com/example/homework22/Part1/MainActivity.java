package com.example.homework22.Part1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.homework22.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Integer> values = new ArrayList<>();


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        TextView sumTextView = (TextView) findViewById(R.id.sumTextView);
        TextView sumArTextView = (TextView) findViewById(R.id.sumArTextView);
        TextView funcTextView = (TextView) findViewById(R.id.funcTextView);

        MyObserver.getINSTANCE().subscribe(new MyObserver.MyObserverActionListener() {
            @Override
            public void notifyDataChanged(ArrayList<Double> data) {
                if(data.size() == 3) {
                    TextView sumTextView = (TextView) findViewById(R.id.sumTextView);
                    TextView sumArTextView = (TextView) findViewById(R.id.sumArTextView);
                    TextView funcTextView = (TextView) findViewById(R.id.funcTextView);

                    sumTextView.setText("sum: " + data.get(0));
                    sumArTextView.setText("Arithmetic result: " + data.get(1));
                    funcTextView.setText("Function: " + data.get(2));
                }
            }
        });


        Button btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Activity2.class);
                intent.putIntegerArrayListExtra("values", values);
                startActivity(intent);
            }
        });




    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("AAA", "fafafa");
        if(requestCode == 1){
            if(resultCode == Activity.RESULT_OK && data != null){

            }
        }

    }
}

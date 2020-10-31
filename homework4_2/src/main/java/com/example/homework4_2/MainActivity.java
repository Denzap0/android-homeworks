package com.example.homework4_2;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Paint;
import android.os.Bundle;
import android.widget.Switch;
import android.widget.Toast;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    private Switch sw;
    private Circle circle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sw = findViewById(R.id.switchBar);
        circle = findViewById(R.id.circle);

        circle.setListener(new Circle.Listener() {
            @Override
            public void onEvent(float x, float y, Paint paint) {
                if (!sw.isChecked()) {
                    Toast toast = Toast.makeText(getApplicationContext(), "x: " + x + " y: " + y, Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    Snackbar snackbar = Snackbar.make(circle, "x: " + x + " y: " + y, Snackbar.LENGTH_SHORT)
                            .setTextColor(paint.getColor());
                    snackbar.show();
                }
            }
        });
    }
}

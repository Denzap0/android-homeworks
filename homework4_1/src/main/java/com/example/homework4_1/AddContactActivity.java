package com.example.homework4_1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.TreeMap;

public class AddContactActivity extends AppCompatActivity {

    ArrayList<String> contacts = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_contact_activity);

        final EditText editName = findViewById(R.id.edit_name);
        final EditText editCommunication = findViewById(R.id.edit_communication);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            contacts = bundle.getStringArrayList("contacts");
        }

        findViewById(R.id.result_add_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent();
                intent.putExtra("name", editName.getText().toString());
                intent.putExtra("communication", editCommunication.getText().toString());
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }
}

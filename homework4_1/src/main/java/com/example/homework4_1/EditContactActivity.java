package com.example.homework4_1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class EditContactActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_contact_activity);

        final EditText editName = findViewById(R.id.edit_name);
        final EditText editCommunication = findViewById(R.id.edit_communication);
        Button editButton = findViewById(R.id.edit_button);

        final Bundle bundle = getIntent().getExtras();

        if(bundle != null){
            editName.setHint(bundle.getString("old_name"));
            editCommunication.setHint(bundle.getString("old_communication"));

            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra("old_name", bundle.getString("old_name").toString());
                    intent.putExtra("new_name", editName.getText().toString());
                    intent.putExtra("new_communication", editCommunication.getText().toString());
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });
        }




    }
}

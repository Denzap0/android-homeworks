package com.example.homework4_1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.homework4_1.Contact.ConnectType;

import java.util.ArrayList;
import java.util.List;

public class EditContactActivity extends AppCompatActivity {

    private List<String> contacts;
    private EditText editCommunication;
    private EditText editName;
    private Button editButton;
    private Button removeButton;
    Switch sw;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_contact_activity);

        editName = findViewById(R.id.edit_name);
        editCommunication = findViewById(R.id.edit_communication);
        editButton = findViewById(R.id.edit_button);
        removeButton = findViewById(R.id.remove_contact);
        sw = findViewById(R.id.switchConnect);

        final Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            editName.setHint(bundle.getString("old_name"));
            editCommunication.setHint(bundle.getString("old_communication"));
            contacts = bundle.getStringArrayList("contacts");
            if(contacts == null){
                contacts = new ArrayList<>();
            }

            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (editName.getText().toString().isEmpty()) {
                        openAttentionEmptyDialog();
                    } else if (contacts.contains(editName.getText().toString())) {
                        openAttentionContainsDialog();
                    } else {
                        backToMainEdit(bundle);
                    }
                }
            });

            removeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    backToMainRemove(bundle);
                }
            });
        }


    }

    public void openAttentionEmptyDialog() {
        AlertEmptyDialog dialog = new AlertEmptyDialog();
        dialog.show(getSupportFragmentManager(), "Alert dialog");
    }

    public void openAttentionContainsDialog() {
        AlertContainsDialog dialog = new AlertContainsDialog();
        dialog.show(getSupportFragmentManager(), "Alert dialog");
    }

    private void backToMainEdit(Bundle bundle){
        Intent intent = new Intent();
        intent.putExtra("old_name", bundle.getString("old_name"));
        intent.putExtra("new_name", editName.getText().toString());
        intent.putExtra("new_communication", editCommunication.getText().toString());
        intent.putExtra("isRemove", false);
        if(sw.isChecked()){
            intent.putExtra("connectType", ConnectType.EMAIL);
        }else{
            intent.putExtra("connectType", ConnectType.PHONE);
        }
        setResult(RESULT_OK, intent);
        finish();
    }
    private void backToMainRemove(Bundle bundle){
        Intent intent = new Intent();
        intent.putExtra("old_name", bundle.getString("old_name".toString()));
        intent.putExtra("isRemove", true);
        setResult(RESULT_OK, intent);
        finish();
    }
}

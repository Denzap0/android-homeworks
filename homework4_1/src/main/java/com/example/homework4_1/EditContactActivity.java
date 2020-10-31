package com.example.homework4_1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.homework4_1.AlertDialogs.AlertContainsDialog;
import com.example.homework4_1.AlertDialogs.AlertEmptyDialog;
import com.example.homework4_1.Contact.ConnectType;
import com.example.homework4_1.Contact.Contact;

import java.util.ArrayList;
import java.util.List;

public class EditContactActivity extends AppCompatActivity {

    private Contact contact;
    private EditText editCommunication;
    private EditText editName;
    private Button editButton;
    private Button removeButton;
    private Switch communicationSwitch;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_contact_activity);

        Bundle bundle = new Bundle();
        editName = findViewById(R.id.edit_name);
        editCommunication = findViewById(R.id.edit_communication);
        editButton = findViewById(R.id.edit_button);
        removeButton = findViewById(R.id.remove_contact);
        communicationSwitch = findViewById(R.id.switchConnect);

        if(getIntent() != null){
            bundle = getIntent().getExtras();
            contact = (Contact) bundle.get("contact");
        }

        if (bundle != null && contact != null) {
            editName.setHint(contact.getName());
            editCommunication.setHint(contact.getCommunication());

        }

        final Bundle finalBundle = bundle;
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editName.getText().toString().isEmpty()) {
                    openAttentionEmptyDialog();
                } else if(contact != null){
                    backToMainEdit(finalBundle);
                }
            }
        });

        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToMainRemove(finalBundle);
            }
        });
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
        intent.putExtra("oldContact", contact);
        intent.putExtra("new_name", editName.getText().toString());
        intent.putExtra("new_communication", editCommunication.getText().toString());
        intent.putExtra("isRemove", false);
        if(communicationSwitch.isChecked()){
            intent.putExtra("connectType", ConnectType.EMAIL);
        }else{
            intent.putExtra("connectType", ConnectType.PHONE);
        }
        setResult(RESULT_OK, intent);
        finish();
    }
    private void backToMainRemove(Bundle bundle){
        Intent intent = new Intent();
        intent.putExtra("oldContact", contact);
        intent.putExtra("isRemove", true);
        setResult(RESULT_OK, intent);
        finish();
    }
}

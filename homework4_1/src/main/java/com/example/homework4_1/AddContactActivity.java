package com.example.homework4_1;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.homework4_1.AlertDialogs.AlertContainsDialog;
import com.example.homework4_1.AlertDialogs.AlertEmptyDialog;
import com.example.homework4_1.Contact.ConnectType;

import java.util.ArrayList;
import java.util.List;

public class AddContactActivity extends AppCompatActivity {

    private List<String> contacts;
    EditText editName;
    EditText editCommunication;
    Switch communicationSwich;
    Bundle bundle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_contact_activity);

        communicationSwich = findViewById(R.id.switchConnect);
        editName = findViewById(R.id.edit_name);
        editCommunication = findViewById(R.id.edit_communication);

        communicationSwich.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    editCommunication.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_mail_24,0,0,0);
                }else{
                    editCommunication.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_local_phone_24,0,0,0);
                }
            }
        });
        if(getIntent() != null) {
            bundle = getIntent().getExtras();
        }else{
            bundle = new Bundle();
        }
        if (bundle != null) {
            contacts = bundle.getStringArrayList("contacts");
            if(contacts == null){
                contacts = new ArrayList<>();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.tick_add_contact, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.add_tick){
            if(editName.getText().toString().isEmpty()){
                openAttentionEmptyDialog();
            }else if(contacts.contains(editName.getText().toString())){
                openAttentionContainsDialog();
            }
            else {
                backToMain();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void openAttentionEmptyDialog(){
        AlertEmptyDialog dialog = new AlertEmptyDialog();
        dialog.show(getSupportFragmentManager(), "Alert dialog");
    }

    public void openAttentionContainsDialog(){
        AlertContainsDialog dialog = new AlertContainsDialog();
        dialog.show(getSupportFragmentManager(), "Alert dialog");
    }

    private void backToMain(){
        Intent intent = new Intent();

        if(communicationSwich.isChecked()){
            intent.putExtra("name", editName.getText().toString());
            intent.putExtra("communication", editCommunication.getText().toString());
            intent.putExtra("connectType", ConnectType.EMAIL);
        }else{
            intent.putExtra("name", editName.getText().toString());
            intent.putExtra("communication", editCommunication.getText().toString());
            intent.putExtra("connectType", ConnectType.PHONE);
        }
        setResult(RESULT_OK, intent);
        finish();
    }


}

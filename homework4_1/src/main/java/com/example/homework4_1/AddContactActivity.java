package com.example.homework4_1;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class AddContactActivity extends AppCompatActivity {

    private ArrayList<String> contacts = new ArrayList<>();
    EditText editName;
    EditText editCommunication;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_contact_activity);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            contacts = bundle.getStringArrayList("contacts");
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
            editName = findViewById(R.id.edit_name);
            editCommunication = findViewById(R.id.edit_communication);
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
        intent.putExtra("name", editName.getText().toString());
        intent.putExtra("communication", editCommunication.getText().toString());
        setResult(RESULT_OK, intent);
        finish();
    }

}

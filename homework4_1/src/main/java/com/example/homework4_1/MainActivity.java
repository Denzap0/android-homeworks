package com.example.homework4_1;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homework4_1.Contact.ConnectType;
import com.example.homework4_1.Contact.Contact;
import com.example.homework4_1.Contact.ContactComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Contact> contacts = new ArrayList<>();
    private ContactComparator comparator;
    private RecyclerView recyclerView;
    private ListItemActionListener listItemActionListener = new ListItemActionListener() {
        @Override
        public void onItemClicked(Contact contact) {
            startEditActivity(contact);
        }
    };
    private ContactsAdapter adapter;

    interface ListItemActionListener {
        void onItemClicked(Contact contact);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        comparator = new ContactComparator();
        adapter = new ContactsAdapter(contacts, listItemActionListener);
        recyclerView = findViewById(R.id.contacts_list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        findViewById(R.id.add_contact_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddContactActivity.class);
                intent.putExtra("contacts", getNamesList());
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_for_contact, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                recyclerView.setAdapter(adapter);
                return false;
            }
        });
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            contacts.add(new Contact(data.getStringExtra("name"), data.getStringExtra("communication"), (ConnectType) data.getExtras().get("connectType")));
            Collections.sort(contacts, comparator);
            adapter.setContacts(contacts);

        }

        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            for (int i = 0; i < contacts.size(); i++) {
                if (contacts.get(i).getName().equals(data.getStringExtra("old_name"))) {
                    contacts.remove(i);
                }

            }
            if (!data.getBooleanExtra("isRemove", true)) {
                contacts.add(new Contact(data.getStringExtra("new_name"), data.getStringExtra("new_communication"), (ConnectType) data.getExtras().get("connectType")));
            }
            Collections.sort(contacts, comparator);
            adapter.setContacts(contacts);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        adapter = new ContactsAdapter(contacts, listItemActionListener);
        recyclerView.setAdapter(adapter);
    }

    private ArrayList<String> getNamesList() {
        ArrayList<String> namesList = new ArrayList<>();
        for (int i = 0; i < contacts.size(); i++) {
            namesList.add(contacts.get(i).getName());
        }
        return namesList;
    }

    private void startEditActivity(Contact contact) {

        Intent intent = new Intent(MainActivity.this, EditContactActivity.class);
        intent.putExtra("old_name", contact.getName());
        intent.putExtra("old_communication", contact.getCommunication());
        intent.putExtra("contacts", getNamesList().toString());
        startActivityForResult(intent, 2);
    }
}


package com.example.homework4_1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class MainActivity extends AppCompatActivity {

    static TreeMap<String, String> contacts = new TreeMap<>();

    RecyclerView recyclerView;
    ListItemActionListener listItemActionListener = new ListItemActionListener() {
        @Override
        public void onItemClicked(String name, String communication) {
            ArrayList<String> namesArrayList = new ArrayList<>(contacts.keySet());
            Intent intent = new Intent(MainActivity.this, EditContactActivity.class);
            intent.putExtra("old_name", name);
            intent.putExtra("old_communication", communication);
            intent.putExtra("contacts", namesArrayList);
            startActivityForResult(intent, 2);
        }
    };
    ContactsAdapter adapter;

    interface ListItemActionListener {
        void onItemClicked(String name, String communication);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.contacts_list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        findViewById(R.id.add_contact_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddContactActivity.class);
                ArrayList<String> namesArrayList = new ArrayList<>(contacts.keySet());
                intent.putExtra("contacts", namesArrayList);
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
            contacts.put(data.getStringExtra("name"), data.getStringExtra("communication"));
            adapter = new ContactsAdapter(contacts, listItemActionListener);
            recyclerView.setAdapter(adapter);

        }

        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            contacts.remove(data.getStringExtra("old_name"));
            if (data.getBooleanExtra("isRemove", true)) {
                adapter = new ContactsAdapter(contacts, listItemActionListener);
                recyclerView.setAdapter(adapter);
            } else {
                contacts.put(data.getStringExtra("new_name"), data.getStringExtra("new_communication"));
                adapter = new ContactsAdapter(contacts, listItemActionListener);
                recyclerView.setAdapter(adapter);
            }
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

}


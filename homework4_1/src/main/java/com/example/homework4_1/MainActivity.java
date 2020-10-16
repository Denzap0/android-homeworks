package com.example.homework4_1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.graphics.drawable.DrawableWrapper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class MainActivity extends AppCompatActivity {

    static TreeMap<String, String> contacts = new TreeMap<>();
    RecyclerView recyclerView;

    static {
        contacts.put("Denzap", "1234567");
        contacts.put("Masha", "@");
        contacts.put("Vasya", "13");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.contacts_list);
        recyclerView.setAdapter(new ContactsAdapter(contacts));
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK && data != null){
            contacts.put(data.getStringExtra("name"), data.getStringExtra("communication"));
            recyclerView.setAdapter(new ContactsAdapter(contacts));
        }

    }

    static class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ItemViewHolder> {

        private TreeMap<String, String> contacts;
        private int count = 0;

        public ContactsAdapter(TreeMap<String, String> contacts) {
            this.contacts = contacts;
        }

        @NonNull
        @Override
        public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact, parent, false);
            return new ItemViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
            int countLocal = 0;
            for (Map.Entry<String, String> entry : contacts.entrySet()) {
                if (countLocal == count) {
                    holder.bind(entry.getKey().toString(), entry.getValue().toString());
                    break;
                }
                countLocal++;
            }
            count++;
        }

        @Override
        public int getItemCount() {
            return contacts != null ? contacts.size() : 0;
        }

        static class ItemViewHolder extends RecyclerView.ViewHolder {

            ImageView contactIcon;
            TextView contactName;
            TextView contactCommunication;

            public ItemViewHolder(@NonNull View itemView) {
                super(itemView);

                contactIcon = itemView.findViewById(R.id.contact_icon);
                contactName = itemView.findViewById(R.id.contact_name);
                contactCommunication = itemView.findViewById(R.id.contact_communication);
            }

            public void bind(String contactName, String contactCommunication) {
                if (contactCommunication.contains("@")) {
                    this.contactIcon.setImageResource(R.drawable.ic_baseline_contact_mail_24);
                } else {
                    this.contactIcon.setImageResource(R.drawable.ic_baseline_contact_phone_24);
                }
                this.contactName.setText(contactName);
                this.contactCommunication.setText(contactCommunication);

            }
        }

    }
}


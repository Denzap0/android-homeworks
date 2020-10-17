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
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class MainActivity extends AppCompatActivity {

    static TreeMap<String, String> contacts = new TreeMap<>();

    RecyclerView recyclerView;
    ListItemActionListener listItemActionListener = new ListItemActionListener() {
        @Override
        public void onItemClicked(String name, String communication) {
            Intent intent = new Intent(MainActivity.this, EditContactActivity.class);
            intent.putExtra("old_name", name);
            intent.putExtra("old_communication", communication);
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
        adapter = new ContactsAdapter(contacts,listItemActionListener);
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
                adapter = new ContactsAdapter(contacts,listItemActionListener);
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
            contacts.put(data.getStringExtra("new_name"), data.getStringExtra("new_communication"));
            adapter = new ContactsAdapter(contacts,listItemActionListener);
            recyclerView.setAdapter(adapter);
        }


    }

    static class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ItemViewHolder> implements Filterable {

        private TreeMap<String, String> contactsLocal;
        private TreeMap<String, String> contactsAll;
        private int count = 0;
        private ListItemActionListener listItemActionListener;


        public ContactsAdapter(TreeMap<String, String> contacts, ListItemActionListener listItemActionListener) {
            this.contactsLocal = contacts;
            contactsAll = new TreeMap<>(contacts);
            this.listItemActionListener = listItemActionListener;
        }

        public void setContacts(TreeMap<String, String> contacts) {
            this.contactsLocal = contacts;
            contactsAll = new TreeMap<>(contacts);
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact, parent, false);
            return new ItemViewHolder(view, listItemActionListener);
        }

        @Override
        public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
            int countLocal = 0;
            for (Map.Entry<String, String> entry : contactsLocal.entrySet()) {
                if (countLocal == count) {
                    holder.bind(entry.getKey(), entry.getValue());
                    break;
                }
                countLocal++;
            }
            count++;
        }

        @Override
        public int getItemCount() {
            return contactsLocal != null ? contactsLocal.size() : 0;
        }

        @Override
        public Filter getFilter() {
            return contactsFilter;
        }

        private Filter contactsFilter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                TreeMap<String, String> filteredContacts = new TreeMap<>();
                if (constraint == null || constraint.length() == 0) {
                    filteredContacts.putAll(contactsAll);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();
                    for (Map.Entry<String, String> contact : contactsAll.entrySet()) {
                        if (contact.getKey().toLowerCase().contains(filterPattern)) {
                            filteredContacts.put(contact.getKey(), contact.getValue());

                        }
                    }
                }
                FilterResults results = new FilterResults();
                results.values = filteredContacts;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                contactsLocal.clear();
                contactsLocal.putAll((Map)results.values);
                notifyDataSetChanged();
            }
        };

        static class ItemViewHolder extends RecyclerView.ViewHolder {

            LinearLayout contactElement;
            ImageView contactIcon;
            TextView contactName;
            TextView contactCommunication;
            private ListItemActionListener listItemActionListener;

            public ItemViewHolder(@NonNull View itemView, ListItemActionListener listItemActionListener) {
                super(itemView);

                contactElement = itemView.findViewById(R.id.contact_element);
                contactIcon = itemView.findViewById(R.id.contact_icon);
                contactName = itemView.findViewById(R.id.contact_name);
                contactCommunication = itemView.findViewById(R.id.contact_communication);
                this.listItemActionListener = listItemActionListener;
            }

            public void bind(final String contactName, final String contactCommunication) {

                contactElement.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listItemActionListener != null) {
                            listItemActionListener.onItemClicked(contactName, contactCommunication);
                        }
                    }
                });
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


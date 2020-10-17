package com.example.homework4_1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Map;
import java.util.TreeMap;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ItemViewHolder> implements Filterable {

    private TreeMap<String, String> contactsLocal;
    private TreeMap<String, String> contactsAll;
    private int count = 0;
    private MainActivity.ListItemActionListener listItemActionListener;


    public ContactsAdapter(TreeMap<String, String> contacts, MainActivity.ListItemActionListener listItemActionListener) {
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
        private MainActivity.ListItemActionListener listItemActionListener;

        public ItemViewHolder(@NonNull View itemView, MainActivity.ListItemActionListener listItemActionListener) {
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

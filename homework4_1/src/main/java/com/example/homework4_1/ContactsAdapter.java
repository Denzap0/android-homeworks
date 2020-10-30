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

import com.example.homework4_1.Contact.ConnectType;
import com.example.homework4_1.Contact.Contact;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ItemViewHolder> implements Filterable {

    private List<Contact> contactsLocal = new ArrayList<>();
    private List<Contact> contactsAll = new ArrayList<>();
    private MainActivity.ListItemActionListener listItemActionListener;


    public ContactsAdapter(List<Contact> contacts, MainActivity.ListItemActionListener listItemActionListener) {
        contactsLocal.addAll(contacts);
        contactsAll.addAll(contacts);
        this.listItemActionListener = listItemActionListener;
    }

    public void setContacts(List<Contact> contacts) {
        contactsLocal.clear();
        contactsLocal.addAll(contacts);
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
        holder.bind(contactsLocal.get(position));

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
            List<Contact> filteredContacts = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredContacts.addAll(contactsAll);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Contact contact : contactsAll) {
                    if (contact.getName().toLowerCase().contains(filterPattern)) {
                        filteredContacts.add(contact);

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
            contactsLocal.addAll((Collection<? extends Contact>) results.values);
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

        public void bind(final Contact contact) {

            contactElement.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listItemActionListener != null) {
                        listItemActionListener.onItemClicked(contact);
                    }
                }
            });
            if (contact.getConnectType() == ConnectType.EMAIL) {
                this.contactIcon.setImageResource(R.drawable.ic_baseline_contact_mail_24);
            } else {
                this.contactIcon.setImageResource(R.drawable.ic_baseline_contact_phone_24);
            }
            this.contactName.setText(contact.getName());
            this.contactCommunication.setText(contact.getCommunication());

        }
    }

}

package com.example.homework5_2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filterable
import android.widget.Filter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import com.example.homework5_2.Contact.ConnectType
import com.example.homework5_2.Contact.Contact
import java.util.*

interface ListItemActionListener {
    fun onItemClicked(contact: Contact?)
}

class ContactsAdapter(
    contacts: List<Contact>?,
    listItemActionListener: ListItemActionListener
) :
    RecyclerView.Adapter<ContactsAdapter.ItemViewHolder>(), Filterable {
    private val contactsLocal = mutableListOf<Contact>()
    private val contactsAll = mutableListOf<Contact>()
    private var listItemActionListener: ListItemActionListener
        
    init {
        if(contacts != null) {
            contactsLocal.addAll(contacts)
            contactsAll.addAll(contacts)
        }
        this.listItemActionListener = listItemActionListener
    }
    public fun setContacts(contacts: List<Contact>?) {
        contactsAll.clear()
        contactsLocal.clear()
        if (contacts != null) {
            contactsAll.addAll(contacts)
            contactsLocal.addAll(contacts)
        }

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.contact, parent, false)
        return ItemViewHolder(view, listItemActionListener)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        contactsLocal[position].let { holder.bind(it) }
    }

    override fun getItemCount(): Int {
        return contactsLocal.size ?: 0
    }

    override fun getFilter(): Filter {
        return contactsFilter
    }

    private val contactsFilter: Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence): FilterResults {
            val filteredContacts = mutableListOf<Contact>()
            if (constraint.isEmpty()) {
                filteredContacts.addAll(contactsAll)
            } else {
                val filterPattern = constraint.toString().toLowerCase().trim { it <= ' ' }
                for (contact in contactsAll) {
                    if (contact.name.toLowerCase().contains(filterPattern)) {
                        filteredContacts.add(contact)
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredContacts
            return results
        }

        override fun publishResults(constraint: CharSequence, results: FilterResults) {
            contactsLocal.clear()
            contactsLocal.addAll(results.values as MutableList<Contact>)
            notifyDataSetChanged()
        }
    }

    class ItemViewHolder(
        itemView: View,
        listItemActionListener: ListItemActionListener?
    ) :
        RecyclerView.ViewHolder(itemView) {
        var contactElement: LinearLayout = itemView.findViewById(R.id.contact_element)
        var contactIcon: ImageView = itemView.findViewById(R.id.contact_icon)
        var contactName: TextView = itemView.findViewById(R.id.contact_name)
        var contactCommunication: TextView = itemView.findViewById(R.id.contact_communication)
        private val listItemActionListener: ListItemActionListener? =
            listItemActionListener

        fun bind(contact: Contact) {
            contactElement.setOnClickListener { listItemActionListener?.onItemClicked(contact) }
            val resId = if (contact.connectType == ConnectType.EMAIL) {
                R.drawable.ic_baseline_contact_mail_24
            } else {
                R.drawable.ic_baseline_contact_phone_24
            }
            contactIcon.setImageResource(resId)
            contactName.text = contact.name
            contactCommunication.text = contact.communication
        }

    }

    fun addItem(contact: Contact) {
        contactsAll.add(contact)
        contactsLocal.add(contact)
        notifyDataSetChanged()
    }
}



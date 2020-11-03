package com.example.homework5_2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.homework5_2.Contact.ConnectType
import com.example.homework5_2.Contact.Contact
import java.util.*

class ContactsAdapter(
    contacts: List<Contact>?,
    listItemActionListener: MainActivity.ListItemActionListener
) :
    RecyclerView.Adapter<ContactsAdapter.ItemViewHolder>(), Filterable {
    private val contactsLocal: MutableList<Contact>? = ArrayList()
    private val contactsAll: MutableList<Contact> = ArrayList()
    private val listItemActionListener: MainActivity.ListItemActionListener
        
    init {
        contactsLocal!!.addAll(contacts!!)
        contactsAll.addAll(contacts)
        this.listItemActionListener = listItemActionListener
    }
    public fun setContacts(contacts: List<Contact>?) {
        contactsAll.clear()
        contactsLocal!!.clear()
        contactsAll.addAll(contacts!!)
        contactsLocal.addAll(contacts)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.contact, parent, false)
        return ItemViewHolder(view, listItemActionListener)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(contactsLocal!![position])
    }

    override fun getItemCount(): Int {
        return contactsLocal?.size ?: 0
    }

    override fun getFilter(): Filter {
        return contactsFilter
    }

    private val contactsFilter: Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence): FilterResults {
            val filteredContacts: MutableList<Contact> = ArrayList()
            if (constraint == null || constraint.length == 0) {
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
            contactsLocal!!.clear()
            contactsLocal.addAll((results.values as Collection<Contact>))
            notifyDataSetChanged()
        }
    }

    class ItemViewHolder(
        itemView: View,
        listItemActionListener: MainActivity.ListItemActionListener?
    ) :
        RecyclerView.ViewHolder(itemView) {
        var contactElement: LinearLayout
        var contactIcon: ImageView
        var contactName: TextView
        var contactCommunication: TextView
        private val listItemActionListener: MainActivity.ListItemActionListener?
        fun bind(contact: Contact) {
            contactElement.setOnClickListener { listItemActionListener?.onItemClicked(contact) }
            if (contact.connectType == ConnectType.EMAIL) {
                contactIcon.setImageResource(R.drawable.ic_baseline_contact_mail_24)
            } else {
                contactIcon.setImageResource(R.drawable.ic_baseline_contact_phone_24)
            }
            contactName.text = contact.name
            contactCommunication.text = contact.communication
        }

        init {
            contactElement = itemView.findViewById(R.id.contact_element)
            contactIcon = itemView.findViewById(R.id.contact_icon)
            contactName = itemView.findViewById(R.id.contact_name)
            contactCommunication = itemView.findViewById(R.id.contact_communication)
            this.listItemActionListener = listItemActionListener
        }
    }

    fun addItem(contact: Contact) {
        contactsAll.add(contact)
        contactsLocal!!.add(contact)
        notifyDataSetChanged()
    }

    
}

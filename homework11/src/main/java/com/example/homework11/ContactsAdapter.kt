package com.example.homework11

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ContactsAdapter(private val contactsList : List<Contact>) : RecyclerView.Adapter<ContactsAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.contact,parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(contactsList[position])
    }

    override fun getItemCount(): Int = contactsList.size

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var contactIcon : ImageView = itemView.findViewById(R.id.contact_icon)
        var contactName : TextView = itemView.findViewById(R.id.contact_name)
        var contactConnect : TextView = itemView.findViewById(R.id.contact_communication)

        fun bind(contact: Contact){
            var resId : Int = if (contact.isEmail){
                R.drawable.ic_baseline_contact_mail_24
            }else{
                R.drawable.ic_baseline_contact_phone_24
            }
            contactIcon.setImageResource(resId)
            contactName.text = contact.name
            contactConnect.text = contact.communication
        }
    }
}
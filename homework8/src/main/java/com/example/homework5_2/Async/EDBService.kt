package com.example.homework5_2.Async

import android.content.Context
import com.example.homework5_2.Contact.Contact

interface EDBService {

    fun addContactToDB(contact: Contact, applicationContext: Context)

    fun updateContactInDB(oldContact: Contact, newContact: Contact, applicationContext: Context)

    fun deleteContactFromDB(contact: Contact, applicationContext: Context)

    fun getContactsFromDB(contacts : MutableList<Contact>, applicationContext: Context)
}
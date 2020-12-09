package com.example.homework5_2.Async

import com.example.homework5_2.Contact.Contact
import com.example.homework5_2.Listeners.AsyncCustomGetContactsListener
import com.example.homework5_2.Listeners.AsyncCustomListener

interface EDBService {

    fun addContactToDB(contact: Contact, asyncCustomListener: AsyncCustomListener)

    fun updateContactInDB(oldContact: Contact, newContact: Contact, asyncCustomListener: AsyncCustomListener)

    fun deleteContactFromDB(contact: Contact, asyncCustomListener: AsyncCustomListener)

    fun getContactsFromDB(asyncCustomGetContactsListener: AsyncCustomGetContactsListener)
}
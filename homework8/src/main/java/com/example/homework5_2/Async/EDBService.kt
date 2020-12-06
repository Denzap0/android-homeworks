package com.example.homework5_2.Async

import android.content.Context
import com.example.homework5_2.Contact.Contact
import com.example.homework5_2.DataBase.DBHelper
import io.reactivex.Completable

interface EDBService {

    fun addContactToDB(contact: Contact)

    fun updateContactInDB(oldContact: Contact, newContact: Contact)

    fun deleteContactFromDB(contact: Contact)

    fun getContactsFromDB()
}
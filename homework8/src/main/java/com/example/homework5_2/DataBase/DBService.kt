package com.example.homework5_2.DataBase

import android.content.ContentValues
import android.content.Context
import com.example.homework5_2.Contact.ConnectType
import com.example.homework5_2.Contact.Contact

class DBService {

    companion object {

        const val BASE_NAME = "ContactsBase"
        const val BASE_VERSION = 1

        const val TABLE_ID = "_id"
        const val TABLE_NAME = "name"
        const val TABLE_COMMUNICATION = "communication"
        const val TABLE_CONNECT_TYPE = "connect_type"

        fun addContactToDB(contact: Contact, dbHelper: DBHelper) {
            val contentValues = ContentValues().apply {
                put("name", contact.name)
                put("communication", contact.communication)
                put("connect_type", if (contact.connectType == ConnectType.PHONE) 0 else 1)
            }
            dbHelper.writableDatabase?.insert(
                "ContactsBase",
                null,
                contentValues
            )
        }

        fun updateContactInDB(
            oldContact: Contact,
            newContact: Contact,
            dbHelper: DBHelper
        ) {
            val contentValues = ContentValues().apply {
                put("name", newContact.name)
                put("communication", newContact.communication)
                put("connect_type", if (newContact.connectType == ConnectType.PHONE) 0 else 1)
            }
            dbHelper.writableDatabase?.update(
                "ContactsBase",
                contentValues,
                "name = ?",
                arrayOf(oldContact.name)
            )
        }

        fun deleteContactFromDB(contact: Contact, dbHelper: DBHelper) {
            dbHelper.writableDatabase?.delete(
                "ContactsBase",
                "name = ?",
                arrayOf(contact.name)
            )
        }

        fun getContactsFromDB(contacts : MutableList<Contact>, dbHelper: DBHelper){
            contacts.clear()
            val cursor = dbHelper.writableDatabase?.query(
                "ContactsBase",
                null,
                null,
                null,
                null,
                null,
                null)

            if(cursor != null) {
                val nameIndex = cursor.getColumnIndex("name")
                val communicationIndex = cursor.getColumnIndex("communication")
                val connectTypeIndex = cursor.getColumnIndex("connect_type")

                while (cursor.moveToNext()){
                    contacts.add(
                        Contact(cursor.getString(nameIndex),
                        cursor.getString(communicationIndex),
                        if(cursor.getInt(connectTypeIndex) == 0) ConnectType.PHONE else ConnectType.EMAIL)
                    )
                }
                cursor.close()
            }
        }
    }
}
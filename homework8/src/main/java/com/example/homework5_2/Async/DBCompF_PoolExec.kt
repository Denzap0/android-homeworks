package com.example.homework5_2.Async

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.example.homework5_2.AlertDialogs.LoadingDialog
import com.example.homework5_2.Contact.ConnectType
import com.example.homework5_2.Contact.Contact
import com.example.homework5_2.DataBase.App
import java.util.concurrent.*

class DBCompF_PoolExec(private val activity: Activity, private val mainExecutor: Executor) : EDBService {

    private lateinit var future : CompletableFuture<Boolean>

    override fun addContactToDB(contact: Contact, applicationContext: Context) {
        val loadingDialog = LoadingDialog(activity)
        loadingDialog.startLoadingDialog()
        future = CompletableFuture.runAsync {
            val contentValues = ContentValues().apply {
                put("name", contact.name)
                put("communication", contact.communication)
                put("connect_type", if (contact.connectType == ConnectType.PHONE) 0 else 1)
            }
            (applicationContext as App).dbHelper?.writableDatabase?.insert(
                "ContactsBase",
                null,
                contentValues
            )
        }.thenApply { true }
        loadingDialog.dismissDialog()
    }

    override fun updateContactInDB(
        oldContact: Contact,
        newContact: Contact,
        applicationContext: Context
    ) {
        val loadingDialog = LoadingDialog(activity)
        loadingDialog.startLoadingDialog()
        future = CompletableFuture.runAsync{
            val contentValues = ContentValues().apply {
                put("name", newContact.name)
                put("communication", newContact.communication)
                put("connect_type", if (newContact.connectType == ConnectType.PHONE) 0 else 1)
            }
            (applicationContext as App).dbHelper?.writableDatabase?.update(
                "ContactsBase",
                contentValues,
                "name = ?",
                arrayOf(oldContact.name)
            )
        }.thenApply { true }
        loadingDialog.dismissDialog()
    }

    override fun deleteContactFromDB(contact: Contact, applicationContext: Context) {
        val loadingDialog = LoadingDialog(activity)
        loadingDialog.startLoadingDialog()
        future = CompletableFuture.runAsync{
            (applicationContext as App).dbHelper?.writableDatabase?.delete(
                "ContactsBase",
                "name = ?",
                arrayOf(contact.name)
            )
        }.thenApply { true }
        loadingDialog.dismissDialog()
    }

    override fun getContactsFromDB(contacts: MutableList<Contact>, applicationContext: Context) {
        val loadingDialog = LoadingDialog(activity)
        loadingDialog.startLoadingDialog()
        future = CompletableFuture.supplyAsync {
            contacts.clear()
            val cursor = (applicationContext as App).dbHelper?.writableDatabase?.query(
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
            return@supplyAsync true
        }
        loadingDialog.dismissDialog()

    }

    public fun isDone() : Boolean = future.getNow(false)

}
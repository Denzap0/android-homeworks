package com.example.homework5_2.Async

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import com.example.homework5_2.AlertDialogs.LoadingDialog
import com.example.homework5_2.Contact.ConnectType
import com.example.homework5_2.Contact.Contact
import com.example.homework5_2.DataBase.App
import com.example.homework5_2.DataBase.DBHelper
import com.example.homework5_2.DataBase.DBService
import com.example.homework5_2.Listeners.AsyncCustomListener
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executor

class DBCompF_PoolExec(
    private val mainExecutor: Executor,
    private val dbHelper: DBHelper,
    private val asyncCustomListener: AsyncCustomListener
) : EDBService {

    private lateinit var future: CompletableFuture<Boolean>

    override fun addContactToDB(contact: Contact) {
        future = CompletableFuture.supplyAsync({
            asyncCustomListener.onStart()
        }, mainExecutor)
            .thenApplyAsync {
            DBService.addContactToDB(contact, dbHelper)
        }.thenApplyAsync({
                asyncCustomListener.onStop()
                true
            }, mainExecutor)
    }

    override fun updateContactInDB(
        oldContact: Contact,
        newContact: Contact
    ) {
        future = CompletableFuture.supplyAsync({
            asyncCustomListener.onStart()
        }, mainExecutor)
            .thenApplyAsync {
                DBService.updateContactInDB(oldContact, newContact, dbHelper)
            }.thenApplyAsync({
                asyncCustomListener.onStop()
                true
            }, mainExecutor)

    }

    override fun deleteContactFromDB(contact: Contact) {
        future = CompletableFuture.supplyAsync({
            asyncCustomListener.onStart()
        }, mainExecutor)
            .thenApplyAsync {
                DBService.deleteContactFromDB(contact, dbHelper)
            }.thenApplyAsync({
                asyncCustomListener.onStop()
                true
            }, mainExecutor)
    }

    override fun getContactsFromDB() {
        var contacts = mutableListOf<Contact>()
        future = CompletableFuture.supplyAsync({
            asyncCustomListener.onStart()
        }, mainExecutor)
            .thenApplyAsync {
                contacts = DBService.getContactsFromDB(dbHelper)
            }.thenApplyAsync({
                asyncCustomListener.getContacts(contacts)
                asyncCustomListener.onStop()
                true
            }, mainExecutor)
    }

}


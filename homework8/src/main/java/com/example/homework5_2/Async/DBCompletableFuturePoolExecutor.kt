package com.example.homework5_2.Async

import com.example.homework5_2.Contact.Contact
import com.example.homework5_2.DataBase.DBHelper
import com.example.homework5_2.DataBase.DBService
import com.example.homework5_2.Listeners.AsyncCustomGetContactsListener
import com.example.homework5_2.Listeners.AsyncCustomListener
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executor

class DBCompletableFuturePoolExecutor(
    private val mainExecutor: Executor,
    private val dbHelper: DBHelper
) : EDBService {

    private lateinit var future: CompletableFuture<Boolean>

    override fun addContactToDB(contact: Contact,asyncCustomListener: AsyncCustomListener) {
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
        newContact: Contact,
        asyncCustomListener: AsyncCustomListener
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

    override fun deleteContactFromDB(contact: Contact, asyncCustomListener: AsyncCustomListener) {
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

    override fun getContactsFromDB(asyncCustomGetContactsListener: AsyncCustomGetContactsListener) {
        var contacts = mutableListOf<Contact>()
        future = CompletableFuture.supplyAsync({
            asyncCustomGetContactsListener.onStart()
        }, mainExecutor)
            .thenApplyAsync {
                contacts = DBService.getContactsFromDB(dbHelper)
            }.thenApplyAsync({
                asyncCustomGetContactsListener.onStop(contacts)
                true
            }, mainExecutor)
    }

}


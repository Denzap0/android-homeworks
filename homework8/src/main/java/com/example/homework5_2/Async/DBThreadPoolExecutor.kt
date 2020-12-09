package com.example.homework5_2.Async

import android.os.Handler
import com.example.homework5_2.Contact.Contact
import com.example.homework5_2.DataBase.DBHelper
import com.example.homework5_2.DataBase.DBService
import com.example.homework5_2.Listeners.AsyncCustomGetContactsListener
import com.example.homework5_2.Listeners.AsyncCustomListener
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

class DBThreadPoolExecutor(private val dbHelper: DBHelper, private val handler : Handler) :
    EDBService {


    private val ex = ThreadPoolExecutor(1, 3, 1, TimeUnit.MILLISECONDS, LinkedBlockingQueue())

    override fun addContactToDB(contact: Contact, asyncCustomListener : AsyncCustomListener) {
        handler.post {
            asyncCustomListener.onStart()
        }
        ex.submit {
            DBService.addContactToDB(contact, dbHelper)
        }
        handler.post {
            asyncCustomListener.onStop()
        }
        ex.shutdown()
    }

    override fun updateContactInDB(
        oldContact: Contact,
        newContact: Contact,
        asyncCustomListener : AsyncCustomListener
    ) {
        handler.post {
            asyncCustomListener.onStart()
        }
        ex.submit {
            DBService.updateContactInDB(oldContact, newContact, dbHelper)
        }
        handler.post {
            asyncCustomListener.onStop()
        }
        ex.shutdown()
    }

    override fun deleteContactFromDB(contact: Contact, asyncCustomListener : AsyncCustomListener) {

        handler.post {
            asyncCustomListener.onStart()
        }
        ex.submit {
            DBService.deleteContactFromDB(contact, dbHelper)
        }
        handler.post {
            asyncCustomListener.onStop()
        }
        ex.shutdown()
    }

    override fun getContactsFromDB(asyncCustomGetContactsListener: AsyncCustomGetContactsListener) {
        var contacts = mutableListOf<Contact>()
        handler.post {
            asyncCustomGetContactsListener.onStart()
        }

        ex.submit {
            contacts = DBService.getContactsFromDB(dbHelper)
        }
        handler.post {
            asyncCustomGetContactsListener.onStop(contacts)
        }
        ex.shutdown()
    }



}
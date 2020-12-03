package com.example.homework5_2.Async

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.os.Handler
import android.os.Looper
import com.example.homework5_2.AlertDialogs.LoadingDialog
import com.example.homework5_2.Contact.ConnectType
import com.example.homework5_2.Contact.Contact
import com.example.homework5_2.DataBase.App
import com.example.homework5_2.DataBase.DBService
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

class DBThreadPoolExecutor(private val activity: Activity) : EDBService {

    private val ex = ThreadPoolExecutor(1, 3, 1, TimeUnit.MILLISECONDS, LinkedBlockingQueue())
    private lateinit var handler: Handler

    fun prepareHandler(mainLooper: Looper) {
        handler = Handler(mainLooper)
    }

    fun isStopped(): Boolean {
        return ex.awaitTermination(20, TimeUnit.SECONDS)
    }

    override fun addContactToDB(contact: Contact, applicationContext: Context) {
        val loadingDialog = LoadingDialog(activity)
        handler.post {
            loadingDialog.startLoadingDialog()
        }
        ex.submit {
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
        }
        handler.post {
            LoadingDialog(activity).dismissDialog()
        }
        ex.shutdown()
    }

    override fun updateContactInDB(
        oldContact: Contact,
        newContact: Contact,
        applicationContext: Context
    ) {
        val loadingDialog = LoadingDialog(activity)
        handler.post {
            loadingDialog.startLoadingDialog()
        }
        ex.submit {

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
        }
        handler.post {
            loadingDialog.dismissDialog()
        }
        ex.shutdown()
    }

    override fun deleteContactFromDB(contact: Contact, applicationContext: Context) {
        val loadingDialog = LoadingDialog(activity)
        handler.post {
            loadingDialog.startLoadingDialog()
        }
        ex.submit {
            DBService.deleteContactFromDB(contact,applicationContext)
        }
        handler.post {
            loadingDialog.dismissDialog()
        }
        ex.shutdown()
    }

    override fun getContactsFromDB(contacts: MutableList<Contact> , applicationContext: Context) {
        val loadingDialog = LoadingDialog(activity)
        handler.post {
            loadingDialog.startLoadingDialog()
        }

        ex.submit {
            DBService.getContactsFromDB(contacts,applicationContext)
        }
        handler.post {
            loadingDialog.dismissDialog()
        }
        ex.shutdown()
    }


}
package com.example.homework5_2.Async

import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues
import android.content.Context
import android.os.Looper
import com.example.homework5_2.Contact.ConnectType
import com.example.homework5_2.Contact.Contact
import com.example.homework5_2.DataBase.App
import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import android.os.Handler
import com.example.homework5_2.AlertDialogs.LoadingDialog

class DBThreadPoolExecutor(private val activity: Activity) : EDBService {

    private val ex = ThreadPoolExecutor(1, 3, 1, TimeUnit.MILLISECONDS, LinkedBlockingQueue())
    private lateinit var handler : Handler

    override fun addContactToDB(contact: Contact, applicationContext: Context) {
        val loadingDialog = LoadingDialog(activity)
        handler.post {
            loadingDialog.startLoadingDialog()
        }
        ex.submit {
            handler.post {

            }
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
        applicationContext: Context) {
        val loadingDialog = LoadingDialog(activity)
        handler.post {
            loadingDialog.startLoadingDialog()
        }
        ex.submit{

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
        ex.submit{
            (applicationContext as App).dbHelper?.writableDatabase?.delete(
                "ContactsBase",
                "name = ?",
                arrayOf(contact.name)
            )
        }
        handler.post {
            loadingDialog.dismissDialog()
        }
        ex.shutdown()
    }

    override fun getContactsFromDB(contacts: MutableList<Contact>, applicationContext: Context) {
        val loadingDialog = LoadingDialog(activity)
        handler.post {
            loadingDialog.startLoadingDialog()
        }

        ex.submit{
            contacts.clear()
            val cursor = (applicationContext as App).dbHelper?.readableDatabase?.query(
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
        handler.post {
            loadingDialog.dismissDialog()
        }
        ex.shutdown()

    }

    override fun prepareHandler(mainLooper : Looper) {
        handler = Handler(mainLooper)
    }

    override fun isTerminated() : Boolean{
        return ex.awaitTermination(20, TimeUnit.SECONDS)
    }
}
package com.example.homework5_2.Visibility

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.homework5_2.AlertDialogs.AlertEmptyDialog
import com.example.homework5_2.AlertDialogs.LoadingDialog
import com.example.homework5_2.Async.DBCompletableFuturePoolExecutor
import com.example.homework5_2.Async.DBRxJava
import com.example.homework5_2.Async.DBThreadPoolExecutor
import com.example.homework5_2.Async.EDBService
import com.example.homework5_2.Contact.ConnectType
import com.example.homework5_2.Contact.Contact
import com.example.homework5_2.DataBase.App
import com.example.homework5_2.Listeners.AsyncCustomListener
import com.example.homework5_2.R
import com.example.homework5_2.Settings.AsyncSettingsPreference
import kotlinx.android.synthetic.main.edit_contact_activity.*

class EditContactActivity : AppCompatActivity() {

    var bundle: Bundle? = null
    private lateinit var contact: Contact

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_contact_activity)


        if (intent != null) {
            bundle = intent.extras
            contact = bundle!!.getSerializable("contact") as Contact
        }
        if (bundle != null) {
            edit_name.hint = contact.name
            edit_communication.hint = contact.communication


        }
        edit_button.setOnClickListener {
            if (edit_name.text.toString().isEmpty()) {
                openAttentionEmptyDialog()
            } else {
                backToMainEdit()
            }
        }
        remove_contact.setOnClickListener { backToMainRemove() }
    }

    private fun openAttentionEmptyDialog() {
        val dialog = AlertEmptyDialog()
        dialog.show(supportFragmentManager, "Alert dialog")
    }

    private fun backToMainEdit() {

        val newContact = Contact(
            edit_name!!.text.toString(),
            edit_communication!!.text.toString(),
            if (switchConnect.isChecked) ConnectType.EMAIL else ConnectType.PHONE
        )
        updateDBContactWithSavedAsyncType(newContact)
    }

    private fun backToMainRemove() {
        deleteDBContactWithSavedAsyncType()
    }

    private fun deleteDBContactWithSavedAsyncType() {
        val asyncType = AsyncSettingsPreference(getSharedPreferences("asyncType", MODE_PRIVATE))
        val loadingDialog = LoadingDialog(this)
        when (asyncType.loadAsyncType()) {
            1 -> {
                val dbThreadPoolExecutor = DBThreadPoolExecutor(
                    (application as App).dbHelper,
                    Handler(mainLooper)
                )as EDBService
                dbThreadPoolExecutor.deleteContactFromDB(contact, object : AsyncCustomListener{
                    override fun onStart() {
                        loadingDialog.startLoadingDialog()
                    }

                    override fun onStop() {
                        loadingDialog.dismissDialog()
                        closeActivityRemove()
                    }

                })
            }
            2 -> {
                val db = DBCompletableFuturePoolExecutor(
                    mainExecutor,
                    (application as App).dbHelper
                )as EDBService
                db.deleteContactFromDB(contact, object : AsyncCustomListener{
                    override fun onStart() {
                        loadingDialog.startLoadingDialog()
                    }

                    override fun onStop() {
                        loadingDialog.dismissDialog()
                        closeActivityRemove()
                    }

                })
            }
            3 -> {
                val db = DBRxJava(
                    (application as App).dbHelper
                )as EDBService
                db.deleteContactFromDB(contact, object : AsyncCustomListener{
                    override fun onStart() {
                        loadingDialog.startLoadingDialog()
                    }

                    override fun onStop() {
                        loadingDialog.dismissDialog()
                        closeActivityRemove()
                    }

                })
            }
        }
    }

    private fun updateDBContactWithSavedAsyncType(newContact: Contact) {
        val asyncType = AsyncSettingsPreference(getSharedPreferences("asyncType", MODE_PRIVATE))
        val loadingDialog = LoadingDialog(this)
        val asyncCustomListener = object : AsyncCustomListener{
            override fun onStart() {
                loadingDialog.startLoadingDialog()
            }

            override fun onStop() {
                loadingDialog.dismissDialog()
                closeActivityEdit(newContact)
            }

        }
        when (asyncType.loadAsyncType()) {
            1 -> {
                val dbThreadPoolExecutor = DBThreadPoolExecutor(
                    (application as App).dbHelper,
                    Handler(mainLooper)
                )as EDBService
                dbThreadPoolExecutor.updateContactInDB(contact, newContact, asyncCustomListener)
            }
            2 -> {
                val db = DBCompletableFuturePoolExecutor(
                    mainExecutor,
                    (application as App).dbHelper
                )as EDBService
                db.updateContactInDB(contact, newContact, asyncCustomListener)
            }
            3 -> {
                val db = DBRxJava((application as App).dbHelper)as EDBService
                db.updateContactInDB(contact,newContact, asyncCustomListener)
            }
        }
    }

    private fun closeActivityRemove() {
        val intent = Intent().apply {
            putExtra("old_contact", contact)
            putExtra("isRemove", true)
        }
        setResult(RESULT_OK, intent)
        finish()
    }

    private fun closeActivityEdit(newContact: Contact) {
        val intent = Intent().apply {
            putExtra("old_contact", contact)
            putExtra("new_contact", newContact)
            putExtra("isRemove", false)
        }
        setResult(RESULT_OK, intent)
        finish()
    }
}
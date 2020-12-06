package com.example.homework5_2

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.homework5_2.AlertDialogs.AlertEmptyDialog
import com.example.homework5_2.AlertDialogs.LoadingDialog
import com.example.homework5_2.Async.DBCompF_PoolExec
import com.example.homework5_2.Async.DBRxJava
import com.example.homework5_2.Async.DBThreadPoolExecutor
import com.example.homework5_2.Async.EDBService
import com.example.homework5_2.Contact.ConnectType
import com.example.homework5_2.Contact.Contact
import com.example.homework5_2.DataBase.App
import com.example.homework5_2.Listeners.AsyncCustomListener
import com.example.homework5_2.Listeners.GetCompletableListener
import com.example.homework5_2.Settings.AsyncSettingsPreference
import io.reactivex.Completable
import kotlinx.android.synthetic.main.edit_contact_activity.edit_button
import kotlinx.android.synthetic.main.edit_contact_activity.edit_name
import kotlinx.android.synthetic.main.edit_contact_activity.edit_communication
import kotlinx.android.synthetic.main.edit_contact_activity.remove_contact
import kotlinx.android.synthetic.main.edit_contact_activity.switchConnect

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
        val asyncCustomListener = object : AsyncCustomListener {
            override fun onStart() {
                loadingDialog.startLoadingDialog()
            }

            override fun onStop() {
                loadingDialog.dismissDialog()
                closeActivityRemove()
            }

        }
        when (asyncType.loadAsyncType()) {
            1 -> {
                val dbThreadPoolExecutor = DBThreadPoolExecutor(
                    (application as App).dbHelper,
                    asyncCustomListener,
                    Handler(mainLooper)
                )as EDBService
                dbThreadPoolExecutor.deleteContactFromDB(contact)
            }
            2 -> {
                val db = DBCompF_PoolExec(
                    mainExecutor,
                    (application as App).dbHelper,
                    asyncCustomListener
                )as EDBService
                db.deleteContactFromDB(contact)
            }
            3 -> {
                val getCompletableListener = object : GetCompletableListener {
                    override fun getCompletable(completable: Completable) {
                        completable.subscribe()
                    }

                }
                val db = DBRxJava(
                    (application as App).dbHelper,
                    asyncCustomListener,
                    getCompletableListener
                )as EDBService
                db.deleteContactFromDB(contact)
            }
        }
    }

    private fun updateDBContactWithSavedAsyncType(newContact: Contact) {
        val asyncType = AsyncSettingsPreference(getSharedPreferences("asyncType", MODE_PRIVATE))
        val loadingDialog = LoadingDialog(this)
        val asyncCustomListener = object : AsyncCustomListener {
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
                    asyncCustomListener,
                    Handler(mainLooper)
                )as EDBService
                dbThreadPoolExecutor.updateContactInDB(contact, newContact)
            }
            2 -> {
                val db = DBCompF_PoolExec(
                    mainExecutor,
                    (application as App).dbHelper,
                    asyncCustomListener
                )as EDBService
                db.updateContactInDB(contact, newContact)
            }
            3 -> {
                val getCompletableListener = object : GetCompletableListener{
                    override fun getCompletable(completable: Completable) {
                        completable.subscribe()
                    }

                }
                val db = DBRxJava((application as App).dbHelper, asyncCustomListener, getCompletableListener)as EDBService
                db.updateContactInDB(contact,newContact)
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
package com.example.homework5_2

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.homework5_2.AlertDialogs.AlertEmptyDialog
import com.example.homework5_2.Async.DBCompF_PoolExec
import com.example.homework5_2.Async.DBRxJava
import com.example.homework5_2.Async.DBThreadPoolExecutor
import com.example.homework5_2.Contact.ConnectType
import com.example.homework5_2.Contact.Contact
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

    private fun deleteDBContactWithSavedAsyncType(){
        val asyncType = AsyncSettingsPreference(getSharedPreferences("asyncType", MODE_PRIVATE))
        when (asyncType.loadAsyncType()) {
            1 -> {
                val dbThreadPoolExecutor = DBThreadPoolExecutor(this)
                dbThreadPoolExecutor.prepareHandler(mainLooper)
                dbThreadPoolExecutor.deleteContactFromDB(contact, applicationContext)
                while (!dbThreadPoolExecutor.isStopped()) {
                }
                closeActivityRemove()
            }
            2 -> {
                val db = DBCompF_PoolExec(this, mainExecutor)
                db.deleteContactFromDB(contact, applicationContext)
                while (!db.isDone()) {
                }
                closeActivityRemove()
            }
            3 -> {
                val db = DBRxJava()
                db.deleteContactFromDB(contact, applicationContext)
                db.getCompletable()
                    .subscribe {
                        closeActivityRemove()
                    }
            }
        }
    }

    private fun updateDBContactWithSavedAsyncType(newContact: Contact){
        val asyncType = AsyncSettingsPreference(getSharedPreferences("asyncType",MODE_PRIVATE))
        when (asyncType.loadAsyncType()) {
            1 -> {
                val dbThreadPoolExecutor = DBThreadPoolExecutor(this)
                dbThreadPoolExecutor.prepareHandler(mainLooper)
                dbThreadPoolExecutor.updateContactInDB(contact,newContact, applicationContext)
                while (!dbThreadPoolExecutor.isStopped()){
                }
                closeActivityEdit(newContact)
            }
            2 -> {
                val db = DBCompF_PoolExec(this, mainExecutor)
                db.updateContactInDB(contact,newContact,applicationContext)
                while (!db.isDone()){
                }
                closeActivityEdit(newContact)
            }
            3 -> {
                val db = DBRxJava()
                db.updateContactInDB(contact, newContact, applicationContext)
                db.getCompletable()
                    .subscribe {

                    }
                closeActivityEdit(newContact)
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

    private fun closeActivityEdit(newContact : Contact){
        val intent = Intent().apply {
            putExtra("old_contact", contact)
            putExtra("new_contact", newContact)
            putExtra("isRemove", false)
        }
        setResult(RESULT_OK, intent)
        finish()
    }
}
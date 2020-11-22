package com.example.homework5_2

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.homework5_2.AlertDialogs.AlertEmptyDialog
import com.example.homework5_2.Async.DBThreadPoolExecutor
import com.example.homework5_2.Contact.ConnectType
import com.example.homework5_2.Contact.Contact
import com.example.homework5_2.DataBase.DBService
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
        val newContact  = Contact(edit_name!!.text.toString(), edit_communication!!.text.toString(), if(switchConnect.isChecked) ConnectType.EMAIL else ConnectType.PHONE)
//        DBService.updateContactInDB(contact,
//            newContact,
//            applicationContext)
        val dbThreadPoolExecutor = DBThreadPoolExecutor(this)
        dbThreadPoolExecutor.prepareHandler(mainLooper)
        dbThreadPoolExecutor.updateContactInDB(contact,newContact, applicationContext)
        while (!dbThreadPoolExecutor.isTerminated()){

        }
        val intent = Intent().apply {
            putExtra("old_contact", contact)
            putExtra("new_contact", newContact)
            putExtra("isRemove", false)
        }
        setResult(RESULT_OK, intent)
        finish()
    }

    private fun backToMainRemove() {
        val intent = Intent().apply {
            putExtra("old_contact", contact)
            putExtra("isRemove", true)
        }
//        DBService.deleteContactFromDB(contact, applicationContext)
        val dbThreadPoolExecutor = DBThreadPoolExecutor(this)
        dbThreadPoolExecutor.prepareHandler(mainLooper)
        dbThreadPoolExecutor.deleteContactFromDB(contact, applicationContext)
        while (!dbThreadPoolExecutor.isTerminated()){

        }
        setResult(RESULT_OK, intent)
        finish()
    }
}
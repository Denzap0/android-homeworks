package com.example.homework5_2

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import com.example.homework5_2.AlertDialogs.AlertEmptyDialog
import com.example.homework5_2.Contact.ConnectType
import com.example.homework5_2.Contact.Contact
import kotlinx.android.synthetic.main.edit_contact_activity.*
import java.util.*

class EditContactActivity : AppCompatActivity() {

    var bundle: Bundle? = null
    private var contact: Contact? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_contact_activity)

        if (intent != null) {
            bundle = intent.extras
            contact = bundle?.getSerializable("contact") as Contact
        }
        if (bundle != null) {
            edit_name.hint = contact?.name
            edit_communication.hint = contact?.communication


        }
        edit_button.setOnClickListener(View.OnClickListener {
            if (edit_name.text.toString().isEmpty()) {
                openAttentionEmptyDialog()
            } else {
                backToMainEdit(bundle!!)
            }
        })
        remove_contact.setOnClickListener(View.OnClickListener { backToMainRemove(bundle!!) })
    }

    private fun openAttentionEmptyDialog() {
        val dialog = AlertEmptyDialog()
        dialog.show(supportFragmentManager, "Alert dialog")
    }

    private fun backToMainEdit(bundle: Bundle) {
        val intent = Intent()
        intent.putExtra("contact", contact)
        intent.putExtra("new_name", edit_name!!.text.toString())
        intent.putExtra("new_communication", edit_communication!!.text.toString())
        intent.putExtra("isRemove", false)
        if (switchConnect!!.isChecked) {
            intent.putExtra("connectType", ConnectType.EMAIL)
        } else {
            intent.putExtra("connectType", ConnectType.PHONE)
        }
        setResult(RESULT_OK, intent)
        finish()
    }

    private fun backToMainRemove(bundle: Bundle) {
        val intent = Intent()
        intent.putExtra("contact", contact)
        intent.putExtra("isRemove", true)
        setResult(RESULT_OK, intent)
        finish()
    }
}
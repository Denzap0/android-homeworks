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
import kotlinx.android.synthetic.main.edit_contact_activity.*
import java.util.*

class EditContactActivity : AppCompatActivity() {
    private var contacts: List<String>? = null
    private var editCommunication: EditText? = null
    private var editName: EditText? = null
    private var editButton: Button? = null
    private var removeButton: Button? = null
    var communicationSwitch: Switch? = null
    var bundle: Bundle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_contact_activity)
        editName = findViewById(R.id.edit_name)
        editCommunication = findViewById(R.id.edit_communication)
        editButton = findViewById(R.id.edit_button)
        removeButton = findViewById(R.id.remove_contact)
        communicationSwitch = findViewById(R.id.switchConnect)
        bundle = if (intent != null) {
            intent.extras
        } else {
            Bundle()
        }
        if (bundle != null) {
            edit_name.hint = bundle!!.getString("old_name")
            edit_communication.hint = bundle!!.getString("old_communication")
            contacts = bundle!!.getStringArrayList("contacts")
            if (contacts == null) {
                contacts = ArrayList()
            }
            edit_button.setOnClickListener(View.OnClickListener {
                if (edit_name.text.toString().isEmpty()) {
                    openAttentionEmptyDialog()
                } else if (contacts!!.contains(edit_name.getText().toString())) {
                    openAttentionContainsDialog()
                } else {
                    backToMainEdit(bundle!!)
                }
            })
            remove_contact.setOnClickListener(View.OnClickListener { backToMainRemove(bundle!!) })
        }
    }

    fun openAttentionEmptyDialog() {
        val dialog = AlertEmptyDialog()
        dialog.show(supportFragmentManager, "Alert dialog")
    }

    fun openAttentionContainsDialog() {
        val dialog = AlertEmptyDialog()
        dialog.show(supportFragmentManager, "Alert dialog")
    }

    private fun backToMainEdit(bundle: Bundle) {
        val intent = Intent()
        intent.putExtra("old_name", bundle.getString("old_name"))
        intent.putExtra("new_name", editName!!.text.toString())
        intent.putExtra("new_communication", editCommunication!!.text.toString())
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
        intent.putExtra("old_name", bundle.getString("old_name"))
        intent.putExtra("isRemove", true)
        setResult(RESULT_OK, intent)
        finish()
    }
}
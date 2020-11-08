package com.example.homework5_2

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import com.example.homework5_2.AlertDialogs.AlertEmptyDialog
import com.example.homework5_2.Contact.ConnectType
import kotlinx.android.synthetic.main.add_contact_activity.switchConnect
import kotlinx.android.synthetic.main.add_contact_activity.edit_name
import kotlinx.android.synthetic.main.add_contact_activity.edit_communication

class AddContactActivity : AppCompatActivity() {

    private var bundle: Bundle? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_contact_activity)

        switchConnect.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            val iconId = if (isChecked) R.drawable.ic_baseline_mail_24 else R.drawable.ic_baseline_local_phone_24
            edit_communication.setCompoundDrawablesWithIntrinsicBounds(iconId, 0,0,0)
        })
        bundle = if (intent != null) {
            intent.extras
        } else {
            Bundle()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.tick_add_contact, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.add_tick) {
            if (edit_name!!.text.toString().isEmpty()) {
                openAttentionEmptyDialog()
            } else {
                backToMain()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun openAttentionEmptyDialog() {
        val dialog = AlertEmptyDialog()
        dialog.show(supportFragmentManager, "Alert dialog")
    }

    private fun backToMain() {
        val intent = Intent().apply {
            putExtra("name", edit_name.text.toString())
            putExtra("communication", edit_communication.text.toString())
            putExtra("connectType", if (switchConnect.isChecked) ConnectType.EMAIL else ConnectType.PHONE)
        }
        setResult(RESULT_OK, intent)
        finish()
    }
}
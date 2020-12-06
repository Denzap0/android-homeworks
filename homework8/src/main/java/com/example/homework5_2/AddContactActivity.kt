package com.example.homework5_2

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.widget.CompoundButton
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
import kotlinx.android.synthetic.main.add_contact_activity.switchConnect
import kotlinx.android.synthetic.main.add_contact_activity.edit_communication
import kotlinx.android.synthetic.main.add_contact_activity.edit_name

class AddContactActivity : AppCompatActivity() {

    private var bundle: Bundle? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_contact_activity)

        switchConnect.setOnCheckedChangeListener { buttonView, isChecked ->
            val iconId =
                if (isChecked) R.drawable.ic_baseline_mail_24 else R.drawable.ic_baseline_local_phone_24
            edit_communication.setCompoundDrawablesWithIntrinsicBounds(iconId, 0, 0, 0)
        }
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
        val contactToAdd = Contact(
            edit_name.text.toString(),
            edit_communication.text.toString(),
            if (switchConnect.isChecked) ConnectType.EMAIL else ConnectType.PHONE
        )
        addDBContactWithSavedAsyncType(contactToAdd)
    }

    private fun addDBContactWithSavedAsyncType(contactToAdd: Contact) {
        val asyncType = AsyncSettingsPreference(getSharedPreferences("asyncType", MODE_PRIVATE))
        val loadingDialog = LoadingDialog(this)
        val asyncCustomListener = object : AsyncCustomListener {
            override fun onStart() {
                loadingDialog.startLoadingDialog()
            }

            override fun onStop() {
                loadingDialog.dismissDialog()
                closeActivity(contactToAdd)
            }

        }
        when (asyncType.loadAsyncType()) {
            1 -> {
                val threadForAdd = DBThreadPoolExecutor(
                    (application as App).dbHelper,
                    asyncCustomListener,
                    Handler(mainLooper)
                ).apply {
                    addContactToDB(contactToAdd)
                } as EDBService

            }
            2 -> {
                val threadForAdd = DBCompF_PoolExec(
                    mainExecutor,
                    (application as App).dbHelper,
                    asyncCustomListener
                ) as EDBService
                threadForAdd.addContactToDB(contactToAdd)
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
                ) as EDBService
                db.addContactToDB(contactToAdd)
            }
        }
    }

    private fun closeActivity(contactToAdd: Contact) {
        val intent = Intent().apply {
            putExtra("contact", contactToAdd)
        }
        setResult(RESULT_OK, intent)
        finish()
    }


}




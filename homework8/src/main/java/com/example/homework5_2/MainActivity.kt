package com.example.homework5_2

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.homework5_2.Async.DBCompF_PoolExec
import com.example.homework5_2.Async.DBRxJava
import com.example.homework5_2.Async.DBThreadPoolExecutor
import com.example.homework5_2.Contact.Contact
import com.example.homework5_2.Contact.ContactComparator
import com.example.homework5_2.Settings.AsyncSettingsPreference
import kotlinx.android.synthetic.main.activity_main.*
import java.util.Collections

import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    private var contacts: MutableList<Contact> = ArrayList()
    private var comparator: ContactComparator? = ContactComparator()
    private val listItemActionListener: ListItemActionListener = object : ListItemActionListener {
        override fun onItemClicked(contact: Contact?) {
            if (contact != null) {
                startEditActivity(contact)
            }
        }
    }
    private var adapter: ContactsAdapter? = null

    interface ListItemActionListener {
        fun onItemClicked(contact: Contact?)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getContactsWithSavedAsyncType()
        add_contact_button.setOnClickListener {
            val intent =
                Intent(this@MainActivity, AddContactActivity::class.java)
            startActivityForResult(intent, 1)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        val settingsView = menu.findItem(R.id.settings)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                adapter?.filter?.filter(newText)
                contacts_list!!.adapter = adapter
                return false
            }
        })
        settingsView.setOnMenuItemClickListener {
            val intent = Intent(this@MainActivity, SettingsActivity::class.java)
            startActivity(intent)
            true
        }
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            val contact: Contact = data.getSerializableExtra("contact") as Contact

            contacts.add(contact)
            Collections.sort(contacts, comparator)
            adapter?.setContacts(contacts)
        }
        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            var contact: Contact = data.getSerializableExtra("old_contact") as Contact

            val c = contacts.find { item -> item.name == contact.name }
            c?.run { contacts.remove(this) }

            if (!data.getBooleanExtra("isRemove", true)) {
                contacts.add(data.getSerializableExtra("new_contact") as Contact)
            }
            Collections.sort(contacts, comparator)
            adapter?.setContacts(contacts)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        adapter = ContactsAdapter(contacts, listItemActionListener)
        contacts_list!!.adapter = adapter
    }

    private fun startEditActivity(contact: Contact) {
        val intent = Intent(this@MainActivity, EditContactActivity::class.java)
        intent.putExtra("contact", contact)
        startActivityForResult(intent, 2)
    }

    private fun showRecycler(){
        Collections.sort(contacts, comparator)
        adapter = ContactsAdapter(contacts, listItemActionListener)
        contacts_list.adapter = adapter
        contacts_list.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)
    }

    private fun getContactsWithSavedAsyncType(){
        val asyncType = AsyncSettingsPreference(getSharedPreferences("asyncType", MODE_PRIVATE))
        if(asyncType.loadAsyncType() == 0){
            asyncType.saveAsyncType(1)
        }
        when(asyncType.loadAsyncType()){

            1 -> {
                val db  = DBThreadPoolExecutor(this)
                db.prepareHandler(mainLooper)
                db.getContactsFromDB(contacts, applicationContext)
                while (!db.isStopped()){
                }
                showRecycler()
            }
            2 -> {
                val db = DBCompF_PoolExec(this@MainActivity, mainExecutor)
                db.getContactsFromDB(contacts, applicationContext)
                comparator = ContactComparator()
                while (!db.isDone()){
                }
                showRecycler()
            }
            3 -> {
                val db = DBRxJava()
                db.getContactsFromDB(contacts, applicationContext)
                db.getCompletable()
                    .subscribe{
                        showRecycler()
                    }
            }
        }
    }
}
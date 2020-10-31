package com.example.homework5_2

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.homework5_2.Contact.ConnectType
import com.example.homework5_2.Contact.Contact
import com.example.homework5_2.Contact.ContactComparator
import java.util.*

class MainActivity : AppCompatActivity() {
    private val contacts: MutableList<Contact> = ArrayList()
    private var comparator: ContactComparator? = null
    private val recyclerView: RecyclerView? = findViewById(R.id.contacts_list)
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
        comparator = ContactComparator()
        adapter = ContactsAdapter(contacts, listItemActionListener)
        if (recyclerView != null) {
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        }

        findViewById<View>(R.id.add_contact_button).setOnClickListener {
            val intent =
                Intent(this@MainActivity, AddContactActivity::class.java)
            intent.putExtra("contacts", namesList)
            startActivityForResult(intent, 1)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.search_for_contact, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                adapter!!.filter.filter(newText)
                recyclerView!!.adapter = adapter
                return false
            }
        })
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            val contact: Contact = Contact(data.getStringExtra("name").toString(),data.getStringExtra("communication").toString(),
                data.extras?.get("connectType") as ConnectType
            )
            contacts.add(contact)
            Collections.sort(contacts, comparator)
            adapter!!.setContacts(contacts)
        }
        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            for (i in contacts.indices) {
                if (contacts[i].name == data.getStringExtra("old_name")) {
                    contacts.removeAt(i)
                }
            }
            if (!data.getBooleanExtra("isRemove", true)) {
                val contact: Contact = Contact(data.getStringExtra("name").toString(),data.getStringExtra("communication").toString(),
                    data.extras?.get("connectType") as ConnectType
                )
                contacts.add(contact)
            }
            Collections.sort(contacts, comparator)
            adapter!!.setContacts(contacts)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        adapter = ContactsAdapter(contacts, listItemActionListener)
        recyclerView!!.adapter = adapter
    }

    private val namesList: ArrayList<String>
        private get() {
            val namesList = ArrayList<String>()
            for (i in contacts.indices) {
                namesList.add(contacts[i].name)
            }
            return namesList
        }

    private fun startEditActivity(contact: Contact) {
        val intent = Intent(this@MainActivity, EditContactActivity::class.java)
        intent.putExtra("old_name", contact.name)
        intent.putExtra("old_communication", contact.communication)
        intent.putExtra("contacts", namesList.toString())
        startActivityForResult(intent, 2)
    }
}
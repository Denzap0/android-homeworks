package com.example.homework5_2

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.homework5_2.Contact.ConnectType
import com.example.homework5_2.Contact.Contact
import com.example.homework5_2.Contact.ContactComparator
import com.example.homework5_2.DataBase.App
import kotlinx.android.synthetic.main.activity_main.*
import java.util.Collections
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    private val contacts = mutableListOf<Contact>()
    private var comparator: ContactComparator? = null
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

        getContactsFromDB(contacts)
        comparator = ContactComparator()
        adapter = ContactsAdapter(contacts, listItemActionListener)
        if(contacts.isNotEmpty()){
            Collections.sort(contacts, comparator)
        }
        Log.d("AAAAAAAAAAA", contacts.toString())
        contacts_list.adapter = adapter
        contacts_list.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        add_contact_button.setOnClickListener {
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
                adapter?.filter?.filter(newText)
                contacts_list!!.adapter = adapter
                return false
            }
        })
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            val contact: Contact = Contact(
                data.getStringExtra("name").toString(),
                data.getStringExtra("communication").toString(),
                data.extras?.get("connectType") as ConnectType
            )
            addContactToDB(contact)
            contacts.add(contact)
            Collections.sort(contacts, comparator)
            adapter?.setContacts(contacts)
        }
        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            val oldContact: Contact = data.getSerializableExtra("contact") as Contact
            for (i in 0 until contacts.size) {
                if (contacts[i].name == oldContact.name) {
                    contacts.removeAt(i)
                }
            }
            if (!data.getBooleanExtra("isRemove", true)) {
                val newContact = Contact(
                    data.getStringExtra("new_name").toString(),
                    data.getStringExtra("new_communication").toString(),
                    data.extras?.get("connectType") as ConnectType
                )
                contacts.add(newContact)
                updateContactInDB(oldContact, newContact)
            }else{
                deleteContactFromDB(oldContact)
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
        intent.putExtra("contact", contact)
        startActivityForResult(intent, 2)
    }

    private fun getContactsFromDB(contacts : MutableList<Contact>){
        contacts.clear()
        val cursor = (applicationContext as App).dbHelper?.readableDatabase?.query(
            "ContactsBase",
            null,
            null,
            null,
            null,
            null,
            null)

        if(cursor != null) {
            val nameIndex = cursor.getColumnIndex("name")
            val communicationIndex = cursor.getColumnIndex("communication")
            val connectTypeIndex = cursor.getColumnIndex("connect_type")

            while (cursor.moveToNext()){
                contacts.add(Contact(cursor.getString(nameIndex),
                    cursor.getString(communicationIndex),
                    if(cursor.getInt(connectTypeIndex) == 0) ConnectType.PHONE else ConnectType.EMAIL))
            }
            cursor.close()
        }
    }

    private fun addContactToDB(contact: Contact){
        val contentValues = ContentValues().apply {
            put("name", contact.name)
            put("communication", contact.communication)
            put("connect_type", if(contact.connectType == ConnectType.PHONE) 0 else 1)
        }
        (applicationContext as App).dbHelper?.writableDatabase?.insert("ContactsBase", null, contentValues)
    }

    private fun updateContactInDB(oldContact: Contact, newContact: Contact){
        val contentValues = ContentValues().apply {
            put("name", newContact.name)
            put("communication", newContact.communication)
            put("connect_type", if(newContact.connectType == ConnectType.PHONE) 0 else 1)
        }
        (applicationContext as App).dbHelper?.writableDatabase?.update(
            "ContactsBase",
            contentValues,
            "name = ?",
            arrayOf(oldContact.name))
    }

    private fun deleteContactFromDB(contact: Contact){
        (applicationContext as App).dbHelper?.writableDatabase?.delete("ContactsBase", "name = ?", arrayOf(contact.name))
    }
}
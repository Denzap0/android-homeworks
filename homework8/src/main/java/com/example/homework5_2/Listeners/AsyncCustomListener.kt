package com.example.homework5_2.Listeners

import com.example.homework5_2.AlertDialogs.LoadingDialog
import com.example.homework5_2.Contact.Contact

interface AsyncCustomListener {

    fun onStart()

    fun onStop()

    fun getContacts(contacts : MutableList<Contact>)

}
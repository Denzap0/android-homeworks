package com.example.homework5_2.Listeners

import com.example.homework5_2.Contact.Contact

interface AsyncCustomGetContactsListener {

    fun onStart()

    fun onStop(contacts : MutableList<Contact>)

}
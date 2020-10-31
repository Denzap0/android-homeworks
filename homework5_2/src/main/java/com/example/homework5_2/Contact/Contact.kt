package com.example.homework5_2.Contact

import java.util.*

class Contact(var name: String, var communication: String, var connectType: ConnectType) {

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val contact = o as Contact
        return name == contact.name && connectType == contact.connectType &&
                communication == contact.communication
    }

    override fun hashCode(): Int {
        return Objects.hash(name, connectType, communication)
    }
}
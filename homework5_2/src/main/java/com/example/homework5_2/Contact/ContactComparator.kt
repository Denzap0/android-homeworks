package com.example.homework5_2.Contact

import java.util.*

class ContactComparator : Comparator<Contact?> {


    override fun compare(contact1: Contact?, contact2: Contact?): Int? = contact1?.name?.compareTo(contact2?.name.toString())
}
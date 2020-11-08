package com.example.homework5_2.Contact


class ContactComparator : Comparator<Contact?> {
    override fun compare(o1: Contact?, o2: Contact?): Int {
        return o1?.name.toString().compareTo(o2?.name.toString())
    }


}
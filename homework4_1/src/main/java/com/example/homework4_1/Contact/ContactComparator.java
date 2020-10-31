package com.example.homework4_1.Contact;

import java.util.Comparator;

public class ContactComparator implements Comparator<Contact> {
    @Override
    public int compare(Contact contact1, Contact contact2) {
        return contact1.getName().compareTo(contact2.getName());
    }
}

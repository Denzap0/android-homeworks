package com.example.homework4_1.Contact;

import java.util.Comparator;

public class ContactComparator implements Comparator<Contact> {
    @Override
    public int compare(Contact o1, Contact o2) {
        return o1.getName().compareTo(o2.getName());
    }
}

package com.example.homework5_2.Async

import android.content.Context
import com.example.homework5_2.Contact.Contact
import com.example.homework5_2.DataBase.DBService
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DBRxJava : EDBService {

    private lateinit var completable: Completable

    public fun getCompletable(): Completable = completable

    override fun addContactToDB(contact: Contact, applicationContext: Context) {
        completable = Completable.complete()
            .doOnComplete{
                DBService.addContactToDB(contact,applicationContext)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun updateContactInDB(
        oldContact: Contact,
        newContact: Contact,
        applicationContext: Context
    ) {
        completable = Completable.complete()
            .doOnComplete{
            DBService.updateContactInDB(oldContact, newContact, applicationContext)

        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    }

    override fun deleteContactFromDB(contact: Contact, applicationContext: Context) {
        completable = Completable.complete()
            .doOnComplete{
            DBService.deleteContactFromDB(contact, applicationContext)
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getContactsFromDB(contacts: MutableList<Contact>, applicationContext: Context) {
        completable = Completable.complete()
            .doOnComplete { getContactsFromDB(contacts, applicationContext) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}
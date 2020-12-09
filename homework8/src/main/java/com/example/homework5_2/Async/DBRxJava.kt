package com.example.homework5_2.Async

import com.example.homework5_2.Contact.Contact
import com.example.homework5_2.DataBase.DBHelper
import com.example.homework5_2.DataBase.DBService
import com.example.homework5_2.Listeners.AsyncCustomGetContactsListener
import com.example.homework5_2.Listeners.AsyncCustomListener
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DBRxJava(private val dbHelper: DBHelper) : EDBService {

    override fun addContactToDB(contact: Contact, asyncCustomListener: AsyncCustomListener ) {
        val completable = Completable.complete()
            .observeOn(AndroidSchedulers.mainThread())
            .doOnComplete {
                asyncCustomListener.onStart()
            }
            .subscribeOn(Schedulers.io())
            .doOnComplete{
                DBService.addContactToDB(contact,dbHelper)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnComplete {
                asyncCustomListener.onStop()
            }.subscribe()
    }

    override fun updateContactInDB(
        oldContact: Contact,
        newContact: Contact,
        asyncCustomListener: AsyncCustomListener
    ) {
        val completable = Completable.complete()
            .observeOn(AndroidSchedulers.mainThread())
            .doOnComplete {
                asyncCustomListener.onStart()
            }
            .subscribeOn(Schedulers.io())
            .doOnComplete{
                DBService.updateContactInDB(oldContact, newContact, dbHelper)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnComplete {
                asyncCustomListener.onStop()
            }.subscribe()
    }

    override fun deleteContactFromDB(contact: Contact, asyncCustomListener: AsyncCustomListener) {
        val completable = Completable.complete()
            .observeOn(AndroidSchedulers.mainThread())
            .doOnComplete {
                asyncCustomListener.onStart()
            }
            .subscribeOn(Schedulers.io())
            .doOnComplete{
                DBService.deleteContactFromDB(contact, dbHelper)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnComplete {
                asyncCustomListener.onStop()
            }.subscribe()
    }

    override fun getContactsFromDB(asyncCustomGetContactsListener: AsyncCustomGetContactsListener) {
        var contacts = mutableListOf<Contact>()
//        val completable = Completable.complete()
//            .observeOn(AndroidSchedulers.mainThread())
//            .doOnComplete {
//                asyncCustomGetContactsListener.onStart()
//            }
//            .subscribeOn(Schedulers.io())
//            .doOnComplete{
//                contacts = DBService.getContactsFromDB(dbHelper)
//            }
//            .observeOn(AndroidSchedulers.mainThread())
//            .doOnComplete {
//                asyncCustomGetContactsListener.onStop(contacts)
//            }
        asyncCustomGetContactsListener.onStart()
        Single.create<MutableList<Contact>> {
            DBService.getContactsFromDB(dbHelper)
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { filledContacts -> asyncCustomGetContactsListener.onStop(filledContacts)}
            .subscribe()

    }
}
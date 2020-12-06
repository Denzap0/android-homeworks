package com.example.homework5_2.Async

import com.example.homework5_2.Contact.Contact
import com.example.homework5_2.DataBase.DBHelper
import com.example.homework5_2.DataBase.DBService
import com.example.homework5_2.Listeners.AsyncCustomListener
import com.example.homework5_2.Listeners.GetCompletableListener
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DBRxJava(private val dbHelper: DBHelper, private val asyncCustomListener: AsyncCustomListener,private val getCompletableListener: GetCompletableListener) : EDBService {

    private lateinit var completable: Completable


    override fun addContactToDB(contact: Contact) {
        completable = Completable.complete()
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
            }
        getCompletableListener.getCompletable(completable)
    }

    override fun updateContactInDB(
        oldContact: Contact,
        newContact: Contact
    ) {
        completable = Completable.complete()
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
            }
        getCompletableListener.getCompletable(completable)
    }

    override fun deleteContactFromDB(contact: Contact) {
        completable = Completable.complete()
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
            }
        getCompletableListener.getCompletable(completable)
    }

    override fun getContactsFromDB(contacts: MutableList<Contact>) {
        completable = Completable.complete()
            .observeOn(AndroidSchedulers.mainThread())
            .doOnComplete {
                asyncCustomListener.onStart()
            }
            .subscribeOn(Schedulers.io())
            .doOnComplete{
                DBService.getContactsFromDB(contacts, dbHelper)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnComplete {
                asyncCustomListener.onStop()
            }
        getCompletableListener.getCompletable(completable)
    }
}
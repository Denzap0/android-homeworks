package com.example.homework5_2.DataBase

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri

class MyContentProvider() : ContentProvider() {

    private lateinit var dbHelper: DBHelper
    companion object{
        const val AUTHORITY = "com.denzap.provider"
        const val CONTACTS_PATH = "contacts"
        const val TABLE_NAME = "ContactsBase"
        public val URI = Uri.parse("content://${AUTHORITY}/${CONTACTS_PATH}")
    }
    override fun onCreate(): Boolean {
        dbHelper = App().getDBInstance()
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        if (uri == URI){
            return dbHelper.writableDatabase.query(TABLE_NAME,null,null,null,null,null,null,null)
        }
        return null
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        return 0
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        return 0
    }
}
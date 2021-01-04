package com.example.homework5_2.DataBase

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import com.example.homework5_2.Visibility.MainActivity

public class MyContentProvider() : ContentProvider() {

    private lateinit var dbHelper: DBHelper
    companion object{
        private const val AUTHORITY = "com.denzap.provider"
        private const val CONTACTS_PATH = "contacts"
        private const val TABLE_NAME = "ContactsBase"
        private const val DATA_ACTION = 1
        private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
            addURI("com.homework11.contentprovider", "data", DATA_ACTION)
        }
    }
    override fun onCreate(): Boolean {
        dbHelper = (context?.applicationContext as App).dbHelper
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        return when(uriMatcher.match(uri)){
            DATA_ACTION -> DBService.getContactsFromDBCursor(dbHelper)
            else -> null
        }
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
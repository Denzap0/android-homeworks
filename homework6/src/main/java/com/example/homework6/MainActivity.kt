package com.example.homework6

import FileRecyclerAdapter
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.io.FileOutputStream

class MainActivity : AppCompatActivity() {

    private var files = mutableListOf<File>()
    private var fileNames = mutableListOf<String>()
    private val listItemActionListener: ListItemActionListener = object : ListItemActionListener {
        override fun onItemClicked(file: File) {
            val intent: Intent = Intent(this@MainActivity, EditFileActivity::class.java)
            intent.putExtra("file", file)
            val namesArrayList: ArrayList<String> = ArrayList(fileNames)
            intent.putStringArrayListExtra("fileNames", namesArrayList)
            startActivityForResult(intent, 1)
        }

    }

    private val namesFile = "nameFile"
    private var storagePath: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        storageTypeRead()
        if (File(storagePath, namesFile).exists()) {
            fileNames = readFileToList(namesFile)
        }
        updateLocalFiles(fileNames,files,storagePath,filesRecyclerView,listItemActionListener )
        filesRecyclerView.apply {
            adapter = FileRecyclerAdapter(files, listItemActionListener)
            layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
        }
        add_file_button.setOnClickListener {
            openAddFileDialog()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null) {
            editContact(data)
        } else if (requestCode == 2 && resultCode == Activity.RESULT_OK) {
            storageTypeRead()
            if (File(storagePath, namesFile).exists()) {
                fileNames = readFileToList(namesFile)
            }
            updateFileNames(storagePath,namesFile,fileNames)
            updateLocalFiles(fileNames,files,storagePath,filesRecyclerView,listItemActionListener)
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.add_file, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.settings) {
            openSettingsActivity()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun readFileToList(nameFile: String): MutableList<String> =
        File(storagePath, nameFile).useLines { it.toMutableList() }

    private fun storageTypeRead() {
        val sharedPrefs = getSharedPreferences("settingsFile", Context.MODE_PRIVATE)
        storagePath = if (!sharedPrefs.getBoolean("isExternal", false)) {
            applicationContext.getExternalFilesDir(null)?.absolutePath

        } else {
            applicationContext.filesDir.absolutePath
        }
    }

    @SuppressLint("SetTextI18n")
    private fun openAddFileDialog() {

        val editText = EditText(this)
        editText.inputType = InputType.TYPE_CLASS_TEXT
        val textView: TextView = TextView(this)
        textView.setTextColor(Color.RED)
        val layout = LinearLayout(this)
        layout.apply {
            orientation = LinearLayout.VERTICAL
            addView(editText)
            addView(textView)
        }
        val dialog: AlertDialog? = AlertDialog.Builder(this)
            .setTitle("Add new file")
            .setView(layout)
            .setPositiveButton("Add", null)
            .show()
        val btn: Button = dialog!!.getButton(AlertDialog.BUTTON_POSITIVE)
        btn.setOnClickListener(View.OnClickListener {
            if (!fileNames.contains(editText.text.toString())) {
                addFile(File(storagePath, editText.text.toString()), files, fileNames,storagePath)
                addFileNameInFiles(editText.text.toString(), storagePath, namesFile)
                updateFileNames(storagePath, namesFile,fileNames)
                updateLocalFiles(fileNames, files,storagePath,filesRecyclerView,listItemActionListener)
                dialog.dismiss()
            } else {
                textView.text = "File with this name already exist"
            }
        })
    }

    private fun openSettingsActivity() {
        val intent = Intent(this@MainActivity, StorageTypeActivity::class.java)
        startActivityForResult(intent, 2)
    }

    private fun editContact(data: Intent?) {
        val oldFile = data?.extras?.get("oldFile") as File
        files.remove(oldFile)
        fileNames.remove(oldFile.name)
        if (!data.getBooleanExtra("isRemove", false)) {
            val newFile: File = data.extras?.get("newFile") as File
            addFile(newFile, files, fileNames, storagePath)
            addFileNameInFiles(newFile.name, namesFile, namesFile)
        }
        updateFileNames(storagePath, namesFile, fileNames)
        updateLocalFiles(fileNames, files, storagePath, filesRecyclerView, listItemActionListener)
    }
}

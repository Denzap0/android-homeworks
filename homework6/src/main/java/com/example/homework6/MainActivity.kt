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

    private var files: MutableList<File> = ArrayList()
    private var fileNames: MutableList<String> = ArrayList()
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
        updateLocalFiles()
        filesRecyclerView.adapter = FileRecyclerAdapter(files, listItemActionListener)
        filesRecyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        add_file_button.setOnClickListener(View.OnClickListener {
            openAddFileDialog()
        })
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
            updateFileNames()
            updateLocalFiles()
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
        layout.orientation = LinearLayout.VERTICAL
        layout.addView(editText)
        layout.addView(textView)
        val dialog: AlertDialog? = AlertDialog.Builder(this)
            .setTitle("Add new file")
            .setView(layout)
            .setPositiveButton("Add", null)
            .show()
        val btn: Button = dialog!!.getButton(AlertDialog.BUTTON_POSITIVE)
        btn.setOnClickListener(View.OnClickListener {
            if (!fileNames.contains(editText.text.toString())) {
                addFile(File(storagePath, editText.text.toString()))
                addFileNameInFiles(editText.text.toString())
                updateFileNames()
                updateLocalFiles()
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

    private fun addFile(file: File) {
        file.createNewFile()
        files.add(File(storagePath, file.name))
        fileNames.add(file.name)

    }

    private fun updateLocalFiles() {
        if (fileNames.isNotEmpty()) {
            files.clear()
            fileNames.forEach {
                files.add(File(storagePath, it))
            }
        }
        filesRecyclerView.adapter = FileRecyclerAdapter(files, listItemActionListener)
    }

    private fun updateFileNames() {
        File(storagePath, namesFile).delete()
        for (i in 0 until fileNames.size) {
            FileOutputStream(File(storagePath, namesFile), true)
                .bufferedWriter()
                .use { out ->
                    out.append(fileNames[i].toString())
                    out.newLine()
                }
        }
    }

    private fun addFileNameInFiles(fileName: String) {
        FileOutputStream(File(storagePath, namesFile), true)
            .bufferedWriter()
            .use { out ->
                out.append(fileName)
                out.newLine()
            }
    }

    private fun editContact(data: Intent?) {
        val oldFile = data?.extras?.get("oldFile") as File
        files.remove(oldFile)
        fileNames.remove(oldFile.name)
        if (!data.getBooleanExtra("isRemove", false)) {
            val newFile: File = data.extras?.get("newFile") as File
            addFile(newFile)
            addFileNameInFiles(newFile.name)
        }
        updateFileNames()
        updateLocalFiles()
    }

}

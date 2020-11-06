package com.example.homework6

import FileRecyclerAdapter
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
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
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.zip.Inflater

class MainActivity : AppCompatActivity() {

    private var files: MutableList<File> = ArrayList()
    private var fileNames: MutableList<String> = ArrayList()
    private val listItemActionListener: ListItemActionListener = object : ListItemActionListener {
        override fun onItemClicked(file: File) {
            val intent: Intent = Intent(this@MainActivity, EditFileActivity::class.java)
            intent.putExtra("file", file)
            val namesArrayList : ArrayList<String> = ArrayList(fileNames)

            intent.putStringArrayListExtra("fileNames", namesArrayList)
            startActivityForResult(intent, 1)
        }

    }
    private val namesFile = "nameFile"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        if (File(filesDir, namesFile).exists()) {
            fileNames = readFileToList(namesFile)
        }

        updateLocalFiles()
        filesRecyclerView.adapter = FileRecyclerAdapter(files, listItemActionListener)
        filesRecyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null && !data.getBooleanExtra("isRemove", false)) {
            val oldFile = data.extras!!.get("oldFile") as File
            val newFile : File = data.extras?.get("newFile") as File

            fileNames.remove(oldFile.name)
            files.remove(oldFile)
            fileNames.add(newFile.name)
            files.add(newFile)
            updateFileNames()
            updateLocalFiles()

        }else if(requestCode == 1 && resultCode == Activity.RESULT_OK && data != null && data.getBooleanExtra("isRemove", false)){
            val oldFile = data.extras!!.get("oldFile") as File

            files.remove(oldFile)
            fileNames.remove(oldFile)
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
        if (item.itemId == R.id.add_file) {
            openAddFileDialog()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun readFileToList(fileName: String): MutableList<String> =
        File(filesDir, fileName).useLines { it.toMutableList() }

    @SuppressLint("SetTextI18n")
    private fun openAddFileDialog() {

        val editText: EditText = EditText(this)
        editText.inputType = InputType.TYPE_CLASS_TEXT
        val textView: TextView = TextView(this)
        textView.setTextColor(Color.RED)
        val layout: LinearLayout = LinearLayout(this)
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
                addFile(editText.text.toString())
                addFileName(editText.text.toString())
                updateLocalFiles()
                dialog.dismiss()
            } else {
                textView.text = "File with this name already exist"
            }
        })


    }

    private fun addFile(fileName: String) {
        fileNames.add(fileName)
        if (File(filesDir, fileName).createNewFile()) {
            files.add(File(filesDir, fileName))
        }
    }

    private fun updateLocalFiles() {
        if (fileNames.isNotEmpty()) {
            files.clear()
            fileNames.forEach {
                files.add(File(filesDir, it))
            }
        }
        filesRecyclerView.adapter = FileRecyclerAdapter(files, listItemActionListener)
    }

    private fun updateFileNames(){
        File(filesDir,namesFile).delete()
        for(i in 0..fileNames.size - 1){
            FileOutputStream(File(filesDir, namesFile), true)
                .bufferedWriter()
                .use { out ->
                    out.append(fileNames[i].toString())
                    out.newLine()
                }
        }
    }

    private fun addFileName(fileName: String) {
        FileOutputStream(File(filesDir, namesFile), true)
            .bufferedWriter()
            .use { out ->
                out.append(fileName)
                out.newLine()
            }

    }
}

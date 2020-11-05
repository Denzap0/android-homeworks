package com.example.homework6

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.text.Editable
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.edit_file_activity.*
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class EditFileActivity : AppCompatActivity() {

    private lateinit var file: File
    private lateinit var fileNames : ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_file_activity)

        val bundle: Bundle? = intent.extras
        file = bundle!!.get("file") as File
        fileNames = bundle.getStringArrayList("fileNames") as ArrayList<String>
        nameOfFileForEdit.setText(file.name.toString())
        text.setText(
            FileInputStream(file)
                .bufferedReader()
                .readText()
        )
        editButton.setOnClickListener {
            if (fileNames.contains(nameOfFileForEdit.text.toString()) && nameOfFileForEdit.text.toString() != file.name) {
                showContainsDialog()
            }else{
                file.delete()
                val newFile : File = File(filesDir, nameOfFileForEdit.text.toString())
                FileOutputStream(newFile)
                    .bufferedWriter()
                    .write(text.text.toString())
                val intent : Intent = Intent()
                intent.putExtra("oldFile", file)
                intent.putExtra("newFile", newFile)
                intent.putExtra("isRemove", false)
                setResult(Activity.RESULT_OK, intent)
            }
        }

        deleteButton.setOnClickListener {
            file.delete()
            val intent : Intent = Intent()
            intent.putExtra("oldFile", file)
            intent.putExtra("isRemove", false)
            setResult(Activity.RESULT_OK, intent)
        }

    }

    private fun showContainsDialog() {
        AlertDialog.Builder(this)
            .setTitle("File with this name already exist")
            .show()
    }
}
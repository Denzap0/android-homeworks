package com.example.homework6

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.edit_file_activity.*
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class EditFileActivity : AppCompatActivity() {

    private lateinit var file: File
    private lateinit var fileNames: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_file_activity)

        val bundle: Bundle? = intent.extras
        file = bundle?.get("file") as File
        fileNames = bundle.getStringArrayList("fileNames") as ArrayList<String>
        nameOfFileForEdit.setText(file.name.toString())
        if (file.exists()) {
            val fIn = FileInputStream(file)
                .bufferedReader()
                .use { iIn -> iIn.readText() }

            text.setText(fIn)
        }
        editButton.setOnClickListener {
            if (fileNames.contains(nameOfFileForEdit.text.toString()) && nameOfFileForEdit.text.toString() != file.name) {
                showContainsDialog()
            } else {
                val newFile = File(file.parentFile, nameOfFileForEdit.text.toString())
                finishActivity(newFile)
            }
        }
        deleteButton.setOnClickListener {
            finishActivity(null)
        }

    }

    private fun finishActivity(newFile: File?){
        val intent = Intent()
        intent.putExtra("oldFile", file)
        intent.putExtra("isRemove", newFile == null)
        if(newFile != null){
            intent.putExtra("newFile", newFile)
            updateFile(newFile,file,text.text.toString())
            setResult(RESULT_OK, intent)
            finish()
        }else{
            deleteFile(file)
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    private fun showContainsDialog() {
        AlertDialog.Builder(this)
            .setTitle("File with this name already exist")
            .setPositiveButton("Ok", null)
            .show()
    }
}
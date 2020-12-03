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
                val newFile : File = File(file.parentFile, nameOfFileForEdit.text.toString())
                finishActivityEdit(newFile)
            }
        }
        deleteButton.setOnClickListener {
            finishActivityRemove()
        }

    }

    private fun finishActivityEdit(newFile : File){
        FileService.updateFile(newFile, file, text.text.toString())
        val intent: Intent = Intent()
        intent.putExtra("oldFile", file)
        intent.putExtra("newFile", newFile)
        intent.putExtra("isRemove", false)
        setResult(RESULT_OK, intent)
        finish()
    }

    private fun finishActivityRemove(){
        FileService.deleteFile(file)
        val intent: Intent = Intent()
        intent.putExtra("oldFile", file)
        intent.putExtra("isRemove", true)
        setResult(RESULT_OK, intent)
        finish()
    }

    private fun showContainsDialog() {
        AlertDialog.Builder(this)
            .setTitle("File with this name already exist")
            .setPositiveButton("Ok", null)
            .show()
    }
}
package com.example.homework6

import FileRecyclerAdapter
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.edit_file_activity.*
import java.io.File
import java.io.FileOutputStream

class FileService {

    companion object{
        fun updateFile(newFile :File, oldFile : File, text : String){
            oldFile.delete()
            FileOutputStream(newFile)
                .bufferedWriter()
                .use { out ->
                    out.write(text)
                }
        }

        fun deleteFile(file : File){
            file.delete()
        }

        fun addFile(file: File, files : MutableList<File>, fileNames : MutableList<String>, storagePath : String?) {
            file.createNewFile()
            files.add(File(storagePath, file.name))
            fileNames.add(file.name)

        }

        fun updateLocalFiles(fileNames : MutableList<String>, files : MutableList<File>, storagePath : String?, filesRecyclerView : RecyclerView, listItemActionListener : ListItemActionListener) {
            if (fileNames.isNotEmpty()) {
                files.clear()
                fileNames.forEach {
                    files.add(File(storagePath, it))
                }
            }
            filesRecyclerView.adapter = FileRecyclerAdapter(files, listItemActionListener)
        }

        fun updateFileNames(storagePath: String?, namesFile : String, fileNames: MutableList<String>) {
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

        fun addFileNameInFiles(fileName: String, storagePath: String?, namesFile: String) {
            if(File(storagePath, namesFile).exists()) {
                FileOutputStream(File(storagePath, namesFile), true)
                    .bufferedWriter()
                    .use { out ->
                        out.append(fileName)
                        out.newLine()
                    }
            }
        }


    }
}
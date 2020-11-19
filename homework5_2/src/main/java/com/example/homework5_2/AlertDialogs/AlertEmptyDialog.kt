package com.example.homework5_2.AlertDialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatDialogFragment

class AlertEmptyDialog : AppCompatDialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(activity)
            .setTitle("Attemption")
            .setMessage("Name cant be empty")
            .setPositiveButton("Ok", null)
            .create()

}
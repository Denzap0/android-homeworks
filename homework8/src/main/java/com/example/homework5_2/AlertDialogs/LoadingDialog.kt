package com.example.homework5_2.AlertDialogs

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import com.example.homework5_2.R

class LoadingDialog(private val activity: Activity) {

    private var alertDialog: AlertDialog = AlertDialog.Builder(activity).create()

    public fun startLoadingDialog() {
        val inflater = activity.layoutInflater
        val builder = AlertDialog.Builder(activity)
            .setView(inflater.inflate(R.layout.loading_dialog, null))
            .setCancelable(false)
        alertDialog = builder.create()
        alertDialog.show()
    }

    public fun dismissDialog() {
        alertDialog.dismiss()

    }

}
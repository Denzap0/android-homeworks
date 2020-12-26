package com.example.homework9.presentation.citieslist

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.DialogFragment

class AddCityDialog(
    private val presenter: CitiesActivityPresenter) : DialogFragment() {
    @SuppressLint("SetTextI18n")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogLayout = LinearLayout(context)
        var editCityName = EditText(context)
        var errorTextView = TextView(context)
        dialogLayout.addView(editCityName)
        dialogLayout.addView(errorTextView)
        return  AlertDialog.Builder(context)
            .setTitle("Add city")
            .setView(dialogLayout)
            .setPositiveButton("Add") { dialog, which ->
                if(!presenter.addCity(editCityName.text.toString())){
                    errorTextView.text = "Wrong city name or city already exist"
                    dialog.dismiss()
                }else{

                }

            }
            .create()
    }
}
package com.example.homework9.presentation.citieslist.citylist

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.DialogFragment

class AddCityDialog(
    private val presenter: CitiesActivityPresenter
) : DialogFragment() {
    lateinit var editCityName : EditText
    lateinit var errorTextView : TextView
    @SuppressLint("SetTextI18n")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogLayout = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
        }
        editCityName = EditText(context)
        errorTextView = TextView(context).apply {
            setTextColor(Color.RED)
        }

        dialogLayout.addView(editCityName)
        dialogLayout.addView(errorTextView)
        val builder = AlertDialog.Builder(context)
            .setTitle("Add city")
            .setView(dialogLayout)
            .setPositiveButton("Add",null)
            .create()
        builder.setOnShowListener {
            val btn = (builder as AlertDialog).getButton(AlertDialog.BUTTON_POSITIVE)
            btn.setOnClickListener {
                presenter.addCity(editCityName.text.toString())
            }
        }
        return builder
    }
}
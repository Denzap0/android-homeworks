package com.example.homework9mvvm.viewmodel.citylist

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
    @SuppressLint("SetTextI18n")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogLayout = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
        }
        var editCityName = EditText(context)
        var errorTextView = TextView(context).apply {
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
                if (!presenter.addCity(editCityName.text.toString())){
                    errorTextView.text = "There is no this name or name already exist "
                }else{
                    builder.dismiss()
                }
            }
        }
        return builder
    }
}
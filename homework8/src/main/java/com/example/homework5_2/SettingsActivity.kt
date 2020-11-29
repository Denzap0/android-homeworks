package com.example.homework5_2

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.example.homework5_2.Settings.AsyncSettingsPreference
import com.example.homework5_2.databinding.SettingsActivityBinding
import kotlin.properties.Delegates

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: SettingsActivityBinding
    private lateinit var asyncSettingsPreference: AsyncSettingsPreference
    private var asyncType by Delegates.notNull<Int>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SettingsActivityBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        asyncSettingsPreference = AsyncSettingsPreference(getSharedPreferences("asyncType", MODE_PRIVATE))
        asyncType = asyncSettingsPreference.loadAsyncType()
        when (asyncType) {

            1 -> binding.executorCheckBox.isChecked = true
            2 -> binding.futureCheckBox.isChecked = true
            3 -> binding.rxCheckBox.isChecked = true
        }
        binding.executorCheckBox.setOnCheckedChangeListener { checkBox, isChecked ->
            if (isChecked) {
                binding.futureCheckBox.isChecked = false
                binding.rxCheckBox.isChecked = false
                asyncSettingsPreference.saveAsyncType(1)
            } else {
                checkBox.isChecked = false
            }
        }
        binding.futureCheckBox.setOnCheckedChangeListener { checkBox, isChecked ->
            if (isChecked) {
                binding.executorCheckBox.isChecked = false
                binding.rxCheckBox.isChecked = false
                asyncSettingsPreference.saveAsyncType(2)
            } else {
                checkBox.isChecked = false
            }
        }
        binding.rxCheckBox.setOnCheckedChangeListener { checkBox, isChecked ->
            if (isChecked) {
                binding.executorCheckBox.isChecked = false
                binding.futureCheckBox.isChecked = false
                asyncSettingsPreference.saveAsyncType(3)
            } else {
                checkBox.isChecked = false
            }
        }

        binding.backToMain.setOnClickListener{
            finish()
        }
    }
}
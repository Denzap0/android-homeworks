package com.example.homework9.view

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.homework9.R
import com.example.homework9.databinding.CitiesActivityBinding
import com.example.homework9.presentation.citieslist.citylist.AddCityDialog
import com.example.homework9.presentation.citieslist.citylist.CitiesActivityPresenter
import com.example.homework9.presentation.citieslist.citylist.CitiesActivityPresenterImpl
import com.example.homework9.presentation.citieslist.citylist.CitiesListView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CitiesActivity() : AppCompatActivity(), CitiesListView {

    private lateinit var presenter : CitiesActivityPresenter
    private lateinit var binding : CitiesActivityBinding
    private lateinit var recyclerView : RecyclerView
    private lateinit var adapter : CitiesActivityAdapter
    private lateinit var addCityButton : FloatingActionButton
    private lateinit var saveButton : Button
    private lateinit var addCityDialog : AddCityDialog



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cities_activity)

        binding = CitiesActivityBinding.inflate(layoutInflater)
        presenter = CitiesActivityPresenterImpl(this, application)
        addCityButton = findViewById(R.id.addCityButton)
        recyclerView = findViewById(R.id.citiesRecyclerView)
        saveButton = findViewById(R.id.save_button)
        addCityDialog = AddCityDialog(presenter)
        adapter = CitiesActivityAdapter({data ->
            presenter.setChosenCity(data.name)
            adapter.updateChosenCity(data.name)
        }, presenter.getChosenCity()!!)
        recyclerView.apply {
            adapter = this@CitiesActivity.adapter
            layoutManager = LinearLayoutManager(this@CitiesActivity, RecyclerView.VERTICAL, false)
        }

        saveButton.setOnClickListener{
            setResult(1)
            finish()
        }
        addCityButton.setOnClickListener {
            addCityDialog.show(supportFragmentManager,"Add city")
        }
        presenter.fetchCitiesList()
    }

    override fun showCitiesList(citiesList: List<CityDataView>, chosenCityName : String) {
        adapter.updateCitiesList(citiesList, chosenCityName)
    }

    override fun closeDialog() {
        addCityDialog.dismiss()
    }

}
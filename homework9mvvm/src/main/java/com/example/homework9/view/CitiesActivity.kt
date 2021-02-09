package com.example.homework9mvvm.view

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.homework9.R
import com.example.homework9.databinding.CitiesActivityBinding
import com.example.homework9mvvm.viewmodel.citylist.AddCityDialog
import com.example.homework9mvvm.viewmodel.citylist.CitiesActivityPresenter
import com.example.homework9mvvm.viewmodel.citylist.CitiesActivityPresenterImpl
import com.example.homework9mvvm.viewmodel.citylist.CitiesListView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CitiesActivity() : AppCompatActivity(), CitiesListView {

    private lateinit var presenter : CitiesActivityPresenter
    private lateinit var binding : com.example.homework9.databinding.CitiesActivityBinding
    private lateinit var recyclerView : RecyclerView
    private lateinit var adapter : CitiesActivityAdapter
    private lateinit var addCityButton : FloatingActionButton
    private lateinit var saveButton : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cities_activity)

        binding = CitiesActivityBinding.inflate(layoutInflater)
        presenter = CitiesActivityPresenterImpl(this, application)
        addCityButton = findViewById(R.id.addCityButton)
        recyclerView = findViewById(R.id.citiesRecyclerView)
        saveButton = findViewById(R.id.save_button)
        adapter = CitiesActivityAdapter({data ->
            presenter.setChosenCity(data.name)
            adapter.updateChosenCity(data.name)
        }, presenter.getChosenCity())
        recyclerView.apply {
            adapter = this@CitiesActivity.adapter
            layoutManager = LinearLayoutManager(this@CitiesActivity, RecyclerView.VERTICAL, false)
        }

        saveButton.setOnClickListener{
            setResult(1)
            finish()
        }
        addCityButton.setOnClickListener {
            showAddCityDialog()
        }
        presenter.fetchCitiesList()
    }

    override fun onStartLoading() {

    }

    override fun onStopLoading() {

    }

    override fun onError(message: String) {

    }

    override fun showCitiesList(citiesList: List<CityDataView>, chosenCityName : String) {
        adapter.updateCitiesList(citiesList, chosenCityName)
    }

    private fun showAddCityDialog(){
        val addCityDialog = AddCityDialog(presenter)
        addCityDialog.show(supportFragmentManager, "dialog")
    }

}
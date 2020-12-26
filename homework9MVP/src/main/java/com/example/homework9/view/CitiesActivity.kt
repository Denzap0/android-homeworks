package com.example.homework9.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.homework9.R
import com.example.homework9.databinding.CitiesActivityBinding
import com.example.homework9.presentation.citieslist.AddCityDialog
import com.example.homework9.presentation.citieslist.CitiesActivityPresenter
import com.example.homework9.presentation.citieslist.CitiesActivityPresenterImpl
import com.example.homework9.presentation.citieslist.CitiesListView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CitiesActivity : AppCompatActivity(), CitiesListView {

    private lateinit var presenter : CitiesActivityPresenter
    private lateinit var binding : CitiesActivityBinding
    private lateinit var recyclerView : RecyclerView
    private lateinit var adapter : CitiesActivityAdapter
    private lateinit var addCityButton : FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cities_activity)
        binding = CitiesActivityBinding.inflate(layoutInflater)
        presenter = CitiesActivityPresenterImpl(this, application)
        addCityButton = findViewById(R.id.addCityButton)
        recyclerView = findViewById(R.id.citiesRecyclerView)
        adapter = CitiesActivityAdapter({data -> }, presenter.getChosenCity())
        recyclerView.apply {
            adapter = this@CitiesActivity.adapter
            layoutManager = LinearLayoutManager(this@CitiesActivity, RecyclerView.VERTICAL, false)
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
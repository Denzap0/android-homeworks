package com.example.homework9.view

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.homework9.R
import com.example.homework9.databinding.CitiesActivityBinding
import com.example.homework9.presentation.citieslist.citylist.AddCityDialog
import com.example.homework9.presentation.citieslist.citylist.CitiesActivityViewModel
import com.example.homework9.presentation.citieslist.citylist.CitiesActivityViewModelmpl
import com.example.homework9.presentation.citieslist.citylist.CitiesListView
import com.example.homework9.presentation.citieslist.citylist.CitiesViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CitiesActivity() : AppCompatActivity(), CitiesListView {

    private lateinit var viewModel : CitiesActivityViewModel
    private lateinit var binding : CitiesActivityBinding
    private lateinit var recyclerView : RecyclerView
    private lateinit var adapter : CitiesActivityAdapter
    private lateinit var addCityButton : FloatingActionButton
    private lateinit var saveButton : Button
    private lateinit var citiesViewModelFactory : CitiesViewModelFactory


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cities_activity)

        citiesViewModelFactory = CitiesViewModelFactory(application)
        initViewModel()
        binding = CitiesActivityBinding.inflate(layoutInflater)
        addCityButton = findViewById(R.id.addCityButton)
        recyclerView = findViewById(R.id.citiesRecyclerView)
        saveButton = findViewById(R.id.save_button)
        adapter = CitiesActivityAdapter({data ->
            viewModel.setChosenCity(data.name)
            adapter.updateChosenCity(data.name)
        }, viewModel.getChosenCity())
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
        viewModel.fetchCitiesList()
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
        val addCityDialog = AddCityDialog(viewModel)
        addCityDialog.show(supportFragmentManager, "dialog")
    }

    private fun initViewModel(){
        viewModel = ViewModelProvider(this, citiesViewModelFactory).get(CitiesActivityViewModelmpl::class.java)
        (viewModel as CitiesActivityViewModelmpl).citiesLiveData.observe(this, {data -> showCitiesList(data, viewModel.getChosenCity())})
    }

}
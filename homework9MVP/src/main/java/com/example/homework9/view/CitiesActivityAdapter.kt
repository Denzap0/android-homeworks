package com.example.homework9.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.homework9.R
import kotlinx.android.synthetic.main.item_city.view.cityName

class CitiesActivityAdapter(
    private val itemClickListener: (CityDataView) -> Unit,
    private var chosenCityName: String
) : RecyclerView.Adapter<CitiesActivityAdapter.ItemViewHolder>() {

    private val citiesList = mutableListOf<CityDataView>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CitiesActivityAdapter.ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_city, parent, false)
        return ItemViewHolder(view, itemClickListener)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(citiesList[position], chosenCityName == citiesList[position].name)
    }

    override fun getItemCount(): Int = citiesList.size

    fun updateCitiesList(citiesListNew: List<CityDataView>, chosenCityName: String) {
        citiesList.apply {
            clear()
            addAll(citiesListNew)
        }
        this.chosenCityName = chosenCityName
        notifyDataSetChanged()
    }

    fun updateChosenCity(name: String) {
        chosenCityName = name
        notifyDataSetChanged()
    }

    class ItemViewHolder(
        private val view: View,
        private val itemClickListener: (CityDataView) -> Unit
    ) : RecyclerView.ViewHolder(view) {
        private val cityName = view.findViewById<TextView>(R.id.cityName)
        private val isChosenCheck = view.findViewById<ImageView>(R.id.isChosenCheck)
        fun bind(cityDataView: CityDataView, isChosen: Boolean) {
            cityName.text = cityDataView.name
            isChosenCheck.visibility = if (isChosen) View.VISIBLE else View.INVISIBLE
            view.setOnClickListener { itemClickListener(cityDataView) }
        }
    }


}
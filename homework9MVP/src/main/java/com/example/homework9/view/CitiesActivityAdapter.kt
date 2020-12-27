package com.example.homework9.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.homework9.databinding.ItemCityBinding

class CitiesActivityAdapter(
    private val itemClickListener: (CityDataView) -> Unit,
    private var chosenCityName : String
) : RecyclerView.Adapter<CitiesActivityAdapter.ItemViewHolder>() {

    private val citiesList = mutableListOf<CityDataView>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitiesActivityAdapter.ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ItemViewHolder(ItemCityBinding.inflate(inflater), itemClickListener)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(citiesList[position], chosenCityName == citiesList[position].name)
    }

    override fun getItemCount(): Int = citiesList.size

    fun updateCitiesList(citiesListNew : List<CityDataView>, chosenCityName: String){
        citiesList.apply {
            clear()
            addAll(citiesListNew)
        }
        this.chosenCityName = chosenCityName
        notifyDataSetChanged()
    }

    fun updateChosenCity(name : String){
        chosenCityName = name
        notifyDataSetChanged()
    }

    class ItemViewHolder(
        private val binding : ItemCityBinding,
        private val itemClickListener : (CityDataView) -> Unit
    ) : RecyclerView.ViewHolder(binding.root){
        fun bind(cityDataView: CityDataView, isChosen : Boolean){
            with(binding){
                cityName.text = cityDataView.name
                isChosenCheck.visibility = if(isChosen) View.VISIBLE else View.INVISIBLE
                root.setOnClickListener { itemClickListener(cityDataView) }
            }
        }
    }



}
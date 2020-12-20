package com.example.homework9.view

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.homework9.databinding.ItemWeatherBinding

class WeatherListAdapter() : RecyclerView.Adapter<> {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ??? {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: Ite, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    class ItemViewHolder(
        private val binding : ItemWeatherBinding,
        private val itemClickListener : (WeatherDataView) -> Unit
    ) : RecyclerView.ViewHolder(binding.root){
        fun bind(weatherData : WeatherDataView){
            with(binding){

            }
        }
    }
}
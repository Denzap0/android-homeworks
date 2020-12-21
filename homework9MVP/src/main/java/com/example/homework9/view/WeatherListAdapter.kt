package com.example.homework9.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.homework9.databinding.ItemWeatherBinding

class WeatherListAdapter(private val itemClickListener: (WeatherDataView) -> Unit) : RecyclerView.Adapter<WeatherListAdapter.ItemViewHolder>() {

    private val weatherList = mutableListOf<WeatherDataView>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ItemViewHolder(ItemWeatherBinding.inflate(inflater),itemClickListener)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(weatherList[position])
    }

    override fun getItemCount(): Int = weatherList.size

    fun updateItems(weatherListData : List<WeatherDataView>){
        weatherList.apply {
            clear()
            addAll(weatherListData)
        }
        notifyDataSetChanged()
    }

    class ItemViewHolder(
        private val binding : ItemWeatherBinding,
        private val itemClickListener : (WeatherDataView) -> Unit
    ) : RecyclerView.ViewHolder(binding.root){
        fun bind(weatherData : WeatherDataView){
            with(binding){
                Glide.with(root.context).load("http://openweathermap.org/img/w/" + weatherData.iconType + ".png")
                timeTextView.text = weatherData.date.time.toString()
                weatherTextView.text = weatherData.weather
                temperatureTextView.text = weatherData.temperature.toString()
            }
        }
    }
}
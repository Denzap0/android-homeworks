package com.example.homework9.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.homework9.databinding.ItemWeatherBinding
import java.text.SimpleDateFormat

class WeatherListAdapter(private val itemClickListener: (WeatherDataView) -> Unit) :
    RecyclerView.Adapter<WeatherListAdapter.ItemViewHolder>() {

    private val weatherList = mutableListOf<WeatherDataView>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ItemViewHolder(com.example.homework9.databinding.ItemWeatherBinding.inflate(inflater), itemClickListener)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(weatherList[position])
    }

    override fun getItemCount(): Int = weatherList.size

    fun updateItems(weatherListData: List<WeatherDataView>) {
        weatherList.apply {
            clear()
            addAll(weatherListData)
        }
        notifyDataSetChanged()
    }

    class ItemViewHolder(
        private val binding: ItemWeatherBinding,
        private val itemClickListener: (WeatherDataView) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        private val simpleDateFormat = SimpleDateFormat("HH:mm")
        fun bind(weatherData: WeatherDataView) {
            with(binding) {
                Glide.with(root.context)
                    .load(com.example.homework9.presentation.citieslist.weatherlist.IMAGE_URL.format(weatherData.iconType))
                    .into(weatherImage)
                timeTextView.text = simpleDateFormat.format(weatherData.date).toString()
                weatherTextView.text = weatherData.weather
                temperatureTextView.text = weatherData.temperature.toString()
                root.setOnClickListener { itemClickListener(weatherData) }
            }
        }
    }
}
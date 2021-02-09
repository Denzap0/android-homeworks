package com.example.homework9.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.homework9.R
import java.text.SimpleDateFormat

class WeatherListAdapter(private val itemClickListener: (WeatherDataView) -> Unit) :
    RecyclerView.Adapter<WeatherListAdapter.ItemViewHolder>() {

    private val weatherList = mutableListOf<WeatherDataView>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_weather, parent, false)
        return ItemViewHolder(view, itemClickListener)
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
        private val view: View,
        private val itemClickListener: (WeatherDataView) -> Unit
    ) : RecyclerView.ViewHolder(view) {

        private val simpleDateFormat = SimpleDateFormat("HH:mm")
        private val weatherImage = view.findViewById<ImageView>(R.id.weatherImage)
        private val timeTextView = view.findViewById<TextView>(R.id.timeTextView)
        private val weatherTextView = view.findViewById<TextView>(R.id.weatherTextView)
        private val temperatureTextView = view.findViewById<TextView>(R.id.temperatureTextView)

        fun bind(weatherData: WeatherDataView) {
                Glide.with(view.context)
                    .load(com.example.homework9.presentation.citieslist.weatherlist.IMAGE_URL.format(weatherData.iconType))
                    .into(weatherImage)
                timeTextView.text = simpleDateFormat.format(weatherData.date).toString()
                weatherTextView.text = weatherData.weather
                temperatureTextView.text = weatherData.temperature.toString()
                view.setOnClickListener { itemClickListener(weatherData) }

        }
    }
}
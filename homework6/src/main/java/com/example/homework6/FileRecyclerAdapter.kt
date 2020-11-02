package com.example.homework6

import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FileRecyclerAdapter (private var data: ArrayList<File> ) : RecyclerView.Adapter<FileRecyclerAdapter.ViewHolder>() {

    class ViewHolder(private var textView: TextView, private var imageView: ImageView) : RecyclerView.ViewHolder(textView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
        return data.size
    }


}
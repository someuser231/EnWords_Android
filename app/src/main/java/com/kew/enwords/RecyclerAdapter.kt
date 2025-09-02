package com.kew.enwords

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter: RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    var words_data = mutableListOf<String>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.test_rv_card, parent, false)
        return ViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
        holder.word.text = words_data[position]
    }

    override fun getItemCount(): Int = words_data.size
    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        var word: TextView = view.findViewById(R.id.word)
    }
}
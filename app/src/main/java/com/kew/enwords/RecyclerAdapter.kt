package com.kew.enwords

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import database.DatabaseHelper
import database.WordStructure

class RecyclerAdapter(private val dbHelper: DatabaseHelper): RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    var words_data = mutableListOf<WordStructure>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.rv_card, parent, false)
        return ViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.word.text = words_data[position].word
        holder.btn_del.setOnClickListener {
            dbHelper.DeleteData(words_data[position].id)
            words_data = dbHelper.ReadData()
            this.notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int = words_data.size
    
    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        var word: TextView = view.findViewById(R.id.word)
        var btn_del: ImageButton = view.findViewById(R.id.btn_del)
    }
}
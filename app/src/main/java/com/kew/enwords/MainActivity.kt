package com.kew.enwords

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val myAdapter = RecyclerAdapter()
        val rv: RecyclerView = findViewById(R.id.recyclerView)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = myAdapter

        val txt_add: TextInputEditText = findViewById(R.id.txtInput)
        val txt_translate: TextView = findViewById(R.id.txt_translate)
        val btn_add: Button = findViewById(R.id.btn_add)
        val btn_translate: Button = findViewById(R.id.btn_translate)

        btn_add.setOnClickListener {
            myAdapter.words_data.add(txt_add.text.toString())
            myAdapter.notifyDataSetChanged()
        }
        btn_translate.setOnClickListener {
            txt_translate.text = txt_add.text.toString()
        }

    }
}
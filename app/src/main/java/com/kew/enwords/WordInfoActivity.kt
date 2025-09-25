package com.kew.enwords

import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import database.DatabaseHelper
import database.WordStructure

class WordInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_word_info)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnBack: ImageButton = findViewById(R.id.btn_back)
        btnBack.setOnClickListener {
            finish()
        }

        val db = DatabaseHelper(this)

        val wordId = intent.getIntExtra("id", -1)
        var word: WordStructure? = null
        if (wordId >= 0) {
            word = db.GetElement(wordId)
        }
        else {
            finish()
        }

        val txtWord: TextView = findViewById(R.id.txt_word)
        val txtTcUs: TextView = findViewById(R.id.txt_tcUs)
        val txtTcUk: TextView = findViewById(R.id.txt_tcUk)

        txtWord.setText(word!!.word)
        txtTcUs.setText("Амер.: [${word.tcUs}]")
        txtTcUk.setText("Брит.: [${word.tcUk}]")

    }
}
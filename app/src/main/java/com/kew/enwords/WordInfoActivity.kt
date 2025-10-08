package com.kew.enwords

import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import database.DatabaseHelper
import database.WordStructure

class WordInfoActivity : AppCompatActivity() {
    val frManager = supportFragmentManager
    var frTransaction = frManager.beginTransaction()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_word_info)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val db = DatabaseHelper(this)

        val wordId = intent.getIntExtra("id", -1)
        var word: WordStructure? = null
        if (wordId >= 0) {
            word = db.GetElement(wordId)
            frTransaction = frManager.beginTransaction()
            frTransaction.add(R.id.frame, WordInfoFr(word))
            frTransaction.commit()
        }
        else {
            finish()
        }

        val btnBack: ImageButton = findViewById(R.id.btn_back)
        var editMode: Boolean = false
        btnBack.setOnClickListener {
            if (!editMode) {
                finish()
            }
            else {
                frTransaction = frManager.beginTransaction()
                frTransaction.replace(R.id.frame, WordInfoFr(word!!))
                frTransaction.commit()
                editMode = false
            }
        }

        val btnEdit: ImageButton = findViewById(R.id.btn_edit)
        btnEdit.setOnClickListener {
            if (!editMode) {
                frTransaction = frManager.beginTransaction()
                frTransaction.replace(R.id.frame, WordInfoEditFr(word!!))
                frTransaction.commit()
                editMode = true

            }
        }
    }
}
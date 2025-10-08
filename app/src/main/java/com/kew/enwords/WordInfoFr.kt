package com.kew.enwords

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import database.WordStructure

class WordInfoFr(private val word: WordStructure) : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {  }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_word_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val txtWord: TextView = view.findViewById(R.id.txt_word)
        val txtTcUs: TextView = view.findViewById(R.id.txt_tcUs)
        val txtTcUk: TextView = view.findViewById(R.id.txt_tcUk)
        val txtTl: TextView = view.findViewById(R.id.txt_tl)

        val strTl = word.tl.replace(" | ", "\n- ")

        txtWord.setText(word.word)
        txtTcUs.setText("Амер.: [${word.tcUs}]")
        txtTcUk.setText("Брит.: [${word.tcUk}]")
        txtTl.setText(strTl)
    }
}
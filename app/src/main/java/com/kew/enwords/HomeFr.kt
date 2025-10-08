package com.kew.enwords

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.gson.Gson
import database.DatabaseHelper
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okio.IOException

class HomeFr : Fragment() {
    data class WordResp(
        val word: String,
        val tc_us: String,
        val tc_uk: String,
        val word_form: Array<String>,
        val tl: Array<String>
    )
    var wordResp: WordResp? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {  }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val txtInput: EditText = view.findViewById(R.id.txt_input)
        val btnAdd: ImageButton = view.findViewById(R.id.btn_add)
        val rgrpTl: RadioGroup = view.findViewById(R.id.rgrp_tl)

        val db = DatabaseHelper(view.context)
        btnAdd.setOnClickListener {
            if (wordResp != null) {
                addWordToDB(db, wordResp!!)
            } else {
                Toast.makeText(this.context, "Word is null", Toast.LENGTH_SHORT).show()
            }
        }

        val timerTl = object: CountDownTimer(1000, 1000) {
            override fun onFinish() {
                translateWhApi(view, txtInput.text.toString())
            }

            override fun onTick(millisUntilFinished: Long) {
                return
            }
        }
        txtInput.setOnKeyListener {_, keyCode, event ->
            wordResp = null
            timerTl.cancel()
            timerTl.start()
            return@setOnKeyListener true
        }

        rgrpTl.setOnCheckedChangeListener { group, i ->
            val idWh = R.id.btn_wh
            val idLibre = R.id.btn_libre
            val clrActive = ContextCompat.getColor(requireContext(), R.color.white)
            val clrNormal = ContextCompat.getColor(requireContext(), R.color.dark_gray)

            when(i) {
                idWh -> {
                    group.findViewById<RadioButton>(idLibre).setTextColor(clrNormal)
                }
                idLibre -> {
                    group.findViewById<RadioButton>(idWh).setTextColor(clrNormal)
                }
            }
            group.findViewById<RadioButton>(group.checkedRadioButtonId).setTextColor(clrActive)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFr().apply {
                arguments = Bundle().apply {

                }
            }
    }

    private fun translateWhApi(view: View, searchingWord: String) {
        val client = OkHttpClient()
        val request = Request.Builder().url("http://10.0.2.2:8080/whapi/$searchingWord").build()
        client.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call, e: java.io.IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) {
                        throw IOException(response.message)
                    }
                    val gson = Gson()
                    wordResp = gson.fromJson(response.body!!.string(), WordResp::class.java)
                    drawWordInfo(view, wordResp!!)
                }
            }
        })
    }

    private fun drawWordInfo(view: View, word: WordResp) {
        val txtTc: TextView = view.findViewById(R.id.txt_tc)
        val txtTl: TextView = view.findViewById(R.id.txt_tl)
        var outputTc = ""
        var outputTl = ""

        if (word.tc_us.isNotEmpty()) {
            outputTc += "амер. [" + word.tc_us + "]\n"
        }
        if (word.tc_uk.isNotEmpty()) {
            outputTc += "брит. [" + word.tc_uk + "]"
        }

        if (word.word_form.isNotEmpty()) {
            outputTl += "Форма слова:\n"
            for (i in word.word_form) {
                outputTl += "   - $i\n"
            }
            outputTl += "\n"
        }
        if (word.tl.isNotEmpty()) {
            outputTl += "Перевод:\n"
            for (i in word.tl) {
                outputTl += "   - $i\n"
            }
        }

        this.activity?.runOnUiThread {
            txtTc.text = outputTc
            txtTl.text = outputTl
        }
    }

    private fun addWordToDB(db: DatabaseHelper, word: WordResp) {
        db.AddData(word.word, word.tc_us, word.tc_uk, word.word_form, word.tl)
    }
}
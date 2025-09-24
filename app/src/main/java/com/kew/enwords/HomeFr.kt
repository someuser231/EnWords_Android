package com.kew.enwords

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
import database.DatabaseHelper
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.internal.http.promisesBody
import okio.IOException
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFr.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFr : Fragment() {
    // TODO: Rename and change types of parameters
    private var db: DatabaseHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val txt_add: TextInputEditText = view.findViewById(R.id.txtInput)
        val btn_add: Button = view.findViewById(R.id.btn_add)
        val btn_translate: Button = view.findViewById(R.id.btn_translate)

        val db = DatabaseHelper(view.context)
        btn_add.setOnClickListener {
            db.AddData(txt_add.text.toString(), "", "")
        }
        btn_translate.setOnClickListener {
            translateWhApi(view, txt_add.text.toString())
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFr.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFr().apply {
                arguments = Bundle().apply {

                }
            }
    }

    data class WordResp(val word: String, val tc_us: String, val tc_uk: String, val word_form: Array<String>, val tl: Array<String>)
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
                    drawWordInfo(view, gson.fromJson(response.body!!.string(), WordResp::class.java))
                }
            }
        })
    }

    private fun drawWordInfo(view: View, word: WordResp) {
        val txt_translate: TextView = view.findViewById(R.id.txt_translate)
        var outputString = ""

        if (word.word_form.size > 0) {
            outputString += "Форма слова:\n"
            for (i in word.word_form) {
                outputString += "   - $i\n"
            }
            outputString += "\n"
        }

        if (word.tl.size > 0) {
            outputString += "Перевод:\n"
            for (i in word.tl) {
                outputString += "   - $i\n"
            }
        }


        this.activity?.runOnUiThread {
            txt_translate.text = outputString
        }
    }
}
package com.kew.enwords

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.google.android.material.textfield.TextInputEditText
import database.DatabaseHelper

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
        val txt_translate: TextView = view.findViewById(R.id.txt_translate)
        val btn_add: Button = view.findViewById(R.id.btn_add)
        val btn_translate: Button = view.findViewById(R.id.btn_translate)

        val db = DatabaseHelper(view.context)
        btn_add.setOnClickListener {
            db.AddData(txt_add.text.toString(), "", "")
        }
        btn_translate.setOnClickListener {
            txt_translate.text = txt_add.text.toString()
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
}
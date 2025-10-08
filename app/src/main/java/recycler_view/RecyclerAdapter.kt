package recycler_view

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.kew.enwords.R
import com.kew.enwords.WordInfoActivity
import database.DatabaseHelper
import database.WordStructure

class RecyclerAdapter(private val dbHelper: DatabaseHelper, private val context: Context): RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    var words_data = mutableListOf<WordStructure>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.rv_card, parent, false)
        return ViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.word.text = words_data[position].word
        holder.body.setOnClickListener {
            val intent = Intent(context, WordInfoActivity::class.java)
            intent.putExtra("id", words_data[position].id)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int = words_data.size

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        var word: TextView = view.findViewById(R.id.word)
        val body: CardView = view.findViewById(R.id.rv_card)
    }

    fun DeleteData(itemId: Int) {
        dbHelper.DeleteElement(itemId)
        words_data = dbHelper.ReadData()
        this.notifyDataSetChanged()
    }
}
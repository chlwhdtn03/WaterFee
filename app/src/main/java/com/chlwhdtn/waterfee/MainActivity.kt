package com.chlwhdtn.waterfee

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {

    companion object {
        lateinit var prefs: DataManager
    }

    lateinit var adapter: CardAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        prefs = DataManager(this)
        adapter = CardAdapter(prefs.list, this)
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        System.out.println(prefs.list.size)

        val input_price: EditText = findViewById(R.id.input_price);
        val view_price: TextView = findViewById(R.id.view_price);
        input_price.setText(prefs.price.toString())
        view_price.text = String.format("%,d 원", input_price.text.toString().toInt(), 3)
        input_price.addTextChangedListener {
            if(input_price.text.isNotEmpty()) {
                view_price.text = String.format("%,d 원", input_price.text.toString().toInt(), 3)
                prefs.price = input_price.text.toString().toInt()
                adapter.notifyDataSetChanged()
            }
        }

        val manual_btn: MaterialButton = findViewById(R.id.manual_btn)
        manual_btn.setOnClickListener {
            adapter.notifyDataSetChanged()
        }

        val recyclerView: RecyclerView = findViewById(R.id.recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        recyclerView.post {
            adapter.notifyDataSetChanged()
        }
    }
}
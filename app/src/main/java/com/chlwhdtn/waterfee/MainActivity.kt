package com.chlwhdtn.waterfee

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.addTextChangedListener

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val input_price: EditText = findViewById(R.id.input_price);
        val view_price: TextView = findViewById(R.id.view_price);
        input_price.addTextChangedListener {
            view_price.text = String.format("%,d 원", input_price.text.toString().toInt(), 3)
        }
    }
}
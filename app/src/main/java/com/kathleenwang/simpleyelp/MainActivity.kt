package com.kathleenwang.simpleyelp

import android.R
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.kathleenwang.simpleyelp.R.layout.activity_main)
        btn_main_go.setOnClickListener {
            val intent = Intent(this, RestaurantActivity::class.java)
            val location = editText_main_location.text.toString()
            var choice = "coffee"
            if (radio_main_tea.isChecked) {
                choice = "tea"
            }
            intent.putExtra("location", location);
            intent.putExtra("choice", choice)
            startActivity(intent)
        }
    }
}




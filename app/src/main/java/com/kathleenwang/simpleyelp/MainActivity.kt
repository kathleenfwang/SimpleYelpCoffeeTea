package com.kathleenwang.simpleyelp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_main_go.setOnClickListener {
            val intent = Intent(this, RestaurantActivity::class.java)
            startActivity(intent)
        }
    }

}




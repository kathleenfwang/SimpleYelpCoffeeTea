package com.kathleenwang.simpleyelp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_restaurant.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
private const val TAG ="MainActivity"
public const val BASE_URL = "https://api.yelp.com/v3/"
public const val API_KEY = "-zAwAo1fT9CXF9XM1FnCNExAG930j7UTOtG_NMgFPgUa8IBIJhD5WDCCg4Zz45M6K9VGXnYXNdSbFDOhaKDAMKj1wv6L000PAeTQJ_6HFcbiP-wUONHFgq7O_YLrX3Yx"

class RestaurantActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant)
        // take response and create recycler view and render data
        // recycler view uses an adapter to render list item data
        var SEARCH =   getIntent().getStringExtra("choice");
        var LOCATION =  getIntent().getStringExtra("location");
        editLocation.setText(LOCATION);
        categoryButton.setText(SEARCH);
        startButton.setOnClickListener {
            LOCATION = editLocation.text.toString()
            getRestaurant(SEARCH!!,LOCATION!!)
        }
        categoryButton.setOnClickListener {
            if (categoryButton.text == "coffee") {
                categoryButton.text = "tea"
                SEARCH = "tea"
            }
            else {
                categoryButton.text =  "coffee"
                SEARCH = "coffee"
            }
            getRestaurant(SEARCH!!,LOCATION!!)
        }
        getRestaurant(SEARCH!!,LOCATION!!)
    }
    private fun getRestaurant(SEARCH:String, LOCATION:String) {
        val restaurants = mutableListOf<YelpRestaurants>()
        val adapter = RestaurantsAdapter(this, restaurants)
        rvRestaurants.adapter = adapter
        adapter.onItemClick = { restaurant ->
            // do something with your item
            Log.d("TAG", restaurant.toString())
            val intent = Intent(this,SecondRestaurantActivity::class.java)
            intent.putExtra("restaurant",restaurant)
            startActivity(intent)
        }
        rvRestaurants.layoutManager = LinearLayoutManager(this)
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val yelpService = retrofit.create(YelpService::class.java)

        yelpService.searchRestaurant("Bearer $API_KEY", SEARCH, LOCATION)
            .enqueue(object : Callback<YelpSearchResult> {
                override fun onResponse(
                    call: Call<YelpSearchResult>,
                    response: Response<YelpSearchResult>
                ) {
                    val body = response.body()
                    if (body == null) {
                        Log.w(TAG, "Didnt receive body...exiting")
                        return
                    }
                    restaurants.addAll(body.restaurants)
                    adapter.notifyDataSetChanged()
                }
                override fun onFailure(call: Call<YelpSearchResult>, t: Throwable) {
                    Log.d(TAG, "Failure: $t")
                }
            })
    }




}
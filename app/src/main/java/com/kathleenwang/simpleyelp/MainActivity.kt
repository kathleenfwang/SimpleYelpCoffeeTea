package com.kathleenwang.simpleyelp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlinx.android.synthetic.main.activity_main.*

private const val TAG ="MainActivity"
private const val BASE_URL = "https://api.yelp.com/v3/"
private const val API_KEY = "-zAwAo1fT9CXF9XM1FnCNExAG930j7UTOtG_NMgFPgUa8IBIJhD5WDCCg4Zz45M6K9VGXnYXNdSbFDOhaKDAMKj1wv6L000PAeTQJ_6HFcbiP-wUONHFgq7O_YLrX3Yx"
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // take response and create recycler view and render data
        val restaurants = mutableListOf<YelpRestaurants>()
        val adapter = RestaurantsAdapter(this, restaurants)
        // recycler view uses an adapter to render list item data
        rvRestaurants.adapter = adapter
        rvRestaurants.layoutManager = LinearLayoutManager(this)
        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val yelpService = retrofit.create(YelpService::class.java)
        yelpService.searchRestaurant("Bearer $API_KEY","coffee,tea", "San Francisco")
            .enqueue(object : Callback<YelpSearchResult> {
                override fun onResponse(call: Call<YelpSearchResult>, response: Response<YelpSearchResult>) {

                    Log.d(TAG, "Response ${response}")
                    val body = response.body()
                    if (body == null) {
                        Log.w(TAG, "Didnt receive body...exiting")
                        return
                    }
                    Log.d(TAG, "Size: ${body.restaurants.size}")
                    restaurants.addAll(body.restaurants)
                    adapter.notifyDataSetChanged()
                }

                override fun onFailure(call: Call<YelpSearchResult>, t: Throwable) {
                    Log.d(TAG, "Failure: $t")
                }

            })
    }
}
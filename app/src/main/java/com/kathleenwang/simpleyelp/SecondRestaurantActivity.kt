package com.kathleenwang.simpleyelp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_second_restaurant.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
private const val TAG ="SecondActivity"
class SecondRestaurantActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second_restaurant)
        val bundle = getIntent().getExtras()
        if (bundle !== null) {
            secondtvName.text = bundle.getString("name")
            val id = bundle.getString("id")
            getData(id!!)
        }
    }

    private fun getData(id: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val yelpBusinessService = retrofit.create(YelpBusinessService::class.java)
        yelpBusinessService.getRestaurantById("Bearer $API_KEY", id )
            .enqueue(object : Callback<YelpRestaurantResult> {
                override fun onResponse(
                    call: Call<YelpRestaurantResult>,
                    response: Response<YelpRestaurantResult>
                ) {
                    Log.d(TAG, "Response ${response}")
                    val body = response.body()
                    if (body == null) {
                        Log.w(TAG, "Didnt receive body...exiting")
                        return }
                }

                override fun onFailure(call: Call<YelpRestaurantResult>, t: Throwable) {
                    Log.d(TAG, "Failure: $t")
                }
            })
    }
}
package com.kathleenwang.simpleyelp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import kotlinx.android.synthetic.main.activity_second_restaurant.*
import kotlinx.android.synthetic.main.item_restaurant.view.*
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
        val restaurant = intent.getParcelableExtra<YelpRestaurants>("restaurant")
        if (restaurant !== null) {
            secondRatingBar.rating = restaurant.rating.toFloat()
            secondtvName.text = restaurant.name
            val id = restaurant.id
            getData(this,id, restaurant)
        }
    }

    private fun getData(context: Context, id: String, restaurant: YelpRestaurants) {
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
//                    secondIsOpen.text = if (body.is_closed ) "Open" else "Closed"
                    secondPhoneNumber.text = body.display_phone
                    Glide.with(context).load(body.photos?.get(0))
                        .fitCenter()
                        .transform( RoundedCornersTransformation(20, 5))
                        .into(secondtvImage.imageView)

                }

                override fun onFailure(call: Call<YelpRestaurantResult>, t: Throwable) {
                    Log.d(TAG, "Failure: $t")
                }
            })
    }
}
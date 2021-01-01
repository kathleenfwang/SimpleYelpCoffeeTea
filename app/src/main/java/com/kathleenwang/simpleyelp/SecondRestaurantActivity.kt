package com.kathleenwang.simpleyelp

import android.content.Context
import android.content.Intent
import android.net.Uri
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
            displayAddress.text = restaurant.location.address
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
                        return
                    }
                    fullUrlButton.setOnClickListener {
                        val openURL = Intent(Intent.ACTION_VIEW)
                        openURL.data = Uri.parse(body.url)
                        startActivity(openURL)
                    }
                    if (body.is_closed ) {
                        secondIsOpen.setTextColor(resources.getColor(R.color.teal_200))
                        secondIsOpen.text =  "Open" }
                else {
                    secondIsOpen.setTextColor(resources.getColor(R.color.red_200))
                    secondIsOpen.text ="Closed" }
                        secondPhoneNumber.text = body.display_phone
                    var int = 0
                        Glide.with(context).load(body.photos[int])
                            .fitCenter()
                            .transform(RoundedCornersTransformation(20, 5))
                            .into(secondtvImage)
                    nextImageButton.setOnClickListener {
                        int = if (int + 1 < 3) {
                            int + 1 } else { 0 }
                            Glide.with(context).load(body.photos[int])
                                .fitCenter()
                                .transform(RoundedCornersTransformation(20, 5))
                                .into(secondtvImage)
                    }
                }

                override fun onFailure(call: Call<YelpRestaurantResult>, t: Throwable) {
                    Log.d(TAG, "Failure: $t")
                }
            })
    }
}
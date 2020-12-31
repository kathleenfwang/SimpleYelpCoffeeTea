package com.kathleenwang.simpleyelp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

public interface YelpBusinessService {
        @GET("businesses/{id}")
        fun getRestaurantById(
            @Header("Authorization") authorization: String,
            @Path("id") id:String) :
        // what we get back:
                Call<YelpRestaurantResult>

}
package com.kathleenwang.simpleyelp

import com.google.gson.annotations.SerializedName
// serializedName is part of gson for gson annotation to work
// dont need to specify serialized name if it exactly matches the json parameter
data class YelpSearchResult (
    @SerializedName("total") val total: Int,
    @SerializedName("businesses") val restaurants: List<YelpRestaurants>
        )

data class YelpRestaurants (
    val name: String,
    val rating: Double,
    val price: String,
    @SerializedName("review_count") val numReviews: Int,
    @SerializedName("distance") val distanceInMeters: Double,
    @SerializedName("image_url") val imageUrl : String,
    val location: YelpLocation,
    val categories: List<YelpCategory>,
        ) {
    fun displayDistance() :String {
        val milesPerMeter = 0.000621371
        return "${milesPerMeter * distanceInMeters} mi"
    }
}
data class YelpLocation (
    @SerializedName("address1") val address : String
        )
data class YelpCategory (
    val title: String
        )

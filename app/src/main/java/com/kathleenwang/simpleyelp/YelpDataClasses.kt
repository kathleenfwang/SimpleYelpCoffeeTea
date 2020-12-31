package com.kathleenwang.simpleyelp

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

// serializedName is part of gson for gson annotation to work
// dont need to specify serialized name if it exactly matches the json parameter
data class YelpRestaurantResult(val url: String, val display_phone: String?,val photos: List<String>?,val is_closed: Boolean?, val display_address: String?)

data class YelpSearchResult (
    @SerializedName("total") val total: Int,
    @SerializedName("businesses") val restaurants: List<YelpRestaurants>
        )

@Parcelize
data class YelpRestaurants (
    val id: String,
    val name: String,
    val rating: Double,
    val price: String?,
    @SerializedName("review_count") val numReviews: Int,
    @SerializedName("distance") val distanceInMeters: Double,
    @SerializedName("image_url") val imageUrl : String,
    val location: YelpLocation,
    val categories: List<YelpCategory>,
        ) : Parcelable
{
    fun displayDistance() :String {
        val milesPerMeter = 0.000621371
        val distanceInMiles = "%.2f".format(milesPerMeter * distanceInMeters)
        return "${distanceInMiles} mi"
    }
}
@Parcelize data class YelpLocation (
    @SerializedName("address1") val address : String
        ):Parcelable
@Parcelize data class YelpCategory (
    val title: String
        ): Parcelable

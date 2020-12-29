package com.kathleenwang.simpleyelp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_restaurant.view.*

class RestaurantsAdapter(val context: Context, val restaurants: List<YelpRestaurants>) :
    RecyclerView.Adapter<RestaurantsAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
         return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_restaurant, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val restaurant = restaurants[position]
        holder.bind(restaurant)
    }

    override fun getItemCount() = restaurants.size
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(restaurant: YelpRestaurants) {
        itemView.tvName.text = restaurant.name
        }

    }
}

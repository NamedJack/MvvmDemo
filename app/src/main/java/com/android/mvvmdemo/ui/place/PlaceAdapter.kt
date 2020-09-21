package com.android.mvvmdemo.ui.place

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.android.mvvmdemo.R
import com.android.mvvmdemo.logic.model.Places
import com.android.mvvmdemo.ui.weather.WeatherActivity
import kotlinx.android.synthetic.main.activity_weather.*

class PlaceAdapter(private val fragment: PlaceFragment, private val placeList: List<Places>) :
    RecyclerView.Adapter<PlaceAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val placeName: TextView = view.findViewById(R.id.placeName)
        val placeAddress: TextView = view.findViewById(R.id.placeAddress)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.place_item, parent, false)
        val holder = ViewHolder(view)
        holder.itemView.setOnClickListener {
            val position = holder.adapterPosition
            val place = placeList[position]
            val activity = fragment.activity
            if (activity is WeatherActivity) {
                activity.apply {
                    drawerLayout.closeDrawers()
                    viewModel.locationLat = place.location.lat
                    viewModel.locationLng = place.location.lng
                    viewModel.placeName = place.name
                    refreshWeather()
                }
//                activity.drawerLayout.closeDrawers()
//                activity.viewModel.locationLat = place.location.lat
//                activity.viewModel.locationLng = place.location.lng
//                activity.viewModel.placeName = place.name
//                activity.refreshWeather()
            } else {
                val intent = Intent(parent.context, WeatherActivity::class.java).apply {
                    putExtra("location_lng", place.location.lng)
                    putExtra("location_lat", place.location.lat)
                    putExtra("place_name", place.name)
                }
                fragment.viewModel.savePlace(place)
                fragment.startActivity(intent)
            }
        }
        return holder
    }

    override fun getItemCount(): Int = placeList.size

    override fun onBindViewHolder(holder: PlaceAdapter.ViewHolder, position: Int) {
        val place = placeList[position]
        holder.placeName.text = place.name
        holder.placeAddress.text = place.address
    }


}
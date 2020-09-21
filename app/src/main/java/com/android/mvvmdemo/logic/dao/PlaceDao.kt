package com.android.mvvmdemo.logic.dao

import android.content.Context
import android.content.SharedPreferences
import android.graphics.ColorSpace
import androidx.core.content.edit
import com.android.mvvmdemo.WeatherApplication
import com.android.mvvmdemo.logic.model.Places
import com.google.gson.Gson


object PlaceDao {

    fun savePlace(places: Places) {
        sharedPreference().edit{
            putString("place", Gson().toJson(places))
        }
    }

    fun getSavedPlace():Places{
       return Gson().fromJson( sharedPreference().getString("place",""),Places::class.java)
    }

    fun isPlaceSaved() = sharedPreference().contains("place")


    private fun sharedPreference() =
        WeatherApplication.context.getSharedPreferences("sunny_weather", Context.MODE_PRIVATE)

}
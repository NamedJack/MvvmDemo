package com.android.mvvmdemo.logic.model

import com.google.gson.annotations.SerializedName


data class PlaceResponse(val status: String, val places: List<Places>)

data class Places(
    val name: String, val location: Location,
    @SerializedName("formatted_address") val address: String
)

data class Location(val lng: String, val lat: String)
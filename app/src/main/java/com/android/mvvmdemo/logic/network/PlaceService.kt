package com.android.mvvmdemo.logic.network

import com.android.mvvmdemo.WeatherApplication
import com.android.mvvmdemo.logic.model.PlaceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PlaceService {

    @GET("v2/place?token=${WeatherApplication.TOKEN}&lang=zh_CN")
    fun searchPlace(@Query("query") query:String) : Call<PlaceResponse>

}
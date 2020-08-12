package com.android.mvvmdemo.logic

import androidx.lifecycle.liveData
import com.android.mvvmdemo.logic.model.Places
import com.android.mvvmdemo.logic.network.WeatherNetWork
import kotlinx.coroutines.Dispatchers
import okhttp3.Dispatcher
import java.lang.Exception
import java.lang.RuntimeException

object Repository {


    fun searchPlaces(query: String) = liveData(Dispatchers.IO){
        val result = try {

            val placeResponse =  WeatherNetWork.searchPlace(query)
            if (placeResponse.status == "ok") {
                val places = placeResponse.places
                Result.success(places)
            }else{
                Result.failure(RuntimeException("response status is ${placeResponse.status}"))
            }


        } catch (e: Exception) {
            Result.failure<List<Places>>(e)
        }
        emit(result)
    }




}
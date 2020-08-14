package com.android.mvvmdemo.logic

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.android.mvvmdemo.logic.model.Places
import com.android.mvvmdemo.logic.model.Weather
import com.android.mvvmdemo.logic.network.WeatherNetWork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import okhttp3.Dispatcher
import java.lang.Exception
import java.lang.RuntimeException
import kotlin.coroutines.CoroutineContext

object Repository {


    fun searchPlaces(query: String) = fire(Dispatchers.IO){
        val placeResponse = WeatherNetWork.searchPlace(query)
            if (placeResponse.status == "ok") {
                val places = placeResponse.places
                Result.success(places)
            } else {
                Result.failure(RuntimeException("response status is ${placeResponse.status}"))
            }
    }
//        liveData(Dispatchers.IO) {
//        val result = try {
//            val placeResponse = WeatherNetWork.searchPlace(query)
//            if (placeResponse.status == "ok") {
//                val places = placeResponse.places
//                Result.success(places)
//            } else {
//                Result.failure(RuntimeException("response status is ${placeResponse.status}"))
//            }
//        } catch (e: Exception) {
//            Result.failure<List<Places>>(e)
//        }
//        emit(result)
//    }


    fun refreshWeather(lng: String, lat: String) = fire(Dispatchers.IO) {
        coroutineScope {
            val realtimeWeather = async {
                WeatherNetWork.getRealtimeWeather(lng, lat)
            }

            val dailyWeather = async {
                WeatherNetWork.getDailyWeather(lng, lat)
            }

            val realResponse = realtimeWeather.await()
            val dailyResponse = dailyWeather.await()

            if (realResponse.status == "ok" && dailyResponse.status == "ok") {
                val weather = Weather(dailyResponse.result.daily, realResponse.result.realtime)
                Result.success(weather)
            } else {
                Result.failure(
                    RuntimeException(
                        "realtime response status is ${realResponse.status}" +
                                "daily response status is ${dailyResponse.status}"
                    )
                )
            }
        }
    }
//        liveData(Dispatchers.IO) {
//        val result = try {
//            coroutineScope {
//                val realtimeWeather = async {
//                    WeatherNetWork.getRealtimeWeather(lng, lat)
//                }
//
//                val dailyWeather = async {
//                    WeatherNetWork.getDailyWeather(lng, lat)
//                }
//
//                val realResponse = realtimeWeather.await()
//                val dailyResponse = dailyWeather.await()
//
//                if (realResponse.status == "ok" && dailyResponse.status == "ok") {
//                    val weather = Weather(dailyResponse.result.daily, realResponse.result.realtime)
//                    Result.success(weather)
//                } else {
//                    Result.failure(
//                        RuntimeException(
//                            "realtime response status is ${realResponse.status}" +
//                                    "daily response status is ${dailyResponse.status}"
//                        )
//                    )
//                }
//            }
//
//        } catch (e: Exception) {
//            Result.failure<Weather>(RuntimeException(e))
//        }
//        emit(result)
//    }



    private fun <T> fire(context:CoroutineContext,block:suspend () -> Result<T>) =
        liveData<Result<T>>(context) {
            val result = try {
                block()
            } catch (e: Exception) {
                Result.failure<T>(RuntimeException(e))
            }
            emit(result)
        }


}
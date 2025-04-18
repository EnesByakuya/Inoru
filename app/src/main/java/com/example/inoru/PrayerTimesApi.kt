package com.example.inoru

import com.example.inoru.PrayerTimesResponse

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Path

interface PrayerTimesApi {
    @GET("v1/timingsByCity/{date}")
    fun getPrayerTimes(
        @Path("date") date: String,
        @Query("city") city: String,
        @Query("country") country: String, // e.g., "NL" for the Netherlands
        @Query("method") method: Int = 13 // Default for Diyanet, change this later
    ): Call<PrayerTimesResponse>
}
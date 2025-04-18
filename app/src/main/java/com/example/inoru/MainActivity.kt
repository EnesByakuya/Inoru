package com.example.inoru

import android.util.Log
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Get references to the TextViews defined in activity_main.xml
        val tvFajr: TextView = findViewById(R.id.tvFajr)
        val tvDhuhr: TextView = findViewById(R.id.tvDhuhr)
        val tvAsr: TextView = findViewById(R.id.tvAsr)
        val tvMaghrib: TextView = findViewById(R.id.tvMaghrib)
        val tvIsha: TextView = findViewById(R.id.tvIsha)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.aladhan.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(PrayerTimesApi::class.java)
        // Make the API call with hardcoded values for now, but change it later
        // TODO: Make the API call dynamic
        val call = api.getPrayerTimes("18-04-2025", "Rotterdam", "NL", 13)

        call.enqueue(object : Callback<PrayerTimesResponse> {
            override fun onResponse(
                call: Call<PrayerTimesResponse>,
                response: Response<PrayerTimesResponse>
            ) {
                if(response.isSuccessful) {
                    val timings = response.body()?.data?.timings
                    tvFajr.text = getString(R.string.prayer_time_fajr, timings?.Fajr)
                    tvDhuhr.text = getString(R.string.prayer_time_dhuhr, timings?.Dhuhr)
                    tvAsr.text = getString(R.string.prayer_time_asr, timings?.Asr)
                    tvMaghrib.text = getString(R.string.prayer_time_maghrib, timings?.Maghrib)
                    tvIsha.text = getString(R.string.prayer_time_isha, timings?.Isha)
                } else {
                    Log.e("API Error my friend", "Error: ${response.code()} - ${response.message()}")
                    Toast.makeText(this@MainActivity, "Failed to fetch prayer times", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<PrayerTimesResponse>, t: Throwable) {
                Log.e("API Error my friend", "Failure: ${t.message}")
                Toast.makeText(this@MainActivity, "Network error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}

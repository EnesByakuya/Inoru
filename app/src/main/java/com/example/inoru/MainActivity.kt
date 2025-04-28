package com.example.inoru

import android.util.Log
import android.os.Bundle
import android.widget.Spinner
import android.widget.ArrayAdapter
import android.widget.TextView
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.ComponentActivity
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

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

        val spinnerCountry: Spinner = findViewById(R.id.spinnerCountry)
        val spinnerCity: Spinner = findViewById(R.id.spinnerCity)

        val countryToCities = mapOf(
            "Netherlands" to R.array.netherlands_cities,
            "Germany" to R.array.germany_cities,
            "Belgium" to R.array.belgium_cities,
            "Turkiye" to R.array.turkiye_cities,
        )

        val countryAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.country_list,
            android.R.layout.simple_spinner_item
        )
        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCountry.adapter = countryAdapter

        spinnerCountry.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedCountry = parent.getItemAtPosition(position).toString()
                val cityArrayId = countryToCities[selectedCountry] ?: R.array.netherlands_cities

                val countryCode = when (selectedCountry) {
                    "Netherlands" -> "NL"
                    "Germany" -> "DE"
                    "Belgium" -> "BE"
                    "Turkiye" -> "TR"
                    else -> "NL" // Default fallback
                }

                val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
                val currentDate = LocalDateTime.now().format(formatter)
                Log.d("Current Date", currentDate)

                val cityAdapter = ArrayAdapter.createFromResource(
                    parent.context,
                    cityArrayId,
                    android.R.layout.simple_spinner_item
                ).also {
                    it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                }
                spinnerCity.adapter = cityAdapter

                // Make the API call when a country is selected
                spinnerCity.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        cityParent: AdapterView<*>,
                        cityView: View?,
                        cityPosition: Int,
                        cityId: Long
                    ) {
                        val selectedCity = cityParent.getItemAtPosition(cityPosition).toString()

                        val call = api.getPrayerTimes(currentDate, selectedCity, countryCode, 13)
                        call.enqueue(object : Callback<PrayerTimesResponse> {
                            override fun onResponse(
                                call: Call<PrayerTimesResponse>,
                                response: Response<PrayerTimesResponse>
                            ) {
                                if (response.isSuccessful) {
                                    val timings = response.body()?.data?.timings
                                    tvFajr.text = getString(R.string.prayer_time_fajr, timings?.Fajr)
                                    tvDhuhr.text = getString(R.string.prayer_time_dhuhr, timings?.Dhuhr)
                                    tvAsr.text = getString(R.string.prayer_time_asr, timings?.Asr)
                                    tvMaghrib.text = getString(R.string.prayer_time_maghrib, timings?.Maghrib)
                                    tvIsha.text = getString(R.string.prayer_time_isha, timings?.Isha)
                                } else {
                                    Log.e("API Error", "Error: ${response.code()} - ${response.message()}")
                                    Toast.makeText(this@MainActivity, "Failed to fetch prayer times", Toast.LENGTH_SHORT).show()
                                }
                            }

                            override fun onFailure(call: Call<PrayerTimesResponse>, t: Throwable) {
                                Log.e("API Error", "Failure: ${t.message}")
                                Toast.makeText(this@MainActivity, "Network error: ${t.message}", Toast.LENGTH_SHORT).show()
                            }
                        })
                    }

                    override fun onNothingSelected(parent: AdapterView<*>) {
                        // Optional: Handle no city selected
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Optional: Handle no country selected
            }
        }
    }
}

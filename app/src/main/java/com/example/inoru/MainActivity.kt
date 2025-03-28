package com.example.inoru

import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.inoru.ui.theme.InoruTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContent {
//            InoruTheme {
//                Scaffold( modifier = Modifier.fillMaxSize() ) { innerPadding ->
//                    Greeting(
//                        name = "Android",
//                        modifier = Modifier.padding(innerPadding)
//                    )
//                }
//            }
//        }
        setContentView(R.layout.activity_main)

        // Get references to the TextViews defined in activity_main.xml
//        val tvFajr: TextView = findViewById(R.id.tvFajr)
//        val tvDhuhr: TextView = findViewById(R.id.tvDhuhr)
//        val tvAsr: TextView = findViewById(R.id.tvAsr)
//        val tvMaghrib: TextView = findViewById(R.id.tvMaghrib)
//        val tvIsha: TextView = findViewById(R.id.tvIsha)

        // Hardcoded prayer times data
        val prayerTimes = mapOf(
            "Fajr" to "5:30 AM",
            "Dhuhr" to "1:30 PM",
            "Asr" to "4:30 PM",
            "Maghrib" to "7:30 PM",
            "Isha" to "9:30 PM"
        )

        // Set the TextView text using the prayer times data
//        tvFajr.text = "Fajr: ${prayerTimes["Fajr"]}"
//        tvDhuhr.text = "Dhuhr: ${prayerTimes["Dhuhr"]}"
//        tvAsr.text = "Asr: ${prayerTimes["Asr"]}"
//        tvMaghrib.text = "Maghrib: ${prayerTimes["Maghrib"]}"
//        tvIsha.text = "Isha: ${prayerTimes["Isha"]}"
    }
}

//@Composable
//fun Greeting(name: String, modifier: Modifier = Modifier) {
//    Text(
//        text = "Hello $name!",
//        modifier = modifier
//    )
//}
//
//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    InoruTheme {
//        Greeting("Android")
//    }
//}
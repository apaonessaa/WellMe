package com.example.wellme

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.wellme.storage.WellMeDatabase
import com.example.wellme.utils.TimeData
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    // Location
    private lateinit var locationManager: LocationManager
    private val locationPermissionCode = 2
    private var latitude: Double? = null
    private var longitude: Double? = null
    private val accuracy: Double = 25.0

    // DB reference
    private lateinit var storage: WellMeDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // DB reference
        storage = WellMeDatabase.getDatabase(this)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        // Caricare il primo fragment all'avvio
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HomeFragment())
                .commit()
        }

        // Gestire il click sui pulsanti della Bottom Navigation
        bottomNavigationView.setOnItemSelectedListener { item ->
            val selectedFragment = when (item.itemId) {
                R.id.nav_home -> HomeFragment()
                R.id.nav_stats -> StatsFragment()
                // R.id.nav_profile -> ProfileFragment()
                else -> null
            }
            selectedFragment?.let {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, it)
                    .commit()
            }
            true
        }

        // Controlla i permessi e ottieni la posizione
        checkLocationPermission()
    }

    private fun checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), locationPermissionCode
            )
        } else {
            getLocation()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == locationPermissionCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, getString(R.string.localization_access_granted), Toast.LENGTH_SHORT).show()
                getLocation()
            } else {
                Toast.makeText(this, getString(R.string.localization_access_denied), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getLocation() {
        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager

        // Usa GPS se disponibile, altrimenti Network
        val provider = when {
            locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) -> LocationManager.GPS_PROVIDER
            locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) -> LocationManager.NETWORK_PROVIDER
            else -> {
                Toast.makeText(this, getString(R.string.localization_disabled), Toast.LENGTH_SHORT).show()
                return
            }
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED
        ) {
            locationManager.getCurrentLocation(provider, null, mainExecutor) { loc: Location? ->
                if (loc != null) {
                    longitude = loc.longitude
                    latitude = loc.latitude
                    Log.d("MainActivity", "Coordinate ottenute: <$longitude, $latitude>")

                    // Trigger get by location
                    suggestActivity()
                }
            }
        }
    }

    private fun showActivityDialog(activities: List<String>) {
        if (activities.isEmpty()) return

        AlertDialog.Builder(this)
            .setTitle("Attività suggerite 📍")
            .setItems(activities.toTypedArray()) { _, which ->
                val selectedActivity = activities[which]
                val intent = Intent(this@MainActivity, FocusActivity::class.java).apply {
                    putExtra("activityType", selectedActivity)
                }
                startActivity(intent)
            }
            .setNegativeButton("Annulla", null)
            .show()
    }

    private fun suggestActivity() {
        lifecycleScope.launch(Dispatchers.IO) {
            val currentDay = TimeData.getDayOfTheWeek()
            if (latitude != null && longitude != null) {
                val activities =
                    storage.activityStatDao().getSuggestion(longitude!!, latitude!!, accuracy, currentDay)

                Log.d("MainActivity", "Attività trovate: $activities")

                if (activities.isNotEmpty()) {
                    val activityNames = activities.map { it }
                    withContext(Dispatchers.Main) {
                        showActivityDialog(activityNames)
                    }
                }
            }
        }
    }
}

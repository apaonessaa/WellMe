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
import com.example.wellme.fragments.HomeFragment
import com.example.wellme.fragments.ProfileFragment
import com.example.wellme.fragments.StatsFragment
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

        storage = WellMeDatabase.getDatabase(this)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        // Save the first fragment
        if (savedInstanceState == null) {
            // Set home fragment
            bottomNavigationView.selectedItemId = R.id.nav_home
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HomeFragment())
                .commit()
        }

        // Manage the fragments
        bottomNavigationView.menu.findItem(R.id.nav_home).isChecked = true
        bottomNavigationView.setOnItemSelectedListener { item ->
            val selectedFragment = when (item.itemId) {
                R.id.nav_home -> HomeFragment()
                R.id.nav_stats -> StatsFragment()
                R.id.nav_profile -> ProfileFragment()
                else -> null
            }
            selectedFragment?.let {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, it)
                    .commit()
            }
            true
        }

        Log.d("MainActivity","Location: <$longitude, $latitude>")

        // context aware to suggest activities to perform
        if (latitude==null && longitude==null)
            suggestActivities()
    }

    private fun suggestActivities() {
        // Check or Request location permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                locationPermissionCode
            )
        }
        getLocation()
    }

    // Location permission granted or access denied
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == locationPermissionCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast
                    .makeText(
                        this,
                        getString(R.string.localization_access_granted),
                        Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast
                    .makeText(
                        this,
                        getString(R.string.localization_access_denied),
                        Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    // Get location information
    private fun getLocation() {
        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager

        // GPS or Network
        val provider = when {
            locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                -> LocationManager.GPS_PROVIDER
            locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
                -> LocationManager.NETWORK_PROVIDER
            else -> {
                Toast
                    .makeText(
                        this,
                        getString(R.string.localization_disabled),
                        Toast.LENGTH_SHORT)
                    .show()
                return
            }
        }

        if (ContextCompat
                .checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED
        ) {
            locationManager
                .getCurrentLocation(provider, null, mainExecutor) { loc: Location? ->
                    loc?.let {
                        longitude = loc.longitude
                        latitude = loc.latitude
                        Log.d("MainActivity", "Current Location: <$longitude, $latitude>")
                        activityDetector()
                    }
            }
        }
    }

    // Choose suggestions by alert dialog
    private fun showActivityDialog(activities: List<String>) {
        if (activities.isEmpty()) return
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.context_aware_suggested_activities))
            .setItems(activities.toTypedArray()) { _, which ->
                // Define listener
                val intent =
                    Intent(this@MainActivity, FocusActivity::class.java).apply {
                        putExtra("activityType", activities[which])
                    }
                startActivity(intent)
            }
            .setNegativeButton(getString(R.string.cancel_button), null)
            .show()
    }

    // Detects the presence of some activity to suggest
    private fun activityDetector() {
        lifecycleScope.launch(Dispatchers.IO) {
            val weekday = TimeData.getDayOfTheWeek()
            if (latitude != null && longitude != null) {
                // Log.d(
                //    "MainActivity",
                //    "Start Activity Detection by Location & Weekday: <$longitude, $latitude>, $weekday")
                storage.activityStatDao()
                    .getSuggestion(longitude!!, latitude!!, accuracy, weekday)
                    .apply {
                        withContext(Dispatchers.Main) {
                            showActivityDialog(this@apply)
                        }
                    }
            } //else {
                // Log.d(
                //    "Error@MainActivity",
                //    "Start Activity Detection by wrong Location & Weekday: <$longitude, $latitude>, $weekday")
            //}
        }
    }
}

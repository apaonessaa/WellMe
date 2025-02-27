package com.example.wellme

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.wellme.fragments.ControlsFragment
import com.example.wellme.fragments.DataFragment

class FocusActivity : AppCompatActivity() {

    private val stepsPermissionCode = 1
    private val locationPermissionCode = 2
    private lateinit var dataFragment: DataFragment
    private lateinit var locationManager: LocationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_focus)

        val activityType = intent.getStringExtra("activityType") ?: "Running 🏃🏻‍♂️"

        if (savedInstanceState == null) {
            dataFragment = DataFragment().apply {
                arguments = Bundle().apply {
                    putString("activityType", activityType)
                }
            }

            if (activityType == "Running 🏃🏻‍♂️" || activityType == "Walking 🚶🏻‍♂️") {
                val stepsPermission = ContextCompat.checkSelfPermission(
                    this, Manifest.permission.ACTIVITY_RECOGNITION
                );
                if (stepsPermission != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(
                        this, arrayOf(Manifest.permission.ACTIVITY_RECOGNITION), stepsPermissionCode
                    )
                } else {
                    dataFragment.trackSteps(true)
                    showFragments()
                }
            } else {
                showFragments()
            }
        }
    }

    private fun showFragments() {
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), locationPermissionCode
            )
        } else {
            getLocation()
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_data, dataFragment)
            .replace(R.id.fragment_controls, ControlsFragment()).commit()
    }

    private fun getLocation() {
        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        if (!locationManager.isProviderEnabled(LocationManager.FUSED_PROVIDER)) {
            Toast.makeText(this, getString(R.string.localization_disabled), Toast.LENGTH_SHORT)
                .show()
            return
        }
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            locationManager.getCurrentLocation(
                LocationManager.FUSED_PROVIDER, null, mainExecutor
            ) { loc: Location? ->
                if (loc != null) {
                    dataFragment.setLatitude(loc.latitude)
                    dataFragment.setLongitude(loc.longitude)
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == stepsPermissionCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, getString(R.string.steps_access_granted), Toast.LENGTH_SHORT).show()
                dataFragment.trackSteps(true)
                showFragments()
            } else {
                Toast.makeText(this, getString(R.string.steps_access_denied), Toast.LENGTH_SHORT).show()
                dataFragment.trackSteps(false)
                showFragments()
            }
        }
        if (requestCode == locationPermissionCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, getString(R.string.localization_access_granted), Toast.LENGTH_SHORT).show()
                getLocation()
            } else {
                Toast.makeText(this, getString(R.string.localization_access_denied), Toast.LENGTH_SHORT).show()
            }
        }
    }
}
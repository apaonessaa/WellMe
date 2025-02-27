package com.example.wellme.fragments

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.lifecycle.lifecycleScope
import com.example.wellme.R
import com.example.wellme.storage.WellMeDatabase
import com.example.wellme.storage.entities.ActivityStat
import com.example.wellme.utils.TimeData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DataFragment : Fragment(), SensorEventListener {

    private lateinit var textTime: TextView
    private lateinit var textDistance: TextView
    private lateinit var textSteps: TextView
    private lateinit var cardDistance: CardView
    private lateinit var cardSteps: CardView
    private var activityType: String? = null
    private var latitude: Double? = null
    private var longitude: Double? = null
    private var elapsedTime = 0
    private var stepsTaken = 0
    private var isRunning = false
    private var permStepsGranted = false
    private lateinit var sensorManager: SensorManager
    private var stepSensor: Sensor? = null
    private lateinit var storage: WellMeDatabase

    private val handler = Handler(Looper.getMainLooper())
    private val updateRunnable = object: Runnable {
        override fun run() {
            if(isRunning) {
                elapsedTime++
                updateUI()
                handler.postDelayed(this, 1000)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_data, container, false)
        initializeViews(view)
        activityType = arguments?.getString("activityType")
        setupUI()
        if(trackingSteps()) {
            setupSensor()
        }
        storage = WellMeDatabase.getDatabase(requireContext())
        return view
    }

    override fun onResume() {
        super.onResume()
        if(trackingSteps()) {
            sensorManager.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onPause() {
        super.onPause()
        if(trackingSteps()) {
            sensorManager.unregisterListener(this)
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if(trackingSteps()) {
            if (event?.sensor?.type == Sensor.TYPE_STEP_DETECTOR && isRunning) {
                stepsTaken++
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    private fun initializeViews(view: View) {
        cardDistance = view.findViewById(R.id.card_distance)
        cardSteps = view.findViewById(R.id.card_steps)
        textTime = view.findViewById(R.id.text_time)
        textDistance = view.findViewById(R.id.text_distance)
        textSteps = view.findViewById(R.id.text_steps)
        resetUI()
    }

    private fun resetUI() {
        textTime.text = getString(R.string.time_string, 0)
        textDistance.text = getString(R.string.distance_string, 0.0)
        textSteps.text = getString(R.string.steps_string, 0)
    }

    private fun setupUI() {
        when(activityType) {
            "Running 🏃🏻‍♂️", "Walking 🚶🏻‍♂️" -> {
                cardDistance.visibility = View.VISIBLE
                cardSteps.visibility = View.VISIBLE
            }
            else -> {
                cardDistance.visibility = View.GONE
                cardSteps.visibility = View.GONE
            }
        }
    }

    private fun setupSensor() {
        sensorManager =
            requireContext().getSystemService(Context.SENSOR_SERVICE) as SensorManager
        stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR)
        if (stepSensor == null) {
            Toast.makeText(
                requireContext(),
                getString(R.string.sensor_not_available),
                Toast.LENGTH_SHORT
            ).show()
            permStepsGranted = false
        }
    }

    fun startTracking() {
        isRunning = true
        handler.post(updateRunnable)
    }

    fun pauseTracking() {
        isRunning = false
    }

    fun stopTracking() {
        isRunning = false
        val tempElapsedTime = elapsedTime
        val tempStepsTaken = stepsTaken
        lifecycleScope.launch(Dispatchers.IO) {
            if(trackingSteps()) {
                storage.activityStatDao().insertAll(
                    ActivityStat(
                        type = activityType!!,
                        date = TimeData.getCurrentDate(),
                        duration = tempElapsedTime,
                        distance = (tempStepsTaken * 0.75) / 1000,
                        steps = tempStepsTaken,
                        day = TimeData.getDayOfTheWeek(),
                        latitude = latitude,
                        longitude = longitude
                    )
                )
            } else {
                storage.activityStatDao().insertAll(
                    ActivityStat(
                        type = activityType!!,
                        date = TimeData.getCurrentDate(),
                        duration = tempElapsedTime,
                        distance = null,
                        steps = null,
                        day = TimeData.getDayOfTheWeek(),
                        latitude = latitude,
                        longitude = longitude
                    )
                )
            }
            val allActivities = storage.activityStatDao().getAll()
            Log.d("DataFragment", "Saved Activity Entries: $allActivities")
        }
        elapsedTime = 0
        stepsTaken = 0
        updateUI()
    }

    fun trackSteps(enable: Boolean) {
        permStepsGranted = enable
    }

    fun setLatitude(lat: Double) {
        latitude = lat
    }

    fun setLongitude(long: Double) {
        longitude = long
    }

    fun isRunning(): Boolean {
        return isRunning
    }

    private fun updateUI() {
        textTime.text = getString(R.string.time_string, elapsedTime)
        if(trackingSteps()) {
            textDistance.text = getString(R.string.distance_string, (stepsTaken * 0.75) / 1000)
            textSteps.text = getString(R.string.steps_string, stepsTaken)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacks(updateRunnable)
    }

    private fun trackingSteps(): Boolean {
        return (activityType == "Running 🏃🏻‍♂️" || activityType == "Walking 🚶🏻‍♂️") && permStepsGranted
    }
}
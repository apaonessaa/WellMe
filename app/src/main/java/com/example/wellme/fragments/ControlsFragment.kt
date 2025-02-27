package com.example.wellme.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.wellme.R

class ControlsFragment : Fragment() {

    private var dataFragment: DataFragment? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_controls, container, false)
        val buttonStartPause = view.findViewById<Button>(R.id.buttonStartPause)
        val buttonStop = view.findViewById<Button>(R.id.buttonStop)
        dataFragment = parentFragmentManager.findFragmentById(R.id.fragment_data) as? DataFragment
        buttonStartPause.setOnClickListener {
            if(dataFragment?.isRunning() == true) {
                buttonStartPause.text = getString(R.string.start_button)
                dataFragment?.pauseTracking()
            } else {
                buttonStartPause.text = getString(R.string.pause_button)
                dataFragment?.startTracking()
            }
        }

        buttonStop.setOnClickListener {
            dataFragment?.stopTracking()
            buttonStartPause.text = getString(R.string.start_button)
        }

        return view
    }

}
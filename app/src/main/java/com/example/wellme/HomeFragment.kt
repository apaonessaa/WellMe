package com.example.wellme

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import com.example.wellme.utils.ActivityData

class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Start Activity and Note buttons
        val btnStartActivity = view.findViewById<Button>(R.id.startActivity_button)
        btnStartActivity.setOnClickListener {
            showActivityDialog()
        }
        val btnStartNote = view.findViewById<Button>(R.id.startNote_button)
        btnStartNote.setOnClickListener {
            val intent = Intent(activity, MoodActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showActivityDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.choose_activity_menu_title)
            .setItems(ActivityData.types) { _, which ->
                val selectedActivity = ActivityData.types[which]
                val intent = Intent(activity, FocusActivity::class.java)
                intent.putExtra("activityType", selectedActivity)
                startActivity(intent)
            }
            .setNegativeButton(getString(R.string.cancel_button), null)
            .show()
    }
}
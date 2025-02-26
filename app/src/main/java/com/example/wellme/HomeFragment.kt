package com.example.wellme

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AlertDialog

class HomeFragment : Fragment() {

    private val activities =
        arrayOf("Walking", "Running", "Weightlifting", "Yoga", "Basketball", "Soccer",
            "Tennis", "Table Tennis", "Boxing", "Golf")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Infliamo il layout del Fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Gestiamo i pulsanti
        val btnStart = view.findViewById<Button>(R.id.startActivity_button)
        btnStart.setOnClickListener {
            showActivityDialog()
        }

        // Sezione: Stato d'animo
        val btnAnnota = view.findViewById<Button>(R.id.btnAnnota)
        btnAnnota.setOnClickListener {
            val intent = Intent(activity, MoodActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showActivityDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.choose_activity_menu_title)
            .setItems(activities) { _, which ->
                val selectedActivity = activities[which]
                val intent = Intent(activity, FocusActivity::class.java)
                intent.putExtra("activityType", selectedActivity)
                startActivity(intent)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}
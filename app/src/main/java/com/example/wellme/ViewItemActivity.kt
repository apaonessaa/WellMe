package com.example.wellme

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ViewItemActivity : AppCompatActivity() {
    private val tag = "ViewItemActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(tag,"onCreate")
        setContentView(R.layout.activity_view_item)

        val tw = findViewById<TextView>(R.id.textView4)
        val intent = getIntent()
        val dato = intent.getStringExtra("Dato")
        if(dato == "Esercizio") {
            val id = intent.getIntExtra("ID",0)
            val type = intent.getStringExtra("Type")
            val date = intent.getStringExtra("Date")
            val duration = intent.getIntExtra("Duration",0)
            val distance = intent.getDoubleExtra("Distance", 0.0)
            val steps = intent.getIntExtra("Steps",0)
            val day = intent.getIntExtra("Day",0)
            val latitude = intent.getDoubleExtra("Latitude",0.0)
            val longitude = intent.getDoubleExtra("Longitude",0.0)
            tw.text = "Activity info:\n\n " +
                    "ID Activity: $id \n\n " +
                    "Activity Type: $type \n\n " +
                    "Activity Date: $date \n\n " +
                    "Activity Duration: $duration \n\n " +
                    "Activity Distance: $distance \n\n " +
                    "Activity Steps: $steps \n\n " +
                    "Activity Day: $day \n\n " +
                    "Latitude: $latitude \n\n " +
                    "Longitude: $longitude"
        }
        else {
            val id = intent.getIntExtra("ID",0)
            val date = intent.getStringExtra("Date")
            val hour = intent.getStringExtra("Hour")
            val mood = intent.getStringExtra("Mood")
            val detail = intent.getStringExtra("Detail")
            val cause = intent.getStringExtra("Cause")
            val note = intent.getStringExtra("Note")
            tw.text = "Emotion info:\n\n " +
                    "ID Mood: $id \n\n " +
                    "Mood: $mood \n\n " +
                    "Mood Date: $date \n\n " +
                    "Mood Hour: $hour \n\n " +
                    "Mood Detail: $detail \n\n "+
                    "Mood Cause: $cause \n\n "
            "Mood Note: $note"
        }
    }
}
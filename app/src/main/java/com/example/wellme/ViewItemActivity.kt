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
        //Visualizzazione nella textView delle info sul singolo elemento
        val tw = findViewById<TextView>(R.id.singleItem)
        //Mittente intent è ActivityAdapter o MoodAdapter
        val intent = getIntent()
        val dato = intent.getStringExtra("Dato")
        if(dato == "Esercizio") {
            //val id = intent.getIntExtra("ID",0)
            val type = intent.getStringExtra("Type")
            val date = intent.getStringExtra("Date")
            val duration = intent.getIntExtra("Duration",0)
            val distance = intent.getDoubleExtra("Distance", 0.0)
            val steps = intent.getIntExtra("Steps",0)
            val day = intent.getIntExtra("Day",0)
            val latitude = intent.getDoubleExtra("Latitude",-0.0)
            val longitude = intent.getDoubleExtra("Longitude",-0.0)
            var lat = latitude.toString()
            var lon = longitude.toString()
            if(latitude == -0.0 && longitude == -0.0) {
                lat = "-"
                lon = "-"
            }
            tw.text = "Activity info: \n\n" +
                    " Type: $type \n\n" +
                    " Date: $date \n\n" +
                    " Duration: $duration \n\n" +
                    " Distance: $distance \n\n" +
                    " Steps: $steps \n\n" +
                    " Day: $day \n\n" +
                    " Latitude: $lat \n\n" +
                    " Longitude: $lon"
        }
        else {
            //val id = intent.getIntExtra("ID",0)
            val date = intent.getStringExtra("Date")
            val hour = intent.getStringExtra("Hour")
            val h = hour ?: "--"
            val mood = intent.getStringExtra("Mood")
            val detail = intent.getStringExtra("Detail")
            val det = detail ?: "No details"
            val cause = intent.getStringExtra("Cause")
            val c = cause ?: "Undefined"
            val note = intent.getStringExtra("Note")
            var n : String? = "No notes"
            if(note != "") {
                n = note
            }
            tw.text = "Emotion info: \n\n" +
                    " Mood: $mood \n\n" +
                    " Date: $date \n\n" +
                    " Hour: $h \n\n" +
                    " Detail: $det \n\n"+
                    " Cause: $c \n\n"+
                    " Note: $n"
        }
    }
}
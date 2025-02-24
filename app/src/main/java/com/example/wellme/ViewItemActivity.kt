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
            val duration = intent.getIntExtra("Duration",0)
            val latitude = intent.getDoubleExtra("Latitude",0.0)
            val longitude = intent.getDoubleExtra("Longitude",0.0)
            tw.text = "Activity info:\n\n " +
                    "$id \n\n " +
                    "$type \n\n " +
                    "$duration \n\n " +
                    "$latitude \n\n " +
                    "$longitude"
        }
        else {
            val id = intent.getIntExtra("ID",0)
            val date = intent.getStringExtra("Date")
            val hour = intent.getStringExtra("Hour")
            val mood = intent.getStringExtra("Mood")
            val note = intent.getStringExtra("Note")
            tw.text = "Emotion info:\n\n " +
                    "$id \n\n " +
                    "$mood \n\n " +
                    "$date \n\n " +
                    "$hour \n\n " +
                    "$note"
        }
    }
}
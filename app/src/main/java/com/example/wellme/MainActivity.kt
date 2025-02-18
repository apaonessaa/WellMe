package com.example.wellme

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnStart = findViewById<Button>(R.id.startActivity_button)

        btnStart.setOnClickListener {
            val intent = Intent(this, FocusActivity::class.java)
            intent.putExtra("activityType", "walking")
            startActivity(intent)
        }

        // Sezione: Stato d'animo
        // Intent to MoodActivity
        val btnAnnota = findViewById<Button>(R.id.btnAnnota)
        btnAnnota.setOnClickListener {
            val intent = Intent(this, MoodActivity::class.java)
            startActivity(intent)
        }
    }
}
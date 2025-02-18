package com.example.wellme;

import android.os.Bundle
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MoodActivity : AppCompatActivity() {
    enum class Moment { UNKNOWN, NOW, TODAY }

    private var moodMoment = Moment.UNKNOWN

    private val moodStates = arrayOf(
        "Molto Spiacevole",
        "Spiacevole",
        "Leggermente Spiacevole",
        "Neutro",
        "Leggermente Piacevole",
        "Piacevole",
        "Molto Piacevole"
    )

    private val emotionsMap = mapOf(
        "Molto Spiacevole" to listOf("Tristezza", "Disperazione", "Ansia"),
        "Spiacevole" to listOf("Frustrazione", "Delusione", "Sconforto"),
        "Leggermente Spiacevole" to listOf("Noia", "Sottotono", "Scetticismo"),
        "Neutro" to listOf("Soddisfazione", "Calma", "Serenità", "Indifferenza", "Spossatezza"),
        "Leggermente Piacevole" to listOf("Interesse", "Curiosità", "Ottimismo"),
        "Piacevole" to listOf("Gioia", "Entusiasmo", "Appagamento"),
        "Molto Piacevole" to listOf("Euforia", "Felicità", "Amore", "Gratitudine")
    )

    private fun getCurrentTime(): String {
        return SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
    }

    private fun getCurrentDate(): String {
        return SimpleDateFormat("dd/MM", Locale.getDefault()).format(Date())
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mood)

        val moodToggleGroup = findViewById<MaterialButtonToggleGroup>(R.id.moodToggleGroup)
        val moodTimestamp = findViewById<TextView>(R.id.moodTimestamp)
        val moodSeekBar = findViewById<SeekBar>(R.id.moodSeekBar)
        val moodLabel = findViewById<TextView>(R.id.moodLabel)
        val emotionChipGroup = findViewById<ChipGroup>(R.id.emotionChipGroup)
        val save = findViewById<Button>(R.id.btnAnnota)

        // Selector & Timestamp
        moodToggleGroup.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    R.id.moodBtn1 -> {
                        moodMoment = Moment.NOW
                        moodTimestamp.text = "Ora corrente: ${getCurrentTime()}"
                    }
                    R.id.moodBtn2 -> {
                        moodMoment = Moment.TODAY
                        moodTimestamp.text = "Data odierna: ${getCurrentDate()}"
                    }
                }
            }
        }

        // SeekBar & Label
        moodSeekBar.progress = 3
        moodLabel.text = moodStates[3]
        moodSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                val moodSelected = moodStates[progress]
                moodLabel.text = moodSelected
                updateEmotionChips(emotionChipGroup, moodSelected)
            }
            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })

        // ChipGroup
        updateEmotionChips(emotionChipGroup, moodStates[3])

        // Button Save
        save.setOnClickListener {
            println("Mood:"+ moodLabel.text)
        }
    }

    private fun updateEmotionChips(chipGroup: ChipGroup, mood: String) {
        chipGroup.removeAllViews()
        val emotions = emotionsMap[mood] ?: return
        for (emotion in emotions) {
            val chip = Chip(this).apply {
                text = emotion
                isCheckable = true
            }
            chipGroup.addView(chip)
        }
    }
}

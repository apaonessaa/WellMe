package com.example.wellme;

import android.app.Application
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.wellme.storage.WellMeDatabase
import com.example.wellme.storage.dao.MoodStatDao
import com.example.wellme.storage.entities.MoodStat
import com.google.android.material.button.MaterialButton
import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

enum class Moment { UNKNOWN, NOW, TODAY }

class MoodActivity : AppCompatActivity() {


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
        "Leggermente Piacevole" to
                listOf("Stupore", "Entusiasmo", "Sorpresa", "Passione", "Felicità", "Gioia",
                "Coraggio", "Orgoglio", "Fiducia in sé", "Speranza", "Divertimento", "Appagamento",
                    "Sollievo", "Gratitudine", "Soddisfazione", "Calma", "Serenità"
                ),
        "Piacevole" to listOf("Gioia", "Entusiasmo", "Appagamento"),
        "Molto Piacevole" to listOf("Euforia", "Felicità", "Amore", "Gratitudine")
    )

    private val contexts = arrayOf(
        "Salute", "Attività Fisica", "Cura personale", "Hobby", "Identità", "Spriritualità",
        "Vita sociale", "Famiglia", "Amicizie", "Patner", "Vita sentimentale", "Impegni",
        "Lavoro", "Istruzione", "Viaggi", "Meteo", "Eventi di attualità", "Denaro"
    )

    // DB
    private lateinit var storage: WellMeDatabase

    private fun getCurrentTime(): String {
        return SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
    }

    private fun getCurrentDate(): String {
        return SimpleDateFormat("dd/MM", Locale.getDefault()).format(Date())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mood)

        storage = WellMeDatabase.getDatabase(this)

        val moodToggleGroup = findViewById<MaterialButtonToggleGroup>(R.id.moodToggleGroup)
        val moodBtn1 = findViewById<MaterialButton>(R.id.moodBtn1)
        val moodBtn2 = findViewById<MaterialButton>(R.id.moodBtn2)
        val moodSeekBar = findViewById<SeekBar>(R.id.moodSeekBar)
        val moodLabel = findViewById<TextView>(R.id.moodLabel)
        val emotionChipGroup = findViewById<ChipGroup>(R.id.emotionChipGroup)
        val contextChipGroup = findViewById<ChipGroup>(R.id.contextChipGroup)
        val save = findViewById<Button>(R.id.btnAnnota)

        // Selector & Timestamp
        moodToggleGroup.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    R.id.moodBtn1 -> {
                        moodBtn1.text=
                            StringBuilder()
                                .append("Descrivi questo momento\n")
                                .append(getCurrentTime()).toString()
                        moodMoment = Moment.NOW
                    }
                    R.id.moodBtn2 -> {
                        moodBtn2.text=
                            StringBuilder()
                                .append("Descrivi questo giorno\n")
                                .append(getCurrentDate()).toString()
                        moodMoment = Moment.TODAY
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

        // ChipGroup
        updateContextChips(contextChipGroup)

        // Button Save
        save.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                storage.moodStatDao().insertAll(
                    MoodStat(
                        date = getCurrentDate(),
                        hour = getCurrentTime(),
                        mood = "ciao",
                        detail = "detail",
                        cause = "cause",
                        note = "note"
                    )
                )

                val allMoods = storage.moodStatDao().getAll()
                Log.d("MoodActivity", "Saved Mood Entries: $allMoods")
            }
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

    private fun updateContextChips(chipGroup: ChipGroup) {
        chipGroup.removeAllViews()
        for (context in contexts) {
            val chip = Chip(this).apply {
                text = context
                isCheckable = true
            }
            chipGroup.addView(chip)
        }
    }

}

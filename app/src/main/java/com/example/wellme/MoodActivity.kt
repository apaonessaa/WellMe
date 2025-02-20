package com.example.wellme;

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.wellme.storage.WellMeDatabase
import com.example.wellme.storage.entities.MoodStat
import com.google.android.material.button.MaterialButton
import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import com.example.wellme.utils.MoodData
import com.example.wellme.utils.TimeData

enum class Moment { UNKNOWN, NOW, TODAY }

class MoodActivity : AppCompatActivity() {

    // Options
    private val states = MoodData.states
    private val details = MoodData.details
    private val causes = MoodData.causes

    //
    private var moodMoment = Moment.UNKNOWN

    // Selection
    private var stateSelected: String
    private var detailsSelected: MutableList<String>
    private var causesSelected: MutableList<String>

    // DB reference
    private lateinit var storage: WellMeDatabase

    init {
        stateSelected = states[MoodData.DEFAULT_STATE]
        detailsSelected = mutableListOf()
        causesSelected = mutableListOf()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mood)

        storage = WellMeDatabase.getDatabase(this)

        val timeGroup = findViewById<MaterialButtonToggleGroup>(R.id.moodToggleGroup)
        val now = findViewById<MaterialButton>(R.id.moodBtn1)
        val today = findViewById<MaterialButton>(R.id.moodBtn2)
        val seekBar = findViewById<SeekBar>(R.id.moodSeekBar)
        val seekLabel = findViewById<TextView>(R.id.moodLabel)
        val detailsChipGroup = findViewById<ChipGroup>(R.id.emotionChipGroup)
        val causesChipGroup = findViewById<ChipGroup>(R.id.contextChipGroup)
        val save = findViewById<Button>(R.id.btnAnnota)

        // Time selection
        timeGroup.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    // TODO UI view
                    R.id.moodBtn1 -> {
                        now.text=
                            StringBuilder()
                                .append("Descrivi questo momento\n")
                                .append(TimeData.getCurrentTime()).toString()
                        moodMoment = Moment.NOW
                    }
                    R.id.moodBtn2 -> {
                        today.text=
                            StringBuilder()
                                .append("Descrivi questo giorno\n")
                                .append(TimeData.getCurrentDate()).toString()
                        moodMoment = Moment.TODAY
                    }
                }
            }
        }

        // SeekBar & Label
        seekBar.progress = MoodData.DEFAULT_STATE
        seekLabel.text = stateSelected
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                seekLabel.text = states[progress]
                updateState(states[progress])
                updateDetails(detailsChipGroup)
            }
            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })

        // ChipGroup
        updateDetails(detailsChipGroup)

        // ChipGroup
        updateCauses(causesChipGroup)

        // Button Save
        save.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                storage.moodStatDao().insertAll(
                    MoodStat(
                        date = TimeData.getCurrentDate(),
                        hour = TimeData.getCurrentTime(),
                        mood = stateSelected,
                        detail = detailsSelected.toString(),
                        cause = causesSelected.toString(),
                        note = "note"
                    )
                )

                val allMoods = storage.moodStatDao().getAll()
                Log.d("MoodActivity", "Saved Mood Entries: $allMoods")
            }
        }
    }

    private fun updateState(state: String) {
        stateSelected = state
        detailsSelected.clear()
        causesSelected.clear()
    }

    private fun updateDetails(chipGroup: ChipGroup) {
        chipGroup.removeAllViews()
        details[stateSelected]!!.forEach { detail ->
            // Update Chip Group
            chipGroup.addView(
                Chip(this).apply {
                    text = detail
                    isCheckable = true
                })
            // Update Selection
            detailsSelected.add(detail)
        }
    }

    private fun updateCauses(chipGroup: ChipGroup) {
        chipGroup.removeAllViews()
        causes.forEach { cause ->
            // Update Chip Group
            chipGroup.addView(
                Chip(this).apply {
                text = cause
                isCheckable = true
            })
        }
    }
}

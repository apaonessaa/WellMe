package com.example.wellme;

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
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
import kotlinx.coroutines.withContext

class MoodActivity : AppCompatActivity() {

    // Options
    private val states = MoodData.states
    private val details = MoodData.details
    private val causes = MoodData.causes

    // Selection
    private var stateSelected: String
    private var detailsSelected: MutableList<String>
    private var causesSelected: MutableList<String>
    private var dateSelected: String?
    private var hourSelected: String?

    // DB reference
    private lateinit var storage: WellMeDatabase

    init {
        stateSelected = states[MoodData.DEFAULT_STATE]
        detailsSelected = mutableListOf()
        causesSelected = mutableListOf()
        dateSelected = null
        hourSelected = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mood)

        storage = WellMeDatabase.getDatabase(this)

        val timeGroup = findViewById<MaterialButtonToggleGroup>(R.id.groupTime)
        val today = findViewById<MaterialButton>(R.id.todayTime)
        val now = findViewById<MaterialButton>(R.id.nowTime)
        val seekBar = findViewById<SeekBar>(R.id.seekBar)
        val seekLabel = findViewById<TextView>(R.id.seekLabel)
        val detailsChipGroup = findViewById<ChipGroup>(R.id.detailGroup)
        val causesChipGroup = findViewById<ChipGroup>(R.id.causesGroup)
        val save = findViewById<Button>(R.id.saveBtn)

        // Time selection
        today.text=
            StringBuilder()
                .append("Describe today.")
                .toString()

        now.text=
            StringBuilder()
                .append("Describe this moment.")
                .toString()

        timeGroup.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    R.id.todayTime -> {
                        dateSelected = TimeData.getCurrentDate()
                        hourSelected = null
                        today.text=
                            StringBuilder()
                                .append("Describe today.")
                                .toString()
                    }
                    R.id.nowTime -> {
                        dateSelected = TimeData.getCurrentDate()
                        hourSelected = TimeData.getCurrentTime()
                        now.text=
                            StringBuilder()
                                .append("Describe this moment.\n")
                                .append(hourSelected).toString()
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
                details[stateSelected]?.let { viewChips(it, detailsSelected, detailsChipGroup) }
            }
            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })

        // ChipGroup
        details[stateSelected]?.let { viewChips(it, detailsSelected, detailsChipGroup) }
        viewChips(causes, causesSelected, causesChipGroup)

        // Button Save
        save.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                if (dateSelected!=null) {
                    storage.moodStatDao().insertAll(
                        MoodStat(
                            date = dateSelected!!,
                            hour = hourSelected,
                            mood = stateSelected,
                            detail = detailsSelected.toString(),
                            cause = causesSelected.toString(),
                            note = "note"
                        )
                    )
                    val allMoods = storage.moodStatDao().getAll()
                    Log.d("MoodActivity", "Saved Mood Entries: $allMoods")
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@MoodActivity,
                            "È necessario scegliere una data",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    Log.d("Error@MoodActivity", "Saving Mood Entries, but the date is $dateSelected")
                }
            }
        }
    }

    private fun updateState(state: String) {
        stateSelected = state
        detailsSelected.clear()
        causesSelected.clear()
    }

    private fun viewChips(target: List<String>, selected: MutableList<String>, chipGroup: ChipGroup) {
        chipGroup.removeAllViews()
        target.forEach { detail ->
            // Define Chip
            val chip = Chip(this).apply {
                text = detail
                isCheckable = true
                isChecked = false
                chipIcon = null
                isChipIconVisible = false
                isCloseIconVisible = false
                checkedIcon = null
            }
            // Default Style
            chip.setChipBackgroundColorResource(android.R.color.darker_gray)
            chip.setChipStrokeColorResource(android.R.color.darker_gray)
            chip.chipStrokeWidth = 4f
            // On Change, update selected list and style
            chip.setOnCheckedChangeListener { _, isChecked ->
                when (isChecked) {
                    true -> {
                        selected.add(detail)
                        chip.setChipBackgroundColorResource(android.R.color.white)
                        chip.setChipStrokeColorResource(android.R.color.holo_orange_dark)
                    }

                    else -> {
                        selected.remove(detail)
                        chip.setChipBackgroundColorResource(android.R.color.darker_gray)
                        chip.setChipStrokeColorResource(android.R.color.darker_gray)
                    }
                }
            }
            // Append Chip
            chipGroup.addView(chip)
        }
    }
}

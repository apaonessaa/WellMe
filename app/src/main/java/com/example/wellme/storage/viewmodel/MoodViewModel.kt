package com.example.wellme.storage.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.wellme.storage.WellMeDatabase
import com.example.wellme.storage.entities.MoodStat
import kotlinx.coroutines.launch

class MoodViewModel(application: Application) : AndroidViewModel(application) {
    private val moodDao = WellMeDatabase.getDatabase(application).moodStatDao()
    val allMoods: LiveData<List<MoodStat>> = moodDao.getAllEmotions()
    val current: LiveData<MoodStat> = moodDao.getEmotionById(0)

    fun insert(moodStat: MoodStat) {
        viewModelScope.launch {
            moodDao.insertAll(moodStat)
        }
    }
}
package com.example.wellme.storage.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.wellme.storage.WellMeDatabase
import com.example.wellme.storage.entities.ActivityStat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ActivityViewModel(application: Application) : AndroidViewModel(application) {
    private val activityDao = WellMeDatabase.getDatabase(application).activityStatDao()
    val allActivity: LiveData<List<ActivityStat>> = activityDao.getAllExercises()
    val current: LiveData<ActivityStat> = activityDao.getExerciseById(0)

    fun insert(activityStat: ActivityStat) {
        viewModelScope.launch(Dispatchers.Main) {
            activityDao.insertAll(activityStat)
        }
    }
}
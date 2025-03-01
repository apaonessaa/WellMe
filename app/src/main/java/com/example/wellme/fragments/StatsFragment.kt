package com.example.wellme.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.wellme.storage.adapter.ActivityAdapter
import com.example.wellme.storage.adapter.MoodAdapter
import com.example.wellme.storage.viewmodel.ActivityViewModel
import com.example.wellme.storage.viewmodel.MoodViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.wellme.R

class StatsFragment : Fragment() {
    private val tag = "StatsFragment"
    //Dichiarazione ViewModel e Adapter per le Activities
    private lateinit var exerciseViewModel: ActivityViewModel
    private lateinit var exerciseAdapter: ActivityAdapter
    //Dichiarazione ViewModel e Adapter per i Moods
    private lateinit var emotionViewModel: MoodViewModel
    private lateinit var emotionAdapter: MoodAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_stats, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(tag, "onViewCreated")
        //RecyclerView1 (Activities); RecyclerView2 (Moods)
        val recyclerView1 = view.findViewById<RecyclerView>(R.id.rv_exercise)
        val recyclerView2 = view.findViewById<RecyclerView>(R.id.rv_emotion)
        exerciseAdapter = ActivityAdapter()
        emotionAdapter = MoodAdapter()
        recyclerView1.adapter = exerciseAdapter
        recyclerView2.adapter = emotionAdapter
        //Configurazione layout di recyclerView (2 elementi per ogni riga)
        recyclerView1.layoutManager = StaggeredGridLayoutManager(2, 1)
        recyclerView2.layoutManager = StaggeredGridLayoutManager(2, 1)
        //ViewModels
        exerciseViewModel = ViewModelProvider(this)[ActivityViewModel::class.java]
        emotionViewModel = ViewModelProvider(this)[MoodViewModel::class.java]
        //Lista di Activities
        exerciseViewModel.allActivity.observe(viewLifecycleOwner) { exercises ->
            exerciseAdapter.setExercices(exercises)
        }
        //Lista di Moods
        emotionViewModel.allMoods.observe(viewLifecycleOwner) { emotions ->
            emotionAdapter.setEmotions(emotions)
        }
    }
}

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.wellme.R
import com.example.wellme.storage.adapter.ActivityAdapter
import com.example.wellme.storage.adapter.MoodAdapter
import com.example.wellme.storage.viewmodel.ActivityViewModel
import com.example.wellme.storage.viewmodel.MoodViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.wellme.ViewItemActivity

class StatsFragment : Fragment() {
    private val tag = "StatsFragment"
    private lateinit var exerciseViewModel: ActivityViewModel
    private lateinit var exerciseAdapter: ActivityAdapter
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

        val recyclerView1 = view.findViewById<RecyclerView>(R.id.rv_exercise)
        val recyclerView2 = view.findViewById<RecyclerView>(R.id.rv_emotion)
        exerciseAdapter = ActivityAdapter()
        emotionAdapter = MoodAdapter()
        recyclerView1.adapter = exerciseAdapter
        recyclerView2.adapter = emotionAdapter
        recyclerView1.layoutManager = StaggeredGridLayoutManager(2, 1)
        recyclerView2.layoutManager = StaggeredGridLayoutManager(1, 1)

        exerciseViewModel = ViewModelProvider(this)[ActivityViewModel::class.java]
        emotionViewModel = ViewModelProvider(this)[MoodViewModel::class.java]

        exerciseViewModel.allActivity.observe(viewLifecycleOwner) { exercises ->
            exerciseAdapter.setExercices(exercises)
        }

        emotionViewModel.allMoods.observe(viewLifecycleOwner) { emotions ->
            emotionAdapter.setEmotions(emotions)
        }
    }
}

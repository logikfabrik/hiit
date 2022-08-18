package se.logikfabrik.hiit

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.CollapsingToolbarLayout
import se.logikfabrik.hiit.databinding.FragmentWorkoutDetailBinding

class WorkoutDetailFragment : Fragment() {

    /**
     * The placeholder content this fragment is presenting.
     */
    private var workout: WorkoutCountDownTimer.Workout? = null

    lateinit var itemDetailTextView: TextView
    private var toolbarLayout: CollapsingToolbarLayout? = null

    private var _binding: FragmentWorkoutDetailBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            if (it.containsKey(ARG_ITEM_ID)) {
                // Load the placeholder content specified by the fragment
                // arguments. In a real-world scenario, use a Loader
                // to load content from a content provider.
                workout = WorkoutCountDownTimer.Workout(10, 5, 3)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentWorkoutDetailBinding.inflate(inflater, container, false)

        val rootView = binding.root

        toolbarLayout = binding.toolbarLayout
        itemDetailTextView = binding.workoutDetail

        binding.fab?.setOnClickListener {
            onStartButtonClick(it)
        }

        updateContent()



        return rootView
    }

    private fun updateContent() {
        /*
        toolbarLayout?.title = workout?.content

        // Show the placeholder content as text in a TextView.
        workout?.let {
            itemDetailTextView.text = it.details
        }

         */
    }

    companion object {
        /**
         * The fragment argument representing the item ID that this fragment
         * represents.
         */
        const val ARG_ITEM_ID = "item_id"
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    private fun onStartButtonClick(button: View) {
        startWorkout()
    }

    private fun startWorkout() {
        startActivity(Intent(activity, TimerActivity::class.java))
    }
}
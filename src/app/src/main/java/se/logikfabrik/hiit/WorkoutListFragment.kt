package se.logikfabrik.hiit

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import se.logikfabrik.hiit.placeholder.PlaceholderContent;
import se.logikfabrik.hiit.databinding.FragmentWorkoutListBinding
import se.logikfabrik.hiit.databinding.WorkoutListContentBinding

/**
 * A Fragment representing a list of Pings. This fragment
 * has different presentations for handset and larger screen devices. On
 * handsets, the fragment presents a list of items, which when touched,
 * lead to a {@link WorkoutDetailFragment} representing
 * item details. On larger screens, the Navigation controller presents the list of items and
 * item details side-by-side using two vertical panes.
 */

class WorkoutListFragment : Fragment() {


    private var _binding: FragmentWorkoutListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWorkoutListBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = binding.workoutList

        // Leaving this not using view binding as it relies on if the view is visible the current
        // layout configuration (layout, layout-sw600dp)
        val itemDetailFragmentContainer: View? =
            view.findViewById(R.id.workout_detail_nav_container)

        setupRecyclerView(recyclerView, itemDetailFragmentContainer)
    }

    private fun setupRecyclerView(
        recyclerView: RecyclerView,
        itemDetailFragmentContainer: View?
    ) {

        recyclerView.adapter = SimpleItemRecyclerViewAdapter(
            PlaceholderContent.ITEMS, itemDetailFragmentContainer
        )
    }

    class SimpleItemRecyclerViewAdapter(
        private val workouts: List<PlaceholderContent.PlaceholderItem>,
        private val itemDetailFragmentContainer: View?
    ) :
        RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

            val binding = WorkoutListContentBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return ViewHolder(binding)

        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val workout = workouts[position]

            holder.idView.text = workout.id
            holder.contentView.text = workout.content

            with(holder.itemView) {
                tag = workout
                setOnClickListener { itemView ->
                    val item = itemView.tag as PlaceholderContent.PlaceholderItem
                    val bundle = Bundle()
                    bundle.putString(
                        WorkoutDetailFragment.ARG_ITEM_ID,
                        item.id
                    )
                    if (itemDetailFragmentContainer != null) {
                        itemDetailFragmentContainer.findNavController()
                            .navigate(R.id.fragment_workout_detail, bundle)
                    } else {
                        itemView.findNavController().navigate(R.id.show_workout_detail, bundle)
                    }
                }
            }
        }

        override fun getItemCount() = workouts.size

        inner class ViewHolder(binding: WorkoutListContentBinding) :
            RecyclerView.ViewHolder(binding.root) {
            val idView: TextView = binding.idText
            val contentView: TextView = binding.content
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
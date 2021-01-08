package android.example.todo.ui.tasklist

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.example.todo.R
import android.example.todo.receive.NotifyBroadcast
import android.example.todo.utls.factory
import android.example.todo.utls.observe
import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_task_list.*

class TaskListFragment: Fragment() {

    private val adapter = TaskListAdapter() { id, isChecked ->
        viewModel.onCheckedTask(id, isChecked)
    }
    private val viewModel: TaskListViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_task_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvList.adapter = adapter
        observe(viewModel.getTasks()) {
            adapter.items = it
        }
        observe(viewModel.cancelTask) { id ->
            ContextCompat.getSystemService(requireContext(), AlarmManager::class.java)?.cancel(
                PendingIntent.getBroadcast(
                    requireContext(),
                    id,
                    Intent(requireContext(), NotifyBroadcast::class.java),
                    PendingIntent.FLAG_ONE_SHOT
                )
            )
        }

        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_add -> {
                findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
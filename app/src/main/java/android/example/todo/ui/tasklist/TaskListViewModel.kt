package android.example.todo.ui.tasklist

import android.example.todo.storage.dao.TaskDao
import android.example.todo.utls.SingleLiveEvent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.util.*

class TaskListViewModel(private val dao: TaskDao): ViewModel() {

    private val mCancelTask: MutableLiveData<Int> = SingleLiveEvent()

    val cancelTask: LiveData<Int> = mCancelTask

    fun getTasks() = dao.getTasks()
        .map {
            it.map { list ->
                with(list) {
                    android.example.todo.ui.tasklist.TaskListItem(
                        id,
                        name,
                        isChecked,
                        datetime?.let { time -> java.text.DateFormat.format("E dd MMM HH:mm", java.util.Date(time))}
                    )
                }
            }
        }
        .asLiveDate(viewModelScope.coroutineContext)

    fun onCheckedTask(id: Long, checked: Boolean) {
        if (checked) mCancelTask.value = id.toInt()
        viewModelScope.launch {
            dao.setChecked(id, checked)
        }
    }
}
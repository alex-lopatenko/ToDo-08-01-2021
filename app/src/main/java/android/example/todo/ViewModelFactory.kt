package android.example.todo

import android.example.todo.storage.AppDataBase
import android.example.todo.ui.addtask.AddTaskViewModel
import android.example.todo.ui.tasklist.TaskListViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class ViewModelFactory(private val appDataBase: AppDataBase) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when (modelClass) {
            TaskListViewModel::class.java -> TaskListViewModel(appDataBase.taskDao())
            AddTaskViewModel::class.java -> AddTaskViewModel(appDataBase.taskDao())
            else -> throw IllegalArgumentException("Cannot find $modelClass")
        } as T
    }
}

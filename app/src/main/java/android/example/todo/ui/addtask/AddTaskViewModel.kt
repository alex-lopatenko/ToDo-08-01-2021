package android.example.todo.ui.addtask

import android.example.todo.storage.dao.TaskDao
import android.example.todo.storage.entity.TaskEntity
import android.example.todo.utls.SingleLiveEvent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.util.*

class AddTaskViewModel(private val dao: TaskDao) : ViewModel() {

    private var name: String = ""
    private var notifyTime: Date = Date()
    private var notifyEnabled = false

    private val mTimeChecked = MutableLiveData<Boolean>()
    private val mResult:MutableLiveData<Result> = SingleLiveEvent<Result>()
    private val mSaveEnabled = MutableLiveData<Boolean>(false)
    private val mDateTime = MutableLiveData(notifyTime)

    val timeChecked: LiveData<Boolean> = mTimeChecked
    val result: LiveData<Result> = mResult
    val  saveEnabled: LiveData<Boolean>  = mSaveEnabled
    val dateTime: LiveData<Date> = mDateTime


    fun onNameChanged(text: CharSequence?) {
        name = text?.toString() ?: ""
        mSaveEnabled.value =!text.isNullOrEmpty()
    }

    fun onTimeCheckedChange(checked: Boolean) {
        mTimeChecked.value = checked
    }

    fun OnDateChanged(date: Date) {
        notifyTime = date
        mDateTime.value = date
    }

    fun onSaveClick() {
        viewModelScope.launch {
            name?.let { name ->
                val id = dao.insert(TaskEntity(name, notifyEnabled, if (notifyEnabled) notifyTime.time else null))
                mResult.value = Result.Save(id.toInt(), name, if (notifyEnabled) notifyTime.time else null)
            }
        }
    }

    fun onCancelClick() {
        mResult.value = Result.Cancel()
    }
}

sealed class Result {
    object Cancel: Result()
    data class Save(val id: Int, val name: String, val time: Long?): Result()
}
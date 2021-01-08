package android.example.todo.utls

import android.example.todo.App
import android.example.todo.ViewModelFactory
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

inline fun <T> Fragment.observe(data: LiveData<T>, crossinline callback: (T) -> Unit) {
    data.observe(viewLifecycleOwner, Observer { event -> event?.let { callback(it) } })
}

inline val Fragment.factory: ViewModelFactory
    get() = (requireActivity().application as App).factory
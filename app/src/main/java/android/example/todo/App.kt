package android.example.todo

import android.app.Application
import android.example.todo.storage.AppDataBase
import androidx.room.Room

class App: Application() {

    lateinit var factory: ViewModelFactory

    override fun onCreate() {
        super.onCreate()
        val room = Room.databaseBuilder(this, AppDataBase::class.java).build()
        factory = ViewModelFactory(room)
    }
}
package android.example.todo.storage

import android.example.todo.storage.dao.TaskDao
import android.example.todo.storage.entity.TaskEntity
import androidx.room.Database
import androidx.room.RoomDatabase


@Database(version = 1, exportSchema = false, entities = [TaskEntity::class])
abstract class AppDataBase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}
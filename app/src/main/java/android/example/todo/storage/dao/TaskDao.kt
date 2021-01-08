package android.example.todo.storage.dao

import android.example.todo.storage.entity.TaskEntity
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import org.intellij.lang.annotations.Flow

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: TaskEntity): Long

    @Query("SELECT * FROM tasks ORDER BY id")
    fun getTasks(): Flow<List<TaskEntity>>

    @Query("UPDATE tasks SET is_checked=:checked WHERE id=:id")
    suspend fun setChecked(id: Long, checked: Boolean)
}
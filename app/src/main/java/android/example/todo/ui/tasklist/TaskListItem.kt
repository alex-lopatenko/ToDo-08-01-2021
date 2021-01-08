package android.example.todo.ui.tasklist


class TaskListItem(
        val id: Long,
        val name: String,
        val isChecked: Boolean,
        val datetime: CharSequence?
) {
}
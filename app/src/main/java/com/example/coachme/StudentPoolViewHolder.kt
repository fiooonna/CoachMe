package com.example.coachme
import androidx.recyclerview.widget.RecyclerView
import com.example.coachme.databinding.ItemStudentpoolBinding


class StudentPoolViewHolder(
    private val binding: ItemStudentpoolBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(student: Student, onSelect: (Student?) -> Unit) {
        binding.student = student
        binding.root.setOnClickListener {
            onSelect(student)
        }
    }

}
package com.example.coachme

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.coachme.databinding.ItemStudentpoolBinding

class StudentPoolAdaptor (private var studentsList: ArrayList<Student>, private val onSelect: (Student?) -> Unit) :
    RecyclerView.Adapter<StudentPoolViewHolder>() {
    private lateinit var binding: ItemStudentpoolBinding


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentPoolViewHolder {
        binding = ItemStudentpoolBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StudentPoolViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StudentPoolViewHolder, position: Int) {
        val student = studentsList[position]
        Log.i("onBindViewHolder", student.toString())
        holder.bind(student, onSelect)
    }

//    override fun getItemCount(): Int = studentsList.size
    override fun getItemCount(): Int {
        Log.i("getItemCount", studentsList.size.toString())
        return studentsList.size
    }

    class OnClickListener(val clickListener: (student: Student) -> Unit) {
        fun onClick(student: Student) = clickListener(student)

    }


}
package com.example.coachme

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.coachme.databinding.ActivityStudentpoolBinding


class studentpoolActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStudentpoolBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        binding and setting the content of student pool page, assuming the main_activity is the pool page
        binding = DataBindingUtil.setContentView(this, R.layout.activity_studentpool)

        val recyclerViewStudents = binding.rvStudent
        val dummyStudent1 = Student(image = getDrawable(R.drawable.coach1),name = "Freda",location = "Lai Chi Kok",goals = "Reduce Weight",experience = "Intermediate",price = "$100/hr")
        val dummyStudent2 = Student(image = getDrawable(R.drawable.coach2),name = "Tina",location = "Mong Kok",goals = "Build Muscles, Loss Fat",experience = "Beginner",price = "$300/hr")
        val students = listOf(dummyStudent1,dummyStudent2)
        val studentsAdapter = StudentPoolAdaptor(students)
        recyclerViewStudents.adapter = studentsAdapter
        recyclerViewStudents.layoutManager = LinearLayoutManager(this)
        recyclerViewStudents.setHasFixedSize(true)
        // set filter button onClickListener, onClick go to studentpool_filter layout
        val filterButton = findViewById<Button>(R.id.filterbutton_studentpool)
        filterButton.setOnClickListener(View.OnClickListener() {
            val filterIntent = Intent(this, StudentPoolFilterActivity::class.java)
            startActivity(filterIntent)
        })

    // ASSUME SORT button onClickListener, onClick goes to coachpool layout!!!!!
        val sortButton = findViewById<Button>(R.id.sortbutton_studentpool)
        sortButton.setOnClickListener(View.OnClickListener() {
            val sortIntent = Intent(this, CoachPoolActivity::class.java)
            startActivity(sortIntent)

        })
    }

}
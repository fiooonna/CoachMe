package com.example.coachme

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coachme.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        binding and setting the content of student pool page, assuming the main_activity is the pool page
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val recyclerViewStudents = binding.rvStudent
        val dummyStudent1 = Student(image = getDrawable(R.drawable.coach1),name = "Freda",location = "Lai Chi Kok",goals = "Reduce Weight",experience = "Intermediate",price = "$100/hr")
        val dummyStudent2 = Student(image = getDrawable(R.drawable.coach2),name = "Alex",location = "Tsuen Wan",goals = "Gain Weight",experience = "Beginner",price = "$170/hr")
        val students = listOf(dummyStudent1,dummyStudent2)
        val studentsAdapter = StudentPoolAdaptor(students)
        recyclerViewStudents.adapter = studentsAdapter
        recyclerViewStudents.layoutManager = LinearLayoutManager(this)
        recyclerViewStudents.setHasFixedSize(true)

        // set filter button onClickListener, onClick go to studentpool_filter layout
        val filterButton = findViewById<Button>(R.id.filterbutton_studentpool)
        filterButton.setOnClickListener {
            val filterIntent = Intent(this, StudentPoolFilterActivity::class.java)
            startActivity(filterIntent)
        }

    // ASSUME SORT button onClickListener, onClick goes to coachpool layout!!!!!
        val sortButton = findViewById<Button>(R.id.sortbutton_studentpool)
        sortButton.setOnClickListener {
            val sortIntent = Intent(this, CoachPoolActivity::class.java)
            startActivity(sortIntent)
        }

    }

}
package com.example.coachme

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
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
        val header_name = findViewById<TextView>(R.id.header_name)
        header_name.setText("Sam")
        val dummyStudent1 = Student(image = getDrawable(R.drawable.student3),name = "Freda",location = "Lai Chi Kok",goals = "Reduce Weight",experience = "Intermediate",price = "$100/hr")
        val dummyStudent2 = Student(image = getDrawable(R.drawable.student4),name = "Tina",location = "Mong Kok",goals = "Build Muscles, Loss Fat",experience = "Beginner",price = "$300/hr")
        val dummyStudent3 = Student(image = getDrawable(R.drawable.student5),name = "Roy",location = "Shum Shui Po",goals = "Participate in IFBB Pro",experience = "Advanced",price = "$500/hr")
        val dummyStudent4 = Student(image = getDrawable(R.drawable.student6),name = "Jack",location = "HKU",goals = "Slimmer Body",experience = "Beginner",price = "$300/hr")
        val dummyStudent5 = Student(image = getDrawable(R.drawable.student7),name = "Lucas",location = "Causeway Bay",goals = "Reduce Weight",experience = "Intermediate",price = "$100/hr")
        val dummyStudent6 = Student(image = getDrawable(R.drawable.student1),name = "Ryan",location = "Central",goals = "Build Muscles, Loss Fat",experience = "Beginner",price = "$300/hr")
        val students = listOf(dummyStudent1,dummyStudent2,dummyStudent3,dummyStudent4,dummyStudent5,dummyStudent6)
        val studentsAdapter = StudentPoolAdaptor(students, StudentPoolAdaptor.OnClickListener {
            val intent = Intent(this, studentprofile::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        })
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
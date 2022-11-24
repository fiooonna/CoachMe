package com.example.coachme

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.coachme.databinding.ActivityStudentpoolBinding


class studentpoolActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStudentpoolBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        var students: ArrayList<Student> = ArrayList()
        val url:String = Coach_reg3.FLASK_URL +"get_objects_student"
        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                for (i in 0 until response.length()) {
                    // extracting data from each json object
                    val respObj = response.getJSONObject(i)
                    val name = respObj.getString("name")
                    val location = respObj.getString("location")
                    val goals = respObj.getString("goals")
                    val experience = respObj.getString("experience")
                    val minPay = respObj.getString("min_pay")
                    val maxPay = respObj.getString("max_pay")
                    val price = "$minPay - $maxPay/Hr"
//                    //adding data to the list
                    students.add(Student(name,null,location,goals,experience,price))

                }
                Log.d("students list extracted", students.toString())
            },
            { error ->
                //Handle error
                Log.d("error of getting student objects", error.toString())
            })
        Volley.newRequestQueue(this).add(jsonArrayRequest)

        super.onCreate(savedInstanceState)
//        binding and setting the content of student pool page, assuming the main_activity is the pool page
        binding = DataBindingUtil.setContentView(this, R.layout.activity_studentpool)
        val recyclerViewStudents = binding.rvStudent
        val header_name = findViewById<TextView>(R.id.header_name)
        //TODO: ADD DYNAMIC PROFILE PIC!!!!
        //get the extras set up during login, to retrieve the current user info
        var intent: Intent = intent
        val currentUserFirstName = intent.getStringExtra("first_name")
        header_name.text = currentUserFirstName.toString()
        Log.i("set the header name!", currentUserFirstName.toString())


//        val dummyStudent1 = Student(image = getDrawable(R.drawable.student3),name = "Freda",location = "Lai Chi Kok",goals = "Reduce Weight",experience = "Intermediate",price = "$100/hr")
//        val dummyStudent2 = Student(image = getDrawable(R.drawable.student4),name = "Tina",location = "Mong Kok",goals = "Build Muscles, Loss Fat",experience = "Beginner",price = "$300/hr")
//        val dummyStudent3 = Student(image = getDrawable(R.drawable.student5),name = "Roy",location = "Shum Shui Po",goals = "Participate in IFBB Pro",experience = "Advanced",price = "$500/hr")
//        val dummyStudent4 = Student(image = getDrawable(R.drawable.student6),name = "Jack",location = "HKU",goals = "Slimmer Body",experience = "Beginner",price = "$300/hr")
//        val dummyStudent5 = Student(image = getDrawable(R.drawable.student7),name = "Lucas",location = "Causeway Bay",goals = "Reduce Weight",experience = "Intermediate",price = "$100/hr")
//        val dummyStudent6 = Student(image = getDrawable(R.drawable.student1),name = "Ryan",location = "Central",goals = "Build Muscles, Loss Fat",experience = "Beginner",price = "$300/hr")
//        val students_ = listOf(dummyStudent1,dummyStudent2,dummyStudent3,dummyStudent4,dummyStudent5,dummyStudent6)

        Log.i("students list", students.toString())
        val studentsAdapter = StudentPoolAdaptor(students, StudentPoolAdaptor.OnClickListener {
            val intentStudentProfile = Intent(this, studentprofile::class.java)
//            intent_studentProfile.putExtra("userID", userID)
//            Log.i("sent userID to student profile activity", userID.toString())
            startActivity(intentStudentProfile)
            overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left)
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
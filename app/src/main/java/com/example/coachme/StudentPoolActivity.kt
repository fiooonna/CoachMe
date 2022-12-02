package com.example.coachme

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.doOnPreDraw
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.androidbuts.multispinnerfilter.KeyPairBoolData
import com.androidbuts.multispinnerfilter.SingleSpinnerListener
import com.androidbuts.multispinnerfilter.SingleSpinnerSearch
import com.example.coachme.databinding.ActivityStudentpoolBinding
import java.util.*
import kotlin.collections.ArrayList


class StudentPoolActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStudentpoolBinding
    private lateinit var studentsAdapter: StudentPoolAdaptor
    override fun onCreate(savedInstanceState: Bundle?) {
        var students: ArrayList<Student> = ArrayList()
        val url:String = Coach_reg3.FLASK_URL +"get_objects_student"
        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                for (i in 0 until response.length()) {
                    // extracting data from each json object
                    val respObj = response.getJSONObject(i)
                    val user_id = respObj.getInt("user_id")
                    val user_gender = respObj.getString("gender")
                    val student_id = respObj.getInt("student_id")
                    val name = respObj.getString("name")
                    val location = respObj.getString("location")
                    val goals = respObj.getString("goals")
                    val experience = respObj.getString("experience")
                    val minPay = respObj.getString("min_pay")
                    val maxPay = respObj.getString("max_pay")
                    val price = "$minPay - $maxPay/Hr"
                    //adding data to the list
                    students.add(Student(user_id, student_id,user_gender,name,getDrawable(R.drawable.student2),location,goals,experience,price))

                }
                Log.d("students list extracted", students.toString())
                if (students.size > 0 && intent.getStringArrayListExtra("filter_location") != null){
                    students = filterStudents(intent, students)
                    Log.i("Filtered Students: ", students.toString())
                    studentsAdapter.updateData(students)
                    studentsAdapter.notifyDataSetChanged();
                }
                studentsAdapter.notifyDataSetChanged();
            },
            { error ->
                //Handle error
                Log.d("error of getting student objects", error.toString())
            })
        Volley.newRequestQueue(this).add(jsonArrayRequest)

        super.onCreate(savedInstanceState)
//        binding and setting the content of student pool page, assuming the main_activity is the pool page
        binding = DataBindingUtil.setContentView(this, R.layout.activity_studentpool)
        postponeEnterTransition()
        val recyclerViewStudents = binding.rvStudent
        recyclerViewStudents.doOnPreDraw {
            startPostponedEnterTransition()
        }
        val header_name = findViewById<TextView>(R.id.header_name)
        //TODO: ADD DYNAMIC PROFILE PIC!!!!
        //get the extras set up during login, to retrieve the current user info
        var intent: Intent = intent
        val currentUserFirstName = getSharedPreferences("userSharedPreference", MODE_PRIVATE)
            .getString("first_name", "")
        val user_id: Int = intent.getIntExtra("user_id", 0)
        val coach_id: Int = intent.getIntExtra("coach_id", 0)
        val username: String? = intent.getStringExtra("username")


        header_name.text = currentUserFirstName.toString()
        Log.i("set the header name!", currentUserFirstName.toString())

        //val dummyStudent1 = Student(image = getDrawable(R.drawable.student3),name = "Freda",location = "Lai Chi Kok",goals = "Reduce Weight",experience = "Intermediate",price = "$100/hr",  user_id = 100, student_id = 100)
//        val dummyStudent2 = Student(image = getDrawable(R.drawable.student4),name = "Tina",location = "Mong Kok",goals = "Build Muscles, Loss Fat",experience = "Beginner",price = "$300/hr")
//        val dummyStudent3 = Student(image = getDrawable(R.drawable.student5),name = "Roy",location = "Shum Shui Po",goals = "Participate in IFBB Pro",experience = "Advanced",price = "$500/hr")
//        val dummyStudent4 = Student(image = getDrawable(R.drawable.student6),name = "Jack",location = "HKU",goals = "Slimmer Body",experience = "Beginner",price = "$300/hr")
//        val dummyStudent5 = Student(image = getDrawable(R.drawable.student7),name = "Lucas",location = "Causeway Bay",goals = "Reduce Weight",experience = "Intermediate",price = "$100/hr")
//        val dummyStudent6 = Student(image = getDrawable(R.drawable.student1),name = "Ryan",location = "Central",goals = "Build Muscles, Loss Fat",experience = "Beginner",price = "$300/hr")
//        val students_ = listOf(dummyStudent1,dummyStudent2,dummyStudent3,dummyStudent4,dummyStudent5,dummyStudent6)
        //students.add(dummyStudent1)
        Log.i("students list", students.toString())
        //TODO: send the selected student id/info to the profile page and add extra intent
        studentsAdapter = StudentPoolAdaptor(students) {
            Student ->
            Log.d("Chosen Student", Student!!.name)
            val intent = Intent(this, studentprofile::class.java)
            intent.putExtra("student_user_id", Student!!.user_id)
            intent.putExtra("student_id", Student!!.student_id)
            intent.putExtra("coach_user_id", user_id)
            intent.putExtra("student_id", coach_id)
            intent.putExtra("student_username", username)

            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        }
        recyclerViewStudents.adapter = studentsAdapter
        recyclerViewStudents.layoutManager = LinearLayoutManager(this)
        recyclerViewStudents.setHasFixedSize(true)

        //send current filter information
        // set filter button onClickListener, onClick go to studentpool_filter layout
        val filterButton = findViewById<Button>(R.id.filterbutton_studentpool)
        filterButton.setOnClickListener {
            val filterIntent = Intent(this, StudentPoolFilterActivity::class.java)
            startActivity(filterIntent)
        }

    // Implementing sort (max pay desc, name ascd, goal ascd)
        val sortButton = findViewById<SingleSpinnerSearch>(R.id.sortbutton_studentpool)
        sortButton.isSearchEnabled = false

        val sort_options = arrayOf("Max Pay","Name","Goal")
        var sortArray = ArrayList<KeyPairBoolData>()
        for (i in sort_options.indices) {
            val keyPairBoolData = KeyPairBoolData()
            keyPairBoolData.id = (i + 1).toLong()
            keyPairBoolData.name = sort_options[i]
            keyPairBoolData.isSelected = false
            sortArray.add(keyPairBoolData)
        }

        sortButton.setItems(sortArray, object : SingleSpinnerListener {
            override fun onItemsSelected(selectedItem: KeyPairBoolData) {
                Log.i("Selected Item in Sorting: ",  selectedItem.name + selectedItem.id)
                var sortedStudents: List<Student> = students

                if (selectedItem.name == "Max Pay" && students.size>0){
                    sortedStudents =
                        sortedStudents.sortedWith(compareByDescending {
                            it.price.substringBeforeLast("/").split(" - ")[1].toInt()
                        })
                }
                else if (selectedItem.name == "Name" && students.size>0){
                    sortedStudents =
                        sortedStudents.sortedWith(compareBy {
                            it.name
                        })

                }
                else if (selectedItem.name == "Goal" && students.size>0){
                    sortedStudents =
                        sortedStudents.sortedWith(compareBy {
                            it.goals
                        })
                }
                val sortedStudents_arrayList:ArrayList<Student> = ArrayList(sortedStudents)
                studentsAdapter.updateData(sortedStudents_arrayList)
                studentsAdapter.notifyDataSetChanged()
            }

            override fun onClear() {
                Toast.makeText(this@StudentPoolActivity, "Returned to default sorting", Toast.LENGTH_SHORT)
                    .show()
            }
        })


    }

    private fun filterStudents(intent: Intent, students: ArrayList<Student>): ArrayList<Student> {
        Log.i("studentPoolActivity","FilterStudents IS TRIGGERED")
        Log.i("before filtering",students.toString())
        var filteredStudents: ArrayList<Student> = students
        val filter_gender: ArrayList<String>? = intent.getStringArrayListExtra("filter_gender")
        val filter_location = intent.getStringArrayListExtra("filter_location")
        val filter_goal = intent.getStringArrayListExtra("filter_goal")
        val filter_experience_lower = intent.getIntExtra("filter_experience_lower",0)
        val filter_experience_upper = intent.getIntExtra("filter_experience_upper",2)
        val filter_pay_lower = intent.getIntExtra("filter_pay_lower",0)
        val filter_pay_upper = intent.getIntExtra("filter_pay_upper",1000)
        Log.d("Filter Condition", "$filter_pay_lower, $filter_pay_upper, $filter_goal, $filter_experience_lower, $filter_experience_upper")

        if (filter_gender != null && filter_gender.isNotEmpty()) {
            filteredStudents = filteredStudents.filter { filter_gender.contains(it.gender) } as ArrayList<Student>
            Log.i("after filtering gender", filteredStudents.toString())
        }
        if (filter_goal != null && filter_goal.isNotEmpty()) {
            filteredStudents = filteredStudents.filter{filter_goal.contains(it.goals)} as ArrayList<Student>
            Log.i("after filtering goal", filteredStudents.toString())
        }

        if (filter_location != null && filter_location.isNotEmpty()) {
            filteredStudents = filteredStudents.filter{filter_location.contains(it.location)} as ArrayList<Student>
            Log.i("after filtering location", filteredStudents.toString())
        }
        Log.d("Price", students.get(0).price.substringBeforeLast("/").split(" - ")[1].toString())
        filteredStudents = filteredStudents.filter{ it.price.substringBeforeLast("/").split(" - ")[0].toInt() <= filter_pay_upper } as ArrayList<Student>
        filteredStudents = filteredStudents.filter{ it.price.substringBeforeLast("/").split(" - ")[1].toInt() >= filter_pay_lower } as ArrayList<Student>
        Log.i("after filtering pay", students.toString())

        val experienceWordings = arrayListOf("Beginner","Intermediate","Advanced")
        filteredStudents = filteredStudents.filter{ experienceWordings.indexOf(it.experience) <= filter_experience_upper} as ArrayList<Student>
        filteredStudents = filteredStudents.filter{ experienceWordings.indexOf(it.experience) >= filter_experience_lower} as ArrayList<Student>

        Log.i("after filtering experience", filteredStudents.toString())


        return filteredStudents

    }

}
package com.example.coachme

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.coachme.databinding.CoachpoolBinding
import de.hdodenhof.circleimageview.CircleImageView


class CoachPoolActivity : AppCompatActivity() {

    private lateinit var binding: CoachpoolBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        var coaches: ArrayList<Coach> = ArrayList()
        val url:String = Coach_reg3.FLASK_URL +"get_objects_coach"
        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                for (i in 0 until response.length()) {
                    // extracting data from each json object
                    val respObj = response.getJSONObject(i)
                    val user_id = respObj.getInt("user_id")
                    val name = respObj.getString("name")
                    val coach_id = respObj.getInt("coach_id")
                    val yearExp = respObj.getString("yearExp")
                    val qualification = respObj.getString("qualification")
                    val rating = respObj.getInt("rating").toString()
                    val bookmark = respObj.getInt("bookmark").toString()
                    val rated_ppl = respObj.getInt("rated_ppl")

//                   //adding data to the list
                    coaches.add(Coach(user_id = user_id,coach_id =  coach_id, name,image = getDrawable(R.drawable.coach2),yearExp,qualification,rating,bookmark, rated_ppl))


                }
                Log.d("coaches list extracted", coaches.toString())
            },
            { error ->
                //Handle error
                Log.d("error of getting coach objects", error.toString())
            })
        Volley.newRequestQueue(this).add(jsonArrayRequest)


        super.onCreate(savedInstanceState)
        val user_id: Int? = intent.getIntExtra("user_id", 0)
        val student_id: Int? = intent.getIntExtra("student_id", 0)
        val username: String? = intent.getStringExtra("username")

//        binding and setting the content of coach pool page
        binding = DataBindingUtil.setContentView(this, R.layout.coachpool)

        val profile_image = findViewById<CircleImageView>(R.id.profile_image)
        val header_name = findViewById<TextView>(R.id.header_name)
        var intent: Intent = intent
        val currentUserFirstName = intent.getStringExtra("first_name")
        header_name.text = currentUserFirstName.toString()
        Log.i("set the header name!", currentUserFirstName.toString())
        //TODO: set dynamic profile pic
        profile_image.setImageResource(R.drawable.student2)

        val recyclerViewCoaches = binding.rvCoach
        //val dummyCoach1 = Coach(
          //  coach_id = 100, user_id = 100,
            //image = getDrawable(R.drawable.coach1),
            //name = "Timmy",
            //yearExp = "10",
            //qualification = "Former Strongman Champion, IFBB Pro", rating = "5400", bookmark = "1200", rated_ppl = 1200)
//        val dummyCoach2 = Coach(coach_id = 2, image = getDrawable(R.drawable.coach2),name = "John",yearExp = "40 years",
//            qualification = "IFBB Pro", rating = "2.3", bookmark = "4k")
//        val dummyCoach3 = Coach(coach_id = 3,image = getDrawable(R.drawable.coach3),name = "Robert",yearExp = "20 years",
//            qualification = "NASM", rating = "3.9", bookmark = "900")
//        val dummyCoach4 = Coach(coach_id = 4, image = getDrawable(R.drawable.coach4),name = "Larry",yearExp = "30 years",
//            qualification = "CPT", rating = "4.2", bookmark = "702")
//        val dummyCoach5 = Coach(coach_id = 5,image = getDrawable(R.drawable.coach5),name = "Valencia",yearExp = "5 years",
//            qualification = "IFBB Pro", rating = "4.1", bookmark = "1k")
//        val dummyCoach6 = Coach(coach_id = 6, image = getDrawable(R.drawable.coach6),name = "Kelvin",yearExp = "8 years",
//            qualification = "IFBB Pro", rating = "3.3", bookmark = "1.4k")
//        val coaches = listOf(dummyCoach1,dummyCoach2,dummyCoach3,dummyCoach4,dummyCoach5,dummyCoach6)
        //coaches.add(dummyCoach1)
        /*val coachesAdapter = CoachPoolAdaptor(coaches, CoachPoolAdaptor.OnClickListener {
            val intent = Intent(this, coachprofile::class.java)
            intent.putExtra("user_id", user_id)
            intent.putExtra("student_id", student_id)
            intent.putExtra("username", username)

            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        }) */

        val coachesAdapter = CoachPoolAdaptor(coaches) {
            Coach ->
            Log.d("Chosen Coach", Coach!!.name)
            Log.d("Chosen Coach ID", Coach!!.coach_id.toString())
            val intent = Intent(this, coachprofile::class.java)

            intent.putExtra("coach_name", Coach!!.name)
            intent.putExtra("coach_user_id", Coach!!.user_id)
            intent.putExtra("coach_id", Coach!!.coach_id)
            intent.putExtra("qua", Coach!!.qualification)
            intent.putExtra("yearExp", Coach!!.yearExp)
            intent.putExtra("bookmark", Coach!!.bookmark)
            intent.putExtra("rating", Coach.rating)
            intent.putExtra("rated_ppl", Coach.rated_ppl)
            intent.putExtra("student_user_id", user_id)
            intent.putExtra("student_id", student_id)
            intent.putExtra("student_first_name", currentUserFirstName)
            intent.putExtra("student_username", username)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);


        }



        recyclerViewCoaches.adapter = coachesAdapter
        recyclerViewCoaches.layoutManager = LinearLayoutManager(this)
        recyclerViewCoaches.setHasFixedSize(true)

        val filter_button = findViewById<Button>(R.id.filter_button)
        filter_button.setOnClickListener {
            val intent = Intent(this, CoachPoolFilter::class.java)
            startActivity(intent)
        }

//        val coachInfoButton = findViewById<ImageButton>(R.id.coach_info_Button)
//        coachInfoButton.setOnClickListener {
//            val coachInfoIntent = Intent(this, coachprofile::class.java)
//            startActivity(coachInfoIntent)
//        }

    }


}
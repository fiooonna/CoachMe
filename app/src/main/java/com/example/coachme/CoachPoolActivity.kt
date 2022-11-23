package com.example.coachme

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coachme.databinding.CoachpoolBinding
import de.hdodenhof.circleimageview.CircleImageView


class CoachPoolActivity : AppCompatActivity() {

    private lateinit var binding: CoachpoolBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val user_id: String? = intent.getStringExtra("user_id")
        val student_id: String? = intent.getStringExtra("student_id")
        val username: String? = intent.getStringExtra("username")
//        binding and setting the content of coach pool page
        binding = DataBindingUtil.setContentView(this, R.layout.coachpool)
        val profile_image = findViewById<CircleImageView>(R.id.profile_image)
        val header_name = findViewById<TextView>(R.id.header_name)
        profile_image.setImageResource(R.drawable.student2)
        header_name.setText("Tina")

        val recyclerViewCoaches = binding.rvCoach
        val dummyCoach1 = Coach(coach_id = 1, image = getDrawable(R.drawable.coach1),name = "Timmy",experience = "10 years",
            qualification = "Former Strongman Champion, IFBB Pro", rating = "4.1", bookmark = "1.2k")
        val dummyCoach2 = Coach(coach_id = 2, image = getDrawable(R.drawable.coach2),name = "John",experience = "40 years",
            qualification = "IFBB Pro", rating = "2.3", bookmark = "4k")
        val dummyCoach3 = Coach(coach_id = 3,image = getDrawable(R.drawable.coach3),name = "Robert",experience = "20 years",
            qualification = "NASM", rating = "3.9", bookmark = "900")
        val dummyCoach4 = Coach(coach_id = 4, image = getDrawable(R.drawable.coach4),name = "Larry",experience = "30 years",
            qualification = "CPT", rating = "4.2", bookmark = "702")
        val dummyCoach5 = Coach(coach_id = 5,image = getDrawable(R.drawable.coach5),name = "Valencia",experience = "5 years",
            qualification = "IFBB Pro", rating = "4.1", bookmark = "1k")
        val dummyCoach6 = Coach(coach_id = 6, image = getDrawable(R.drawable.coach6),name = "Kelvin",experience = "8 years",
            qualification = "IFBB Pro", rating = "3.3", bookmark = "1.4k")
        val coaches = listOf(dummyCoach1,dummyCoach2,dummyCoach3,dummyCoach4,dummyCoach5,dummyCoach6)

//        val coachesAdapter = CoachPoolAdaptor(coaches)

        val coachesAdapter = CoachPoolAdaptor(coaches, CoachPoolAdaptor.OnClickListener {
            val intent = Intent(this, coachprofile::class.java)
            startActivity(intent)
            intent.putExtra("user_id", user_id)
            intent.putExtra("student_id", student_id)
            intent.putExtra("username", username)
            overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        })

        recyclerViewCoaches.adapter = coachesAdapter
        recyclerViewCoaches.layoutManager = LinearLayoutManager(this)
        recyclerViewCoaches.setHasFixedSize(true)

//        val coachInfoButton = findViewById<ImageButton>(R.id.coach_info_Button)
//        coachInfoButton.setOnClickListener {
//            val coachInfoIntent = Intent(this, coachprofile::class.java)
//            startActivity(coachInfoIntent)
//        }

    }

}
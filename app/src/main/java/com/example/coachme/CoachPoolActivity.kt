package com.example.coachme

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coachme.databinding.CoachpoolBinding


class CoachPoolActivity : AppCompatActivity() {

    private lateinit var binding: CoachpoolBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        binding and setting the content of coach pool page
        binding = DataBindingUtil.setContentView(this, R.layout.coachpool)

        val recyclerViewCoaches = binding.rvCoach
        val dummyCoach1 = Coach(image = getDrawable(R.drawable.coach9),name = "Timmy",experience = "10 years",
            qualification = "Former Strongman Champion, IFBB Pro", rating = "4.1", bookmark = "1.2k")
        val dummyCoach2 = Coach(image = getDrawable(R.drawable.coach10),name = "John",experience = "40 years",
            qualification = "IFBB Pro", rating = "2.3", bookmark = "4k")
        val dummyCoach3 = Coach(image = getDrawable(R.drawable.coach8),name = "Robert",experience = "20 years",
            qualification = "NASM", rating = "3.9", bookmark = "900")
        val dummyCoach4 = Coach(image = getDrawable(R.drawable.coach11),name = "Larry",experience = "30 years",
            qualification = "CPT", rating = "4.2", bookmark = "702")
        val dummyCoach5 = Coach(image = getDrawable(R.drawable.coach14),name = "Valencia",experience = "5 years",
            qualification = "IFBB Pro", rating = "4.1", bookmark = "1k")
        val dummyCoach6 = Coach(image = getDrawable(R.drawable.coach15),name = "Kelvin",experience = "8 years",
            qualification = "IFBB Pro", rating = "3.3", bookmark = "1.4k")
        val coaches = listOf(dummyCoach1,dummyCoach2,dummyCoach3,dummyCoach4,dummyCoach5,dummyCoach6)

//        val coachesAdapter = CoachPoolAdaptor(coaches)

        val coachesAdapter = CoachPoolAdaptor(coaches, CoachPoolAdaptor.OnClickListener {
            val intent = Intent(this, coachprofile::class.java)
            startActivity(intent)
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
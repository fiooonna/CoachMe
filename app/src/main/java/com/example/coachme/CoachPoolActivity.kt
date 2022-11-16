package com.example.coachme

import android.content.Intent
import android.os.Bundle
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
        val dummyCoach1 = Coach(image = getDrawable(R.drawable.coach1),name = "Timmy",experience = "10 years",
            qualification = "Former Strongman Champion, IFBB Pro", rating = "4.1", bookmark = "1.2k")
        val dummyCoach2 = Coach(image = getDrawable(R.drawable.coach2),name = "John",experience = "40 years",
            qualification = "IFBB Pro", rating = "2.3", bookmark = "4k")
        val coaches = listOf(dummyCoach1,dummyCoach2)
        val coachesAdapter = CoachPoolAdaptor(coaches)
        recyclerViewCoaches.adapter = coachesAdapter
        recyclerViewCoaches.layoutManager = LinearLayoutManager(this)
        recyclerViewCoaches.setHasFixedSize(true)


    }

}
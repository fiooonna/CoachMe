package com.example.coachme

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class rating : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rating)
        val ratebutton = findViewById<Button>(R.id.ratebutton)


        ratebutton.setOnClickListener {
            val coachintent = Intent(this, coachprofile::class.java)
            Toast.makeText(getApplicationContext(),"Thank you! You have rated Bob", Toast.LENGTH_SHORT).show();
            startActivity(coachintent)

        }
    }

}
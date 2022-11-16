package com.example.coachme

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class studentprofile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_studentprofile)
        val dumbbellhome = findViewById<ImageButton>(R.id.dumbbell_button)
        val sendbutton = findViewById<Button>(R.id.send_button)


        dumbbellhome.setOnClickListener {
            val coachintent = Intent(this, coachprofile::class.java)
            startActivity(coachintent)
            overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);

        }

        sendbutton.setOnClickListener{
            Toast.makeText(getApplicationContext(),"Request Sent to Bob", Toast.LENGTH_SHORT).show();
        }
    }
}

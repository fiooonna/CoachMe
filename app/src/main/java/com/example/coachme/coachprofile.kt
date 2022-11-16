package com.example.coachme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton

class coachprofile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coachprofile)

        val sendbutton = findViewById<Button>(R.id.send_button)
        val dumbbellhome = findViewById<ImageButton>(R.id.dumbbell_button)

        //this send button text is tengible to change, if the student has matched with the coach, it is a rating button,
        //else, it is send request
        sendbutton.setOnClickListener {
            sendbutton.text = "Rate"
            val rateintent = Intent(this, rating::class.java)
            startActivity(rateintent)

        }

        dumbbellhome.setOnClickListener {
            val studentintent = Intent(this, studentprofile::class.java)
            startActivity(studentintent)
            overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);
        }
    }


}
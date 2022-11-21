package com.example.coachme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class Coach_reg4 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coach_reg4)

        val return_home = findViewById<Button>(R.id.return_home)
        return_home.setOnClickListener(View.OnClickListener() {
            val intent = Intent(this, Sign_in::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        })
    }
}
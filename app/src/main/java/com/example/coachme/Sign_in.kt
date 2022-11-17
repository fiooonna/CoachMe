package com.example.comp3330project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity

class Sign_in : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        var back = findViewById<ImageButton>(R.id.back_btn)
        back.setOnClickListener(View.OnClickListener() {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        })

        var sign_in: Button = findViewById(R.id.button5)
        sign_in.setOnClickListener {
            val intent = Intent(this, main_coach::class.java)
            startActivity(intent)
        }

        var sign_up_now: Button = findViewById(R.id.button6)
        sign_up_now.setOnClickListener {
            val intent = Intent(this, Sign_up::class.java)
            startActivity(intent)
        }
    }


}

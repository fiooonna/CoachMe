package com.example.comp3330project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)

        var sign_in: Button = findViewById(R.id.button)
        sign_in.setOnClickListener {
            val intent = Intent(this, Sign_in::class.java)
            startActivity(intent)
        }

        var sign_up: Button = findViewById(R.id.button2)
        sign_up.setOnClickListener {
            val intent = Intent(this, Sign_up::class.java)
            startActivity(intent)
        }

        var sign_up_now: Button = findViewById(R.id.button6)
        sign_up_now.setOnClickListener {
            val intent = Intent(this, Sign_up::class.java)
            startActivity(intent)
        }

        var news = findViewById<ImageButton>(R.id.news)
        news.setOnClickListener(View.OnClickListener() {
            val intent = Intent(this, News::class.java)
            startActivity(intent)
        })

        var partners = findViewById<ImageButton>(R.id.partners)
        partners.setOnClickListener(View.OnClickListener() {
            val intent = Intent(this, Partners::class.java)
            startActivity(intent)
        })

        var about = findViewById<ImageButton>(R.id.about)
        about.setOnClickListener(View.OnClickListener() {
            val intent = Intent(this, About::class.java)
            startActivity(intent)
        })
    }

}
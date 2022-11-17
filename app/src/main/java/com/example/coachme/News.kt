package com.example.coachme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton

class News : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        var back = findViewById<ImageButton>(R.id.back_btn)
        back.setOnClickListener(View.OnClickListener() {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        })

        var news1 = findViewById<ImageButton>(R.id.news1)
        news1.setOnClickListener(View.OnClickListener() {
            val intent = Intent(this, News1::class.java)
            startActivity(intent)
        })
    }
}
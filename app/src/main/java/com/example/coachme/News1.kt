package com.example.comp3330project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton

class News1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news1)

        var back = findViewById<ImageButton>(R.id.back_btn)
        back.setOnClickListener(View.OnClickListener() {
            val intent = Intent(this, News::class.java)
            startActivity(intent)
        })
    }
}
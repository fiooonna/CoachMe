package com.example.coachme

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton

class Partners : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_partners)

        var back = findViewById<ImageButton>(R.id.back_btn)
        back.setOnClickListener(View.OnClickListener() {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        })

        var p1 = findViewById<ImageButton>(R.id.partner1)
        p1.setOnClickListener(View.OnClickListener() {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://247.fitness/en/"))
            startActivity(intent)
        })

        var p2 = findViewById<ImageButton>(R.id.partner2)
        p2.setOnClickListener(View.OnClickListener() {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.anytimefitness.hk/"))
            startActivity(intent)
        })

        var p3 = findViewById<ImageButton>(R.id.partner3)
        p3.setOnClickListener(View.OnClickListener() {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.pure-fitness.com/"))
            startActivity(intent)
        })

    }
}
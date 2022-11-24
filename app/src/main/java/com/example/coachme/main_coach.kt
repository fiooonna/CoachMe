package com.example.coachme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView

class main_coach : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_coach)

        var intent: Intent = getIntent()
        val email: String? = intent.getStringExtra("email")
        val pw: String? = intent.getStringExtra("pw")
        val id: String? = intent.getStringExtra("id")
        val first_name: String? = intent.getStringExtra("first_name")
        val last_name: String? = intent.getStringExtra("last_name")
        val username: String? = intent.getStringExtra("username")
        val address: String? = intent.getStringExtra("address")
        val gender: String? = intent.getStringExtra("gender")
        val age: String? = intent.getStringExtra("age")
        val exp: String? = intent.getStringExtra("exp")
        val expertise: String? = intent.getStringExtra("expertise")
        val intro: String? = intent.getStringExtra("intro")
        val qua: String? = intent.getStringExtra("qua")

        var hello_name = findViewById<TextView>(R.id.hello_name)
        hello_name.text = "Hello! $first_name"
        var name = findViewById<Button>(R.id.name)
        name.text = "$first_name $last_name"

        var button_find_student = findViewById<ImageButton>(R.id.button_find_students)
        button_find_student.setOnClickListener(View.OnClickListener() {
            val intent =  Intent(this, studentpoolActivity::class.java)
            intent.putExtra("username", username)
            intent.putExtra("first_name", first_name)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        })
    }

}
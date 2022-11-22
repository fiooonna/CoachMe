package com.example.coachme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView

class main_student : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_student)

//        email=$email&pw=$pw&ids=$id&first_name=$first_name&last_name=$last_name&username=$username&address=$address&gender=$gender&age=$age&
//        exp=$exp&target=$target&numperweek=$numperweek&min_pay=$min_pay&max_pay=$max_pay&remarks=$remarks
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
        val target: String? = intent.getStringExtra("target")
        val numperweek: String? = intent.getStringExtra("numperweek")
        val min_pay: String? = intent.getStringExtra("min_pay")
        val max_pay: String? = intent.getStringExtra("max_pay")
        val remarks: String? = intent.getStringExtra("remarks")

        var hello_name = findViewById<TextView>(R.id.hello_name)
        hello_name.setText("Hello! $username")
        var name = findViewById<Button>(R.id.name)
        name.setText("$username")

        var button_find_coaches = findViewById<ImageButton>(R.id.button_find_coaches)
        button_find_coaches.setOnClickListener(View.OnClickListener() {
            val intent =  Intent(this, CoachPoolActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        })
    }
}
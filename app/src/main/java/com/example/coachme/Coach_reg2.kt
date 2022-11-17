package com.example.comp3330project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import com.google.android.material.slider.Slider

class Coach_reg2 : AppCompatActivity() {
    private var intro: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coach_reg2)

        var intent: Intent = getIntent()
        val email: String? = intent.getStringExtra("email")
        val pw: String? = intent.getStringExtra("pw")
        val firm_pw: String? = intent.getStringExtra("firm_pw")
        val id: String? = intent.getStringExtra("id")
        val first_name: String? = intent.getStringExtra("first_name")
        val last_name: String? = intent.getStringExtra("last_name")
        val username: String? = intent.getStringExtra("username")
        val address: String? = intent.getStringExtra("address")
        val gender: String? = intent.getStringExtra("gender")
        val age: String? = intent.getStringExtra("age")
        val exp: String? = intent.getStringExtra("exp")
        val expertise: String? = intent.getStringExtra("expertise")

        var back = findViewById<ImageButton>(R.id.back_btn)
        back.setOnClickListener(View.OnClickListener() {
            val intent = Intent(this, Coach_reg1::class.java)
            startActivity(intent)
        })

        var cont = findViewById<Button>(R.id.contd)
        cont.setOnClickListener(View.OnClickListener() {
            intro = findViewById<EditText>(R.id.editTextTextMultiLine2)!!.text.toString()

            if (intro!!.isNotEmpty()) {
                var intent = Intent(this, Coach_reg3::class.java)
                intent.putExtra("email", email)
                intent.putExtra("pw", pw)
                intent.putExtra("firm_pw", firm_pw)
                intent.putExtra("id", id)
                intent.putExtra("first_name", first_name)
                intent.putExtra("last_name", last_name)
                intent.putExtra("username", username)
                intent.putExtra("address", address)
                intent.putExtra("gender", gender)
                intent.putExtra("age", age)
                intent.putExtra("exp", exp)
                intent.putExtra("expertise", expertise)
                intent.putExtra("intro", intro)
                startActivity(intent)
            } else {
                Toast.makeText(this@Coach_reg2, "Something is not completed. Please check", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
package com.example.comp3330project

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import com.google.android.material.slider.Slider

class Coach_reg1 : AppCompatActivity() {
    private var gender: String? = null
    private var age: String? = null
    private var exp: String? = null
    private var expertise: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coach_reg1)

        var intent: Intent = getIntent()
        val email: String? = intent.getStringExtra("email")
        val pw: String? = intent.getStringExtra("pw")
        val firm_pw: String? = intent.getStringExtra("firm_pw")
        val id: String? = intent.getStringExtra("id")
        val first_name: String? = intent.getStringExtra("first_name")
        val last_name: String? = intent.getStringExtra("last_name")
        val username: String? = intent.getStringExtra("username")
        val address: String? = intent.getStringExtra("address")

        var back = findViewById<ImageButton>(R.id.back_btn)
        back.setOnClickListener(View.OnClickListener() {
            val intent = Intent(this, General_reg::class.java)
            startActivity(intent)
        })

        var male: Button = findViewById(R.id.male)
        var female: Button = findViewById(R.id.female)
        male.setOnClickListener {
            gender = "male"
            male.setBackgroundResource(R.drawable.male_female_selected)
            male.setTextColor(Color.parseColor("#ffffff"))
            female.setBackgroundResource(R.drawable.male_female)
            female.setTextColor(Color.parseColor("#000000"))
        }
        female.setOnClickListener {
            gender = "female"
            female.setBackgroundResource(R.drawable.male_female_selected)
            female.setTextColor(Color.parseColor("#ffffff"))
            male.setBackgroundResource(R.drawable.male_female)
            male.setTextColor(Color.parseColor("#000000"))
        }

        var con: Button = findViewById(R.id.contd)
        con.setOnClickListener(View.OnClickListener() {
            age = findViewById<Slider>(R.id.AgeInput).value.toString()
            exp = findViewById<EditText>(R.id.ExperienceInput)!!.text.toString()
            expertise = findViewById<EditText>(R.id.ExpertiseInput)!!.text.toString()

            if (gender!!.isNotEmpty() && age!!.isNotEmpty() && exp!!.isNotEmpty() && expertise!!.isNotEmpty()) {
                var intent = Intent(this, Coach_reg2::class.java)
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
                startActivity(intent)
            } else {
                Toast.makeText(this@Coach_reg1, "Something is not completed. Please check", Toast.LENGTH_SHORT).show()
            }
        })

    }
}
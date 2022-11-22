package com.example.coachme

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.google.android.material.slider.Slider
import com.jaygoo.widget.RangeSeekBar

class Student_reg1 : AppCompatActivity() {
    private var gender: String = ""
    private var age: String? = null
    private var exp: String? = null
    private var target: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_reg1)


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

        var male: Button = findViewById(R.id.MaleButton)
        var female: Button = findViewById(R.id.FemaleButton)

        male.setOnClickListener {
            gender = "male"
            male.setBackgroundResource(R.drawable.radio_selected)
            male.setTextColor(Color.parseColor("#ffffff"))
            female.setBackgroundResource(R.drawable.radio_selector)
            female.setTextColor(Color.parseColor("#000000"))
        }
        female.setOnClickListener {
            gender = "female"
            female.setBackgroundResource(R.drawable.radio_selected)
            female.setTextColor(Color.parseColor("#ffffff"))
            male.setBackgroundResource(R.drawable.radio_selector)
            male.setTextColor(Color.parseColor("#000000"))
        }

        // var exp_seekbar = findViewById<RangeSeekBar>(R.id.range_seekbar_experience)

        val spinner = findViewById<Spinner>(R.id.TargetInput)
        val TargetInput = findViewById<TextView>(R.id.Target)
        val options = arrayListOf("Toning", "Endurance", "Mobility", "Strength")
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, options)

        spinner.adapter = arrayAdapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                TargetInput.text = "Target selected:  ${options.get(p2).toString()}"
                target = options.get(p2).toString()
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TargetInput.text = "Please select address"
                target = null
            }
        }

        var con: Button = findViewById(R.id.contd)

        con.setOnClickListener(View.OnClickListener() {
            age = findViewById<Slider>(R.id.AgeInput).value.toInt().toString()
            val ExpseekBar = findViewById<RangeSeekBar>(R.id.range_seekbar_experience)
            val exp_int = ExpseekBar.leftSeekBar.progress.toInt()

            var exp_array = arrayOf("Beginner","Intermediate","advanced")
            exp = exp_array.elementAt(exp_int).toString()
            Log.d("LOG VALUE", exp!!)

            var intent = Intent(this, Student_reg2::class.java)
            if (gender!!.isNotEmpty() && age!!.isNotEmpty() && exp!!.isNotEmpty() && target!!.isNotEmpty()) {
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
                intent.putExtra("target", target)

                //Toast.makeText(this@Student_reg1, "$gender$age$exp$target", Toast.LENGTH_SHORT).show()
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right,
                    R.anim.slide_out_left);
            } else {
                Toast.makeText(this@Student_reg1, "Something is not completed. Please check", Toast.LENGTH_SHORT).show()
            }
        })

    }
}
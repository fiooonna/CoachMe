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

class Sign_up : AppCompatActivity() {
    private var email: String? = null
    private var pw: String? = null
    private var firm_pw: String? = null
    private var id: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        var student: Button = findViewById(R.id.student)
        var trainer: Button = findViewById(R.id.trainer)
        student.setOnClickListener {
            id = "student"
            student.setBackgroundResource(R.drawable.student_trainer_selected)
            student.setTextColor(Color.parseColor("#ffffff"))
            trainer.setBackgroundResource(R.drawable.student_trainer)
            trainer.setTextColor(Color.parseColor("#000000"))
        }
        trainer.setOnClickListener {
            id = "trainer"
            trainer.setBackgroundResource(R.drawable.student_trainer_selected)
            trainer.setTextColor(Color.parseColor("#ffffff"))
            student.setBackgroundResource(R.drawable.student_trainer)
            student.setTextColor(Color.parseColor("#000000"))
        }

        var back = findViewById<ImageButton>(R.id.back_btn)
        back.setOnClickListener(View.OnClickListener() {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        })

        var sign_in: Button = findViewById(R.id.button6)
        sign_in.setOnClickListener {
            val intent = Intent(this, Sign_in::class.java)
            startActivity(intent)
        }

        var con: Button = findViewById(R.id.sign_up)
        con.setOnClickListener(View.OnClickListener() {
            email = findViewById<EditText>(R.id.textView)!!.text.toString()
            pw = findViewById<EditText>(R.id.textView2)!!.text.toString()
            firm_pw = findViewById<EditText>(R.id.textView3)!!.text.toString()

            val intent = Intent(this, General_reg::class.java)
            if (email!!.isNotEmpty() && id!!.isNotEmpty() && email!!.contains("@")) {
                if (pw.equals(firm_pw) && pw!!.length >= 8 && firm_pw!!.length>=8) {
                    intent.putExtra("email", email)
                    intent.putExtra("pw", pw)
                    intent.putExtra("firm_pw", firm_pw)
                    intent.putExtra("id", id)
                    startActivity(intent)
                } else{
                    Toast.makeText(this@Sign_up, "Password didn't match or is less than 8 letters. Please check.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this@Sign_up, "Something is not completed or the email is not in a correct format. Please check", Toast.LENGTH_SHORT).show()
            }
        })
    }

}
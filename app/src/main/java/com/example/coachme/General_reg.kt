package com.example.comp3330project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast

class General_reg : AppCompatActivity() {
    private var first_name: String? = null
    private var last_name: String? = null
    private var username: String? = null
    private var address: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_general_reg)

        var intent: Intent = getIntent()
        val email: String? = intent.getStringExtra("email")
        val pw: String? = intent.getStringExtra("pw")
        val firm_pw: String? = intent.getStringExtra("firm_pw")
        val id: String? = intent.getStringExtra("id")

        var back = findViewById<ImageButton>(R.id.back_btn)
        back.setOnClickListener(View.OnClickListener() {
            val intent = Intent(this, Sign_up::class.java)
            startActivity(intent)
        })

        var con: Button = findViewById(R.id.contd)
        con.setOnClickListener(View.OnClickListener() {
            first_name = findViewById<EditText>(R.id.FirstNameInput)!!.text.toString()
            last_name = findViewById<EditText>(R.id.LastNameInput)!!.text.toString()
            username = findViewById<EditText>(R.id.UsernameInput)!!.text.toString()
            address = findViewById<EditText>(R.id.AddressInput)!!.text.toString()

            if (first_name!!.isNotEmpty() && last_name!!.isNotEmpty() && username!!.isNotEmpty() && address!!.isNotEmpty()) {
                if (id.equals("trainer")) {
                    var intent = Intent(this, Coach_reg1::class.java)
                    intent.putExtra("email", email)
                    intent.putExtra("pw", pw)
                    intent.putExtra("firm_pw", firm_pw)
                    intent.putExtra("id", id)
                    intent.putExtra("first_name", first_name)
                    intent.putExtra("last_name", last_name)
                    intent.putExtra("username", username)
                    intent.putExtra("address", address)
                    startActivity(intent)
                } /*else if (id.equals("student") {
                    val intent = Intent(this, Student_reg1::class.java)
                    intent.putExtra("email", email)
                    intent.putExtra("pw", pw)
                    intent.putExtra("firm_pw", firm_pw)
                    intent.putExtra("id", id)
                    intent.putExtra("first_name", first_name)
                    intent.putExtra("last_name", last_name)
                    intent.putExtra("username", username)
                    intent.putExtra("address", address)
                    startActivity(intent)
                }*/
                else {
                    Toast.makeText(this@General_reg, "Unexpected error occured", Toast.LENGTH_SHORT).show()
                    System.exit(0);
                }
            } else {
                Toast.makeText(this@General_reg, "Something is not completed. Please check", Toast.LENGTH_SHORT).show()
            }
        })

    }
}
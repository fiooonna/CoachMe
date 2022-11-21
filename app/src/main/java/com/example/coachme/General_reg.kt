package com.example.coachme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*

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
            overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);
        })

        val spinner = findViewById<Spinner>(R.id.AddressInput)
        val addressInput = findViewById<TextView>(R.id.Address)
        val options = arrayOf("Kowloon", "HK Island", "New Territories")
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, options)

        spinner.adapter = arrayAdapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                addressInput.text = "Address selected:  ${options.get(p2).toString()}"
                address = options.get(p2).toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                addressInput.text = "Please select address"
            }

        }

        var con: Button = findViewById(R.id.contd)
        con.setOnClickListener(View.OnClickListener() {
            first_name = findViewById<EditText>(R.id.FirstNameInput)!!.text.toString()
            last_name = findViewById<EditText>(R.id.LastNameInput)!!.text.toString()
            username = findViewById<EditText>(R.id.UsernameInput)!!.text.toString()

            if (first_name!!.isNotEmpty() && last_name!!.isNotEmpty() && username!!.isNotEmpty() && address!!.isNotEmpty()) {
                if (id.equals("trainer")) {
                    val intent = Intent(this, Coach_reg1::class.java)
                    intent.putExtra("email", email)
                    intent.putExtra("pw", pw)
                    intent.putExtra("firm_pw", firm_pw)
                    intent.putExtra("id", id)
                    intent.putExtra("first_name", first_name)
                    intent.putExtra("last_name", last_name)
                    intent.putExtra("username", username)
                    intent.putExtra("address", address)
                    startActivity(intent)
                    overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                } else if (id.equals("student")) {
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
                    overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                } else {
                    Toast.makeText(this@General_reg, "Unexpected error occured", Toast.LENGTH_SHORT).show()
                    System.exit(0);
                }
            } else {
                Toast.makeText(this@General_reg, "Something is not completed. Please check", Toast.LENGTH_SHORT).show()
            }
        })

    }
}
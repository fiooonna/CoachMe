package com.example.coachme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.jaygoo.widget.RangeSeekBar

class Student_reg2 : AppCompatActivity() {
    private var numperweek: String? = null
    private lateinit var expect_pay: IntArray
    private var remarks: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_reg2)

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
        val target: String? = intent.getStringExtra("target")

        var back = findViewById<ImageButton>(R.id.back_btn)
        back.setOnClickListener(View.OnClickListener() {
            val intent = Intent(this, Student_reg1::class.java)
            startActivity(intent)
        })

        val spinner = findViewById<Spinner>(R.id.weekInput)
        val options = arrayListOf("1", "2", "3", "4", "5 or more")
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, options)

        spinner.adapter = arrayAdapter

        /*spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                numperweek = options.get(p2).toString()
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                numperweek = null
            }
        }*/
        numperweek = "1"

        val con = findViewById<Button>(R.id.contd)
        con.setOnClickListener(View.OnClickListener() {
            remarks = findViewById<EditText>(R.id.RemarkText)!!.text.toString()
            /*var min_pay = findViewById<RangeSeekBar>(R.id.range_seekbar_pay)!!.?.toInt()
            var max_pay = findViewById<RangeSeekBar>(R.id.range_seekbar_pay)!!.?.toInt()
            expect_pay = intArrayOf(min_pay, max_pay)*/
            expect_pay = intArrayOf(0, 200)

            var intent = Intent(this, Student_reg3::class.java)
            sendInfo("http://10.0.2.2:5000/project?email=$email&pw=$pw&ids=$id&first_name=$first_name&last_name=$last_name&username=$username&address=$address&gender=$gender&age=$age&exp=$exp&target=$target&numperweek=$numperweek&expect_pay=$expect_pay&remarks=$remarks")
            /*sendInfo("http://192.168.31.127:5000/project?email=$email&pw=$pw&ids=$id&first_name=$first_name&last_name=$last_name&username=$username&address=$address&gender=$gender&age=$age&exp=$exp&target=$target&numperweek=$numperweek&expect_pay=$expect_pay&remarks=$remarks")*/

            Toast.makeText(this@Student_reg2, "$numperweek$expect_pay$remarks", Toast.LENGTH_LONG).show()
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left
            );
        })
    }

    fun sendInfo(url: String) {
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                println("added")
            },
            Response.ErrorListener { error ->
                Log.e("MyActivity",error.toString())
            }
        )
        Volley.newRequestQueue(this).add(jsonObjectRequest)
    }

}
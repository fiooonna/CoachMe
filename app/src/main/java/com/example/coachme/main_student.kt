package com.example.coachme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray

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
        var student_id: Int = -1
        var user_id: Int = -1
        val url:String = "http://10.0.2.2:5000/get_student?username=$username"
        /*val url:String = "http://192.168.31.127:5000/project"*/
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->

                val usernames: JSONArray = response.get("username") as JSONArray
                val student_ids: JSONArray = response.get("student_id") as JSONArray
                val user_ids: JSONArray = response.get("user_id") as JSONArray
                student_id = student_ids.get(0).toString().toInt()
                user_id = user_ids.get(0).toString().toInt()
                Log.d("student_id", student_id.toString())
                Log.d("user_id", user_id.toString())

            },
            Response.ErrorListener { error ->
                Log.e("MyActivity",error.toString())
            }
        )
        Volley.newRequestQueue(this).add(jsonObjectRequest)

        var hello_name = findViewById<TextView>(R.id.hello_name)
        hello_name.text = "Hello! $first_name"
        var name = findViewById<Button>(R.id.name)
        name.text = "$first_name $last_name"

        var button_find_coaches = findViewById<ImageButton>(R.id.button_find_coaches)
        button_find_coaches.setOnClickListener(View.OnClickListener() {
            val intent =  Intent(this, CoachPoolActivity::class.java)
            intent.putExtra("user_id", user_id)
            intent.putExtra("student_id", student_id)
            intent.putExtra("username", username)
            intent.putExtra("first_name", first_name)

            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        })
    }
}
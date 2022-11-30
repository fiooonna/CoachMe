package com.example.coachme

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.coachme.Coach_reg3.Companion.FLASK_URL
import org.json.JSONArray


class studentprofile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_studentprofile)
        val coach_user_id: Int? = intent.getIntExtra("coach_user_id", 0)
        val coach_first_name: Int? = intent.getIntExtra("coach_first_name", 0)
        val student_user_id: Int? = intent.getIntExtra("student_user_id", 0)
        val student_id: Int? = intent.getIntExtra("student_id", 0)
        val coach_id: Int? = intent.getIntExtra("coach_id", 0)
        val student_name: String? = intent.getStringExtra("student_name")
        val coach_username: String? = intent.getStringExtra("coach_username")

        val address: String? = intent.getStringExtra("address")
        val gender: String? = intent.getStringExtra("gender")
        val age: String? = intent.getStringExtra("age")
        val exp: String? = intent.getStringExtra("exp")
        val target: String? = intent.getStringExtra("target")
        var current_student: Student

        val dumbbellhome = findViewById<ImageButton>(R.id.dumbbell_button)
        val sendbutton = findViewById<Button>(R.id.send_button)


        dumbbellhome.setOnClickListener {
            val coachintent = Intent(this, coachprofile::class.java)
            startActivity(coachintent)
            overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        }

        sendbutton.setOnClickListener{
            /* val intent = Intent(this, studentpoolActivity::class.java)
            startActivity(intent) */

            var match_id: Int = 0

            val url:String = Coach_reg3.FLASK_URL +"matched"
            val jsonObjectRequest = JsonObjectRequest(
                Request.Method.GET, url, null,
                { response ->
                    val match_ids: JSONArray = response.get("match_id") as JSONArray
                    match_id = match_ids.get(match_ids.length() - 1).toString().toInt() + 1
                },
                { error ->
                    Log.e("MyActivity",error.toString())
                }
            )
            Volley.newRequestQueue(this).add(jsonObjectRequest)

            var Matched: Int = 0
            var Invited: Int = -1
            var rating: String = "/"
            sendInfo(FLASK_URL +"match?match_id=$match_id&student_id=$student_id&coach_id=$coach_id&Matched=$Matched&Invited=$Invited&Rating=$rating")

            Toast.makeText(getApplicationContext(),"Great! You have sent the Request", Toast.LENGTH_SHORT).show();
            overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        }
    }

    fun sendInfo(url: String) {
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                println("send request")
            },
            { error ->
                Log.e("MyActivity",error.toString())
            }
        )
        Volley.newRequestQueue(this).add(jsonObjectRequest)
    }
}

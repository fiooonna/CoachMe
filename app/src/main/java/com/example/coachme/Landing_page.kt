package com.example.coachme

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley


class Landing_page : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)

        var sign_in: Button = findViewById(R.id.button)
        sign_in.setOnClickListener {
            val intent = Intent(this, Sign_in::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        }

        var sign_up: Button = findViewById(R.id.button2)
        sign_up.setOnClickListener {
            val intent = Intent(this, Sign_up::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        }

        var sign_up_now: Button = findViewById(R.id.button6)
        sign_up_now.setOnClickListener {
            val intent = Intent(this, Sign_up::class.java)
            startActivity(intent)
        }

        var news = findViewById<ImageButton>(R.id.news)
        news.setOnClickListener(View.OnClickListener() {
            val intent = Intent(this, News::class.java)

            /*val email: String? = "2@"
            val pw: String? = "12345678"
            val firm_pw: String? = "12345678"
            val id: String? = "124"
            val first_name: String? = "Kwok"
            val last_name: String? = "Ivan"
            val username: String? = "user"
            val address: String? = "address"
            val gender: String? = "gender"
            val age: String? = "12"
            val exp: String? = "122"
            val expertise: String? = "exp"
            val intro: String? = "intro"
            val qua: String? = "qrqfw"
            sendInfo("http://10.0.2.2:5000/project?email=$email&pw=$pw&id=$id&first_name=$first_name&last_name=$last_name&username=$username&address=$address&gender=$gender&age=$age&exp=$exp&expertise=$expertise&intro=$intro&qua=$qua")
            sendInfo("http://192.168.31.127:5000/project?email=$email&pw=$pw&id=$id&first_name=$first_name&last_name=$last_name&username=$username&address=$address&gender=$gender&age=$age&exp=$exp&expertise=$expertise&intro=$intro&qua=$qua")
            */startActivity(intent)
        })

        var partners = findViewById<ImageButton>(R.id.partners)
        partners.setOnClickListener(View.OnClickListener() {
            val intent = Intent(this, Partners::class.java)
            startActivity(intent)
        })

        var about = findViewById<ImageButton>(R.id.about)
        about.setOnClickListener(View.OnClickListener() {
            val intent = Intent(this, About::class.java)
            startActivity(intent)
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
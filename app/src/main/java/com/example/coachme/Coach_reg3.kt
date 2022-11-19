package com.example.coachme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject

class Coach_reg3 : AppCompatActivity() {
    private var qua: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coach_reg3)

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
        val intro: String? = intent.getStringExtra("intro")

        val back = findViewById<ImageButton>(R.id.back_btn)
        back.setOnClickListener(View.OnClickListener() {
            val intent = Intent(this, Coach_reg2::class.java)
            startActivity(intent)
        })

        val cont = findViewById<Button>(R.id.contd)
        cont.setOnClickListener(View.OnClickListener() {
            qua = findViewById<EditText>(R.id.SelectQualification)!!.text.toString()

            if (qua!!.isNotEmpty() ) {
                var intent = Intent(this, Coach_reg4::class.
                java)
                sendInfo("http://10.0.2.2:5000/project?email=$email&pw=$pw&id=$id&first_name=$first_name&last_name=$last_name&username=$username&address=$address&gender=$gender&age=$age&exp=$exp&expertise=$expertise&intro=$intro&qua=$qua")

                startActivity(intent)
            } else {
                Toast.makeText(this@Coach_reg3, "Something is not completed. Please check", Toast.LENGTH_SHORT).show()
            }
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
package com.example.comp3330project

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
            qua = findViewById<EditText>(R.id.editTextTextMultiLine2)!!.text.toString()

            if (qua!!.isNotEmpty() ) {
                var intent = Intent(this, Coach_reg4::class.java)

                sendInfo("http://192.168.31.127:5000/project?email=$email")
                sendInfo("http://192.168.31.127:5000/project?pw=$pw")
                sendInfo("http://192.168.31.127:5000/project?id=$id")
                sendInfo("http://192.168.31.127:5000/project?first_name=$first_name")
                sendInfo("http://192.168.31.127:5000/project?last_name=$last_name")
                sendInfo("http://192.168.31.127:5000/project?username=$username")
                sendInfo("http://192.168.31.127:5000/project?address=$address")
                sendInfo("http://192.168.31.127:5000/project?gender=$gender")
                sendInfo("http://192.168.31.127:5000/project?age=$age")
                sendInfo("http://192.168.31.127:5000/project?exp=$exp")
                sendInfo("http://192.168.31.127:5000/project?expertise=$expertise")
                sendInfo("http://192.168.31.127:5000/project?intro=$intro")
                sendInfo("http://192.168.31.127:5000/project?qua=$qua")

                /*startActivity(intent)*/
            } else {
                Toast.makeText(this@Coach_reg3, "Something is not completed. Please check", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun sendInfo(url: String) {
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                switchActivity(response)
            },
            Response.ErrorListener { error ->
                Log.e("MyActivity",error.toString())
            }
        )
        Volley.newRequestQueue(this).add(jsonObjectRequest)
    }

    fun switchActivity(jsonObj: JSONObject){
        val users: JSONArray = jsonObj.getJSONArray("Users")

        val studentList = arrayListOf<String>()
        for (i in 0..users.length() - 1){
            val `object`: JSONObject = users.getJSONObject(i)
            val email = `object`.getString("email")
            val pw = `object`.getString("pw")
            val id = `object`.getString("id")
            val first_name = `object`.getString("first_name")
            val last_name = `object`.getString("last_name")
            val username = `object`.getString("username")
            val address = `object`.getString("address")
            val gender = `object`.getString("gender")
            val age = `object`.getString("age")
            val exp = `object`.getString("exp")
            val expertise = `object`.getString("expertise")
            val intro = `object`.getString("intro")
            val qua = `object`.getString("qua")
            println(email)
            println(pw)
            println(id)
            println(intro)
            println(qua)
//            studentList.add(users.get(i) as String)
        }
    }

}
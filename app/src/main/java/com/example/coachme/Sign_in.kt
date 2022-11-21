package com.example.coachme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject

class Sign_in : AppCompatActivity() {
    private lateinit var username: String
    private lateinit var pw: String
    private var human: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        var back = findViewById<ImageButton>(R.id.back_btn)
        back.setOnClickListener(View.OnClickListener() {
            val intent = Intent(this, Landing_page::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);
        })

        var sign_in: Button = findViewById(R.id.button5)
        sign_in.setOnClickListener {
            username = findViewById<EditText>(R.id.Username)!!.text.toString()
            pw = findViewById<EditText>(R.id.Password)!!.text.toString()
            human = findViewById<CheckBox>(R.id.checkBox)!!.isChecked().toString()

            val url:String = "http://10.0.2.2:5000/project"
            /*val url:String = "http://192.168.31.127:5000/project"*/
            val jsonObjectRequest = JsonObjectRequest(
                Request.Method.GET, url, null,
                Response.Listener { response ->
                    if (human.equals("true")) {
                        switchActivity(response, username, pw)
                    } else {
                        Toast.makeText(this@Sign_in, "Please confirm that you are not a robot.", Toast.LENGTH_SHORT).show()
                    }
                },
                Response.ErrorListener { error ->
                    Log.e("MyActivity",error.toString())
                }
            )
            Volley.newRequestQueue(this).add(jsonObjectRequest)

            overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        }

        var sign_up_now: Button = findViewById(R.id.button6)
        sign_up_now.setOnClickListener {
            val intent = Intent(this, Sign_up::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        }
    }

    fun switchActivity(jsonObj: JSONObject, username: String, pw: String?){
        val user_ids: JSONArray = jsonObj.get("user_id") as JSONArray
        val usernames: JSONArray = jsonObj.get("username") as JSONArray
        val pws: JSONArray = jsonObj.get("pw") as JSONArray
        val email: JSONArray = jsonObj.get("email") as JSONArray
        val id: JSONArray = jsonObj.get("id") as JSONArray
        val first_name: JSONArray = jsonObj.get("first_name") as JSONArray
        val last_name: JSONArray = jsonObj.get("last_name") as JSONArray
        val address: JSONArray = jsonObj.get("address") as JSONArray
        val gender: JSONArray = jsonObj.get("gender") as JSONArray
        val age: JSONArray = jsonObj.get("age") as JSONArray
        val exp: JSONArray = jsonObj.get("exp") as JSONArray
        val expertise: JSONArray = jsonObj.get("expertise") as JSONArray
        val intro: JSONArray = jsonObj.get("intro") as JSONArray
        val qua: JSONArray = jsonObj.get("qua") as JSONArray

        for (i in 0..usernames.length() - 1){
            Log.d("username", usernames.get(i).toString())
            Log.d("pw", pws.get(i).toString())
            if (usernames.get(i).toString().equals(username) && pws.get(i).toString().equals(pw)) {
                if (id.get(i).toString().equals("trainer")) {
                    val intent = Intent(this, main_coach::class.java)
                    intent.putExtra("username", username)
                    intent.putExtra("pw", pw)
                    intent.putExtra("email", email.get(i).toString())
                    intent.putExtra("id", id.get(i).toString())
                    intent.putExtra("first_name", first_name.get(i).toString())
                    intent.putExtra("last_name", last_name.get(i).toString())
                    intent.putExtra("address", address.get(i).toString())
                    intent.putExtra("gender", gender.get(i).toString())
                    intent.putExtra("age", age.get(i).toString())
                    intent.putExtra("exp", exp.get(i).toString())
                    intent.putExtra("expertise", expertise.get(i).toString())
                    intent.putExtra("intro", intro.get(i).toString())
                    intent.putExtra("qua", qua.get(i).toString())

                    startActivity(intent)
                }
                else if (id.get(i).toString().equals("student")) {
                    val intent = Intent(this, main_student::class.java)
                    intent.putExtra("username", username)
                    intent.putExtra("pw", pw)
                    intent.putExtra("email", email.get(i).toString())
                    intent.putExtra("id", id.get(i).toString())
                    intent.putExtra("first_name", first_name.get(i).toString())
                    intent.putExtra("last_name", last_name.get(i).toString())
                    intent.putExtra("address", address.get(i).toString())
                    intent.putExtra("gender", gender.get(i).toString())
                    intent.putExtra("age", age.get(i).toString())
                    intent.putExtra("exp", exp.get(i).toString())
                    intent.putExtra("expertise", expertise.get(i).toString())
                    intent.putExtra("intro", intro.get(i).toString())
                    intent.putExtra("qua", qua.get(i).toString())

                    startActivity(intent)
                }

                else {
                    Toast.makeText(this@Sign_in, "The account is neither student or coach.", Toast.LENGTH_SHORT).show()
                    return
                }

                return
            }
        }
        Toast.makeText(this@Sign_in, "Username or password incorrect.", Toast.LENGTH_SHORT).show()
    }

}

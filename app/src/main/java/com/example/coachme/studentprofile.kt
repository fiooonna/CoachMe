package com.example.coachme

import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.coachme.Coach_reg3.Companion.FLASK_URL
import de.hdodenhof.circleimageview.CircleImageView
import org.json.JSONArray
import org.w3c.dom.Text


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
        val age:Int = intent.getIntExtra("age", 0)
        val exp: String? = intent.getStringExtra("exp")
        val target: String? = intent.getStringExtra("target")
        val pay: String? = intent.getStringExtra("pay")
        val num_lesson: String? = intent.getStringExtra("num_lesson")
        val remark: String? = intent.getStringExtra("remark")
        var current_student: Student

        val name_text = findViewById<TextView>(R.id.name_text)
        val gender_view = findViewById<ImageView>(R.id.gender)
        val goal_text = findViewById<TextView>(R.id.goal_text)
        val pay_text = findViewById<TextView>(R.id.pay_text)
        val age_text = findViewById<TextView>(R.id.age_text)
        val exp_text = findViewById<TextView>(R.id.exp_text)
        val num_lesson_text = findViewById<TextView>(R.id.num_lesson_text)
        val remark_text = findViewById<TextView>(R.id.remark_text)
        val profile_image = findViewById<CircleImageView>(R.id.profile_image)
        val location_text = findViewById<TextView>(R.id.location_text)

        name_text.text = student_name
        location_text.text = address
        //Todo: Dynamic student image
        profile_image.setImageResource(R.drawable.student2)
        if (gender == "male") {
            gender_view.setImageResource(R.drawable.male)
        } else if (gender == "female"){
            gender_view.setImageResource(R.drawable.female)
        }
        goal_text.text = target
        pay_text.text = pay
        num_lesson_text.text = num_lesson
        exp_text.text = exp
        remark_text.text = remark
        age_text.text = age.toString()

        val dumbbellhome = findViewById<ImageButton>(R.id.dumbbell_button)
        val sendbutton = findViewById<Button>(R.id.send_button)


        dumbbellhome.setOnClickListener {
        }

        sendbutton.setOnClickListener{
            /* val intent = Intent(this, StudentPoolActivity::class.java)
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

            sendbutton.setText("Invitation sent")
            sendbutton.isEnabled = false

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

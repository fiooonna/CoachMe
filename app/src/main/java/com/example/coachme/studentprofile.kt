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

    private var mathced = 0
    //invited means whether the coach received invitation from this student
    private var invited = 0

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
        checkMatched(FLASK_URL+"get_matched", student_id!!.toInt(), coach_id!!.toInt())
        val dumbbellhome = findViewById<ImageButton>(R.id.dumbbell_button)
        val sendbutton = findViewById<Button>(R.id.send_button)


        dumbbellhome.setOnClickListener {
        }
        var url = FLASK_URL + "get_matched"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                println("send request")
                val match_ids: JSONArray = response.get("match_id") as JSONArray
                val student_ids: JSONArray = response.get("student_id") as JSONArray
                val coach_ids: JSONArray = response.get("coach_id") as JSONArray
                val matcheds: JSONArray = response.get("matched") as JSONArray
                val Inviteds: JSONArray = response.get("Invited") as JSONArray
                val Ratings: JSONArray = response.get("Rating") as JSONArray

                for (i in 0 .. match_ids.length() - 1) {
                    if (student_id == student_ids.get(i) && coach_id == coach_ids.get(i)) {
                        if (matcheds.get(i) == 1) {
                            sendbutton.setText("Matched")
                            sendbutton.isEnabled = false
                        } else if (Inviteds.get(i) == -1){
                            sendbutton.setText("Invitation Sent")
                            sendbutton.isEnabled = false
                        } else if (Inviteds.get(i) == 1) {
                            sendbutton.setText("Match Student")
                            invited = 1
                        }

                    }
                }

            },
            { error ->
                Log.e("MyActivity",error.toString())
            }
        )
        Volley.newRequestQueue(this).add(jsonObjectRequest)

        sendbutton.setOnClickListener{
            /* val intent = Intent(this, StudentPoolActivity::class.java)
            startActivity(intent) */
            if (invited == 1) {

            } else if (invited == 0) {
                var Matched: Int = 0
                var Invited: Int = -1
                // temporarily rating is -1, because still unrated
                var rating: Int = -1

                Log.d("matching", "$student_id, $coach_id, $Matched, $Invited")
                sendInfo(FLASK_URL +"match?&student_id=$student_id&coach_id=$coach_id&Matched=$Matched&Invited=$Invited&Rating=$rating")


                sendbutton.setText("Invitation sent")
                sendbutton.isEnabled = false

                Toast.makeText(getApplicationContext(),"Great! You have sent the Request", Toast.LENGTH_SHORT).show();

            }

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

    // if the two users are matched already, then the coach cannot send the button again
    fun checkMatched(url: String, student_id: Int, coach_id: Int) {

    }
}

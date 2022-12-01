package com.example.coachme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.coachme.Coach_reg3.Companion.FLASK_URL
import org.json.JSONArray
import org.w3c.dom.Text

class coachprofile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coachprofile)
        R.layout.activity_coach_pool_filter
        val student_user_id: Int? = intent.getIntExtra("student_user_id", 0)
        val student_first_name: Int? = intent.getIntExtra("student_first_name", 0)
        val coach_user_id: Int? = intent.getIntExtra("coach_user_id", 0)
        val student_id: Int? = intent.getIntExtra("student_id", 0)
        val coach_id: Int? = intent.getIntExtra("coach_id", 0)
        val coach_name: String? = intent.getStringExtra("coach_name")
        val student_username: String? = intent.getStringExtra("student_username")
        val qua: String? = intent.getStringExtra("qua")
        val yearExp: String? = intent.getStringExtra("yearExp")
        val rating: Int? = intent.getIntExtra("rating", 0)
        val rated_ppl: Int? = intent.getIntExtra("rated_ppl", 0)
        var current_coach: Coach

// TODO: display bookmark button according to the user history, and update both the coach and student database
        val sendbutton = findViewById<Button>(R.id.send_button)
        val dumbbellhome = findViewById<ImageButton>(R.id.dumbbell_button)


        //this send button text is tengible to change, if the student has matched with the coach, it is a rating button,
        //else, it is send request
        sendbutton.setOnClickListener {
            /*val studentintent = Intent(this, CoachPoolActivity::class.java)
            studentintent.putExtra("first_name", student_first_name)
            studentintent.putExtra("user_id", student_user_id)
            studentintent.putExtra("student_id", student_id)
            startActivity(studentintent) */

            var match_id: Int = 0

            val url:String = FLASK_URL+"matched"
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
            var Invited: Int = 1
            sendInfo(FLASK_URL +"match?match_id=$match_id&student_id=$student_id&coach_id=$coach_id&Matched=$Matched&Invited=$Invited&Rating=$rating")

            overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);
            Toast.makeText(this@coachprofile, "Great, You have submitted the request!", Toast.LENGTH_SHORT).show()
        }

        dumbbellhome.setOnClickListener {
            val studentintent = Intent(this, CoachPoolActivity::class.java)
            intent.putExtra("first_name", student_first_name)
            startActivity(studentintent)
            overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);
            Toast.makeText(this@coachprofile, "Great, You have submitted the request!", Toast.LENGTH_SHORT).show()
        }

        val url:String = Coach_reg3.FLASK_URL +"get_coach?user_id=$coach_user_id"
        val jsonObjRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                Log.d("Coach Retrieved", response.toString())
                val user_ids = response.get("user_id") as JSONArray
                val names = response.get("first_name") as JSONArray
                val coach_ids = response.get("coach_id") as JSONArray
                val usernames = response.get("username") as JSONArray
                val yearExps = response.get("yearExp") as JSONArray
                val qualifications = response.get("qua") as JSONArray
                val ratings = response.get("rating") as JSONArray
                val genders = response.get("gender") as JSONArray
                val bookmarks = response.get("bookmark") as JSONArray
                val first_names = response.get("first_name") as JSONArray
                val ages = response.get("age") as JSONArray
                val rated_ppls = response.get("rated_ppl") as JSONArray
                val intros = response.get("intro") as JSONArray

                val username = usernames.getString(0)
                val first_name = first_names.getString(0)
                val intro = intros.getString(0)
                val age = ages.getInt(0)
                val gender = genders.getString(0)
                val rating = ratings.getInt(0)
                val rated_ppl = rated_ppls.getInt(0)

                val coach_name_text = findViewById<TextView>(R.id.coach_name)
                val age_text = findViewById<TextView>(R.id.age_text)
                val yearExp_text = findViewById<TextView>(R.id.yearExp_text)
                val qua_text = findViewById<TextView>(R.id.qualification_text)
                val intro_text = findViewById<TextView>(R.id.intro_text)
                val gender_view = findViewById<ImageView>(R.id.gender)
                val rating_bar = findViewById<RatingBar>(R.id.listitemrating)

                coach_name_text.text = coach_name
                age_text.text = age.toString()
                yearExp_text.text = yearExp.toString() + "years"
                intro_text.text = intro
                qua_text.text = qua
                if (gender.equals("female")) {
                    gender_view.setImageResource(R.drawable.female)
                } else if (gender.equals("male")) {
                    gender_view.setImageResource(R.drawable.male)
                }

                rating_bar.rating = (rating.toFloat()/ rated_ppl.toFloat())



            },
            { error ->
                //Handle error
                Log.d("error of getting coach objects", error.toString())
            })
        Volley.newRequestQueue(this).add(jsonObjRequest)
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
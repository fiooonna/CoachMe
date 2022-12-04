package com.example.coachme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.ViewDebug.FlagToString
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
import de.hdodenhof.circleimageview.CircleImageView
import org.json.JSONArray
import org.w3c.dom.Text

class coachprofile : AppCompatActivity() {

    private var invited = 0
    private var to_be_rated = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coachprofile)
        R.layout.activity_coach_pool_filter
        val student_user_id: Int? = intent.getIntExtra("student_user_id", 0)
        val student_first_name: Int? = intent.getIntExtra("student_first_name", 0)
        var coach_user_id: Int = intent.getIntExtra("coach_user_id", 0)
        val coach_gender: String? = intent.getStringExtra("gender")

        if (coach_user_id != 0) {
            val userSharedPreference = getSharedPreferences("userSharedPreference", MODE_PRIVATE)
            userSharedPreference.edit()
                .putInt("coach_user_id", coach_user_id)
                .commit()
        }
        coach_user_id = getSharedPreferences("userSharedPreference", MODE_PRIVATE).getInt("coach_user_id", 0)
        var student_id: Int = 0
        var coach_id: Int = intent.getIntExtra("coach_id", 0)
        if (coach_id != 0) {
            val userSharedPreference = getSharedPreferences("userSharedPreference", MODE_PRIVATE)
            userSharedPreference.edit()
                .putInt("coach_id", coach_id)
                .commit()
        }
        coach_id = getSharedPreferences("userSharedPreference", MODE_PRIVATE).getInt("coach_id", 0)
        Log.d("coach_id in coach profile", coach_id.toString())
        val coach_name: String? = intent.getStringExtra("coach_name")
        val username: String? = getSharedPreferences("userSharedPreference", MODE_PRIVATE).getString("USERNAME", "")
        val qua: String? = intent.getStringExtra("qua")
        val expertise: String? = intent.getStringExtra("expertise")
        val yearExp: String? = intent.getStringExtra("yearExp")
        val rating: Int? = intent.getIntExtra("rating", 0)
        val rated_ppl: Int? = intent.getIntExtra("rated_ppl", 0)
        var current_coach: Coach

        var url:String = FLASK_URL+"get_student?&username=$username"
        /*val url:String = "http://192.168.31.127:5000/project"*/
        var jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                val student_ids: JSONArray = response.get("student_id") as JSONArray
                student_id = student_ids.get(0).toString().toInt()
                Log.d("Retrived student_id from db",coach_id.toString())
            },
            { error ->
                Log.e("MyActivity",error.toString())
            }
        )
        Volley.newRequestQueue(this).add(jsonObjectRequest)

// TODO: display bookmark button according to the user history, and update both the coach and student database
        val sendbutton = findViewById<Button>(R.id.send_button)
        val dumbbellhome = findViewById<ImageButton>(R.id.dumbbell_button)
        val BookMark_button = findViewById<ImageButton>(R.id.Bookmark_button)

        //this send button text is tengible to change, if the student has matched with the coach, it is a rating button,
        //else, it is send request
        sendbutton.setOnClickListener {
            /*val studentintent = Intent(this, CoachPoolActivity::class.java)
            studentintent.putExtra("first_name", student_first_name)
            studentintent.putExtra("user_id", student_user_id)
            studentintent.putExtra("student_id", student_id)
            startActivity(studentintent) */

            if (to_be_rated == 1) {
                val intent = Intent(this, activityrating::class.java)
                intent.putExtra("coach_id", coach_id)
                intent.putExtra("coach_name", coach_name)
                intent.putExtra("student_id", student_id)
                intent.putExtra("coach_user_id",coach_user_id)
                intent.putExtra("gender",coach_gender)

                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right,
                    R.anim.slide_out_left);

            } else if (invited == 1) {
                // update the match table to Matched == 1
                var url:String = FLASK_URL+"match?&student_id=$student_id&coach_id=$coach_id"
                /*val url:String = "http://192.168.31.127:5000/project"*/
                var jsonObjectRequest = JsonObjectRequest(
                    Request.Method.GET, url, null,
                    { response ->
                        print("send request")
                    },
                    { error ->
                        Log.e("MyActivity",error.toString())
                    }
                )
                Volley.newRequestQueue(this).add(jsonObjectRequest)
                sendbutton.setText("Matched_succesfully")
                sendbutton.isEnabled = false
            } else if (invited == 0){
                var Matched: Int = 0
                var Invited: Int = 1
                var Rating: Int = -1
                sendInfo(FLASK_URL +"invite?&student_id=$student_id&coach_id=$coach_id&Matched=$Matched&Invited=$Invited&Rating=$Rating")

                sendbutton.setText("Invitation sent")
                sendbutton.isEnabled = false

                overridePendingTransition(R.anim.slide_in_left,
                    R.anim.slide_out_right);
                Toast.makeText(this@coachprofile, "Great, You have submitted the request!", Toast.LENGTH_SHORT).show()
            }

        }

        dumbbellhome.setOnClickListener {

            val studentintent = Intent(this, main_student::class.java)
            intent.putExtra("first_name", student_first_name)
            intent.putExtra("username", username)
            intent.putExtra("user_id", student_user_id)
            startActivity(studentintent)
            overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);
        }

        var bookmark: Int = 0
        var bookmark_now: Int = 0

        url = Coach_reg3.FLASK_URL +"get_coach?user_id=$coach_user_id"
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
                val expertises = response.get("expertise") as JSONArray
                val locations = response.get("location") as JSONArray
                val rated_ppls = response.get("rated_ppl") as JSONArray
                val intros = response.get("intro") as JSONArray

                val username = usernames.getString(0)
                val first_name = first_names.getString(0)
                val intro = intros.getString(0)
                val age = ages.getInt(0)
                val expertise = expertises.getString(0)
                val qua = qualifications.getString(0)
                val yearExp = yearExps.getString(0).split(" ")[0].toInt()
                val gender = genders.getString(0)
                var rating = ratings.getInt(0).toFloat()
                var rated_ppl = rated_ppls.getInt(0)
                val location = locations.getString(0)
                bookmark = bookmarks.getInt(0)

                val coach_name_text = findViewById<TextView>(R.id.coach_name)
                val age_text = findViewById<TextView>(R.id.age_text)
                val yearExp_text = findViewById<TextView>(R.id.yearExp_text)
                val qua_text = findViewById<TextView>(R.id.qualification_text)
                val intro_text = findViewById<TextView>(R.id.intro_text)
                val gender_view = findViewById<ImageView>(R.id.gender)
                val rating_bar = findViewById<RatingBar>(R.id.listitemrating)
                val expertise_text = findViewById<TextView>(R.id.expertise_text)
                val location_text = findViewById<TextView>(R.id.location_text)
                val profile_image = findViewById<CircleImageView>(R.id.profile_image)

                val resID = resources.getIdentifier("coach"+coach_user_id+"_"+gender, "drawable",
                    packageName
                )
                Log.i("resID","coach"+coach_user_id+"_"+gender)
                profile_image.setImageResource(resID)

                coach_name_text.text = first_name

                age_text.text = age.toString()
                yearExp_text.text = yearExp.toString() + " years"
                intro_text.text = intro
                qua_text.text = qua
                expertise_text.text = expertise

                location_text.text = location
                expertise_text.text = expertise

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


        url = FLASK_URL + "get_matched"
        jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                println("send request")
                val match_ids: JSONArray = response.get("match_id") as JSONArray
                val student_ids: JSONArray = response.get("student_id") as JSONArray
                val coach_ids: JSONArray = response.get("coach_id") as JSONArray
                val matcheds: JSONArray = response.get("matched") as JSONArray
                val Inviteds: JSONArray = response.get("Invited") as JSONArray
                val Ratings: JSONArray = response.get("Rating") as JSONArray
                val Bookmarks: JSONArray = response.get("Bookmarks") as JSONArray
                Log.d("sc pair", "$coach_id, $student_id")
                for (i in 0 .. match_ids.length() - 1) {
                    if (student_id == student_ids.get(i) && coach_id == coach_ids.get(i) && Inviteds.get(i) != 0) {
                        if (matcheds.get(i) == 1) {
                            if (Ratings.get(i) == -1) {
                                sendbutton.setText("Rate the Coach")
                                to_be_rated = 1
                            } else {
                                sendbutton.setText("Rated")
                                sendbutton.isEnabled = false
                            }

                        } else if (Inviteds.get(i) == -1){
                            sendbutton.setText("Match Coach")
                            invited = 1
                        } else if (Inviteds.get(i) == 1) {
                            sendbutton.setText("Invitation Sent")
                            sendbutton.isEnabled = false

                        }

                        if (Bookmarks.get(i) == 1) {
                            BookMark_button.setBackgroundResource(R.drawable.bookmark)
                            bookmark_now = 1
                        } else if (Bookmarks.get(i) == 0) {
                            BookMark_button.setBackgroundResource(R.drawable.bookmark_mark)
                            bookmark_now = 0
                        }

                    }
                }

            },
            { error ->
                Log.e("MyActivity",error.toString())
            }
        )
        //force the system to sleep to wait for response
        SystemClock.sleep(100);
        Volley.newRequestQueue(this).add(jsonObjectRequest)


        BookMark_button.setOnClickListener {
            var bm_update = 0
            Log.d("current bookMark", bookmark_now.toString())
            if (bookmark_now == 0) {

                Log.d("bookmark", "+1")
                bookmark = bookmark + 1
                bm_update = 1
                bookmark_now = 1
                BookMark_button.setBackgroundResource(R.drawable.bookmark)
            } else if (bookmark_now == 1) {
                Log.d("bookmark", "-1")
                bookmark = bookmark - 1
                bm_update = 0
                bookmark_now = 0
                BookMark_button.setBackgroundResource(R.drawable.bookmark_mark)
            }
            //update the table (both the match and Coach table)

            url = FLASK_URL + "update_bookmark?&student_id=$student_id&coach_id=$coach_id&bm_update=$bm_update&bookmark=$bookmark"
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
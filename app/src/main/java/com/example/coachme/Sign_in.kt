package com.example.coachme

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.coachme.Coach_reg3.Companion.FLASK_URL
import com.example.coachme.Model.MyResponse
import com.example.coachme.Retrofit.IreCAPTCHA
import com.example.coachme.Retrofit.RetrofitClient
import com.google.android.gms.common.api.*
import com.google.android.gms.safetynet.SafetyNet
import com.google.android.gms.safetynet.SafetyNetApi
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import dmax.dialog.SpotsDialog
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executor

class Sign_in : AppCompatActivity() {
    private lateinit var username: String
    private lateinit var pw: String
    private var human: String? = null

    private var sitekey: String = "6LfmAVQjAAAAANphERSr95w1sxlznU7m8FoHh9xp" //only local? if yes need to find a way to make it global (also for the secret key in recaptcha.php)
    private val api: IreCAPTCHA
        get() = RetrofitClient.getClient("http://localhost/../CoachMe-main/app/src/main/java/com/example/coachme/recaptcha.php/").create(IreCAPTCHA::class.java)
    lateinit var mService: IreCAPTCHA

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        var back = findViewById<ImageButton>(R.id.back_btn)
        back.setOnClickListener(View.OnClickListener() {
            val intent = Intent(this, Landing_page::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right)
        })

        //CAPTCHA part
        mService = api
        var robot: CheckBox = findViewById(R.id.checkBox)
        var done: Boolean = false
        robot.setOnClickListener(View.OnClickListener() {
            if (robot.isChecked == true && done == false) {
                //robot.setChecked(false)
                SafetyNet.getClient(this)
                    .verifyWithRecaptcha(sitekey)
                    .addOnSuccessListener { response ->
                        if (!response.tokenResult!!.isEmpty()) {
                            done = verifyTokenOnServer(response.tokenResult!!)
                            if (done) {
                                robot.setChecked(true)
                            }
                        }
                    }
                    .addOnFailureListener { e ->
                        if (e is ApiException) {
                            Log.d(TAG, "Error: ${e.message}")
                        }
                    }
            }
        })

        var sign_in: Button = findViewById(R.id.button5)
        sign_in.setOnClickListener {
            username = findViewById<EditText>(R.id.Username)!!.text.toString()
            pw = findViewById<EditText>(R.id.Password)!!.text.toString()
            human = findViewById<CheckBox>(R.id.checkBox)!!.isChecked.toString()

            val url:String = FLASK_URL+"get_user"
            /*val url:String = "http://192.168.31.127:5000/project"*/
            val jsonObjectRequest = JsonObjectRequest(
                Request.Method.GET, url, null,
                { response ->
                    switchActivity(response, username, pw)
                    if (human.equals("true")) {
                        switchActivity(response, username, pw)
                    } else {
                        Toast.makeText(this@Sign_in, "Please confirm that you are not a robot.", Toast.LENGTH_SHORT).show()
                    }
                },
                { error ->
                    Log.e("MyActivity",error.toString())
                }
            )
            Volley.newRequestQueue(this).add(jsonObjectRequest)

            overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left)
        }

        var sign_up_now: Button = findViewById(R.id.button6)
        sign_up_now.setOnClickListener {
            val intent = Intent(this, Sign_up::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        }
    }

    private fun verifyTokenOnServer(response: String): Boolean {
        val dialog = SpotsDialog(this@Sign_in)
        dialog.show()
        dialog.setMessage("Please wait")

        mService.validate(response)
                .enqueue(object:Callback<MyResponse>{
                    override fun onResponse(call: Call<MyResponse>?, response: Response<MyResponse>?) {

                        dialog.dismiss()

                        if (response!!.body()!!.success) {
                            Toast.makeText(this@Sign_in, "Posted", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this@Sign_in, response.body()!!.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                    override fun onFailure(call: Call<MyResponse>?, t: Throwable?) {
                        dialog.dismiss()

                        if (t != null) {
                            Log.d("EMDTERROR", t.message!!)
                        }
                    }

                })

        return true
    }

    fun switchActivity(jsonObj: JSONObject, username: String, pw: String?){
        val user_id: JSONArray = jsonObj.get("user_id") as JSONArray
        val usernames: JSONArray = jsonObj.get("username") as JSONArray
        val pws: JSONArray = jsonObj.get("pw") as JSONArray
        val email: JSONArray = jsonObj.get("email") as JSONArray
        val id: JSONArray = jsonObj.get("id") as JSONArray
        val first_name: JSONArray = jsonObj.get("first_name") as JSONArray
        val last_name: JSONArray = jsonObj.get("last_name") as JSONArray
        val address: JSONArray = jsonObj.get("address") as JSONArray
        val gender: JSONArray = jsonObj.get("gender") as JSONArray
        val age: JSONArray = jsonObj.get("age") as JSONArray

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
                    val userSharedPreference = getSharedPreferences("userSharedPreference", MODE_PRIVATE)
                    userSharedPreference.edit()
                        .putString("USERNAME", username)
                        .putString("first_name", first_name.get(i).toString())
                        .putString("last_name", last_name.get(i).toString())
                        .putString("user_id",user_id.get(i).toString())
                        .putString("photo_name","student"+user_id.get(i).toString()+"_"+gender.get(i).toString())
                        .commit()
                    Log.i("SharedPreference saved", "$username, ${first_name.get(i).toString()}" )
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
                    val userSharedPreference = getSharedPreferences("userSharedPreference", MODE_PRIVATE)
                    userSharedPreference.edit()
                        .putString("USERNAME", username)
                        .putString("first_name", first_name.get(i).toString())
                        .putString("last_name", last_name.get(i).toString())
                        .putString("photo_name","coach"+user_id.get(i).toString()+"_"+gender.get(i).toString())
                        .commit()
                    Log.i("SharedPreference saved", "$username, ${first_name.get(i).toString()}" )
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

/*    private fun onClick(*//*view: View*//*) {
        SafetyNet.getClient(this).verifyWithRecaptcha(sitekey)
            .addOnSuccessListener(this) { response ->
                // Indicates communication with reCAPTCHA service was
                // successful.
                val userResponseToken = response.tokenResult
                if (response.tokenResult?.isNotEmpty() == true) {
                    Log.i(TAG, "onSuccess: " + response.tokenResult)
                }
            }
            .addOnFailureListener(this as Executor, OnFailureListener { e ->
                if (e is ApiException) {
                    // An error occurred when communicating with the
                    // reCAPTCHA service. Refer to the status code to
                    // handle the error appropriately.
                    Log.d(TAG, "Error: ${CommonStatusCodes.getStatusCodeString(e.statusCode)}")
                } else {
                    // A different, unknown type of error occurred.
                    Log.d(TAG, "Error: ${e.message}")
                }
            })
    }*/
}

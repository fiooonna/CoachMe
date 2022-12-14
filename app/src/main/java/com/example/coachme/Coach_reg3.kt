package com.example.coachme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.androidbuts.multispinnerfilter.KeyPairBoolData
import com.androidbuts.multispinnerfilter.MultiSpinnerSearch
import org.json.JSONArray
import org.json.JSONObject

class Coach_reg3 : AppCompatActivity() {
    private var qua: String? = null
    private lateinit var button: Button
    private lateinit var imageView: ImageView

    companion object {
        val IMAGE_REQUEST_CODE = 100
        val FLASK_URL: String = "http://10.0.2.2:5000/"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coach_reg3)

        button = findViewById(R.id.UploadButton)
        imageView = findViewById(R.id.UploadProof)

        button.setOnClickListener{
            pickImageGallery()
        }



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

        val quali_multispinner =
            findViewById<MultiSpinnerSearch>(R.id.quali_multispinner)

        quali_multispinner.setSearchEnabled(true);
        quali_multispinner.setSearchHint("Select the qualification");
        quali_multispinner.setEmptyTitle("Not Data Found!");
        quali_multispinner.setShowSelectAllButton(true);
        quali_multispinner.setClearText("Close & Clear");

        val qualiArray = arrayOf<String>("NASM", "CPT", "CIAR", "NSCA-CC", "AFPA", "AFAA")

        val qualiArrayMutable: MutableList<KeyPairBoolData> = ArrayList()
        for (i in 0 until qualiArray.size) {
            val h = KeyPairBoolData()
            h.id = (i + 1).toLong()
            h.name = qualiArray.get(i)
            h.isSelected = false
            qualiArrayMutable.add(h)
        }

        var quali = ""
        quali_multispinner.setItems(qualiArrayMutable) { items ->
            quali = ""
            for (i in items.indices) {
                if (items[i].isSelected) {
                    Log.i("quali Spinner selected", i.toString() + " : " + items[i].name + " : " + items[i].isSelected)
                    quali = quali + items[i].name + ","
                }
            }
            quali = quali.substring(0, quali.length - 1)
        }

        val back = findViewById<ImageButton>(R.id.back_btn)
        back.setOnClickListener(View.OnClickListener() {
            val intent = Intent(this, Coach_reg2::class.java)
            startActivity(intent)
        })

        val cont = findViewById<Button>(R.id.contd)
        cont.setOnClickListener(View.OnClickListener() {


            if (quali!!.isNotEmpty() ) {
                var intent = Intent(this, Coach_reg4::class.java)
                sendInfo(FLASK_URL+"coach?email=$email&pw=$pw&ids=$id&first_name=$first_name&last_name=$last_name&username=$username&address=$address&gender=$gender&age=$age&exp=$exp&expertise=$expertise&intro=$intro&qua=$quali")
                /*sendInfo("http://192.168.31.127:5000/project?email=$email&pw=$pw&id=$id&first_name=$first_name&last_name=$last_name&username=$username&address=$address&gender=$gender&age=$age&exp=$exp&expertise=$expertise&intro=$intro&qua=$qua")*/
                startActivity(intent)
            } else {
                Toast.makeText(this@Coach_reg3, "Something is not completed. Please check", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun pickImageGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK) {
            imageView.setImageURI(data?.data)
        }
    }

    fun sendInfo(url: String) {
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                println("added")
            },
            { error ->
                Log.e("MyActivity",error.toString())
            }
        )
        Volley.newRequestQueue(this).add(jsonObjectRequest)
    }

}
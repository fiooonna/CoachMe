package com.example.coachme

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import com.androidbuts.multispinnerfilter.KeyPairBoolData
import com.androidbuts.multispinnerfilter.MultiSpinnerSearch
import com.google.android.material.slider.Slider

class Coach_reg1 : AppCompatActivity() {
    private var gender: String = ""
    private var age: String? = null
    private var exp: String? = null
    private var expertise: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coach_reg1)

        var intent: Intent = getIntent()
        val email: String? = intent.getStringExtra("email")
        val pw: String? = intent.getStringExtra("pw")
        val firm_pw: String? = intent.getStringExtra("firm_pw")
        val id: String? = intent.getStringExtra("id")
        val first_name: String? = intent.getStringExtra("first_name")
        val last_name: String? = intent.getStringExtra("last_name")
        val username: String? = intent.getStringExtra("username")
        val address: String? = intent.getStringExtra("address")

        var back = findViewById<ImageButton>(R.id.back_btn)
        back.setOnClickListener(View.OnClickListener() {
            val intent = Intent(this, General_reg::class.java)
            startActivity(intent)
        })

        var male: Button = findViewById(R.id.MaleButton)
        var female: Button = findViewById(R.id.FemaleButton)

        male.setOnClickListener {
            gender = "male"
            male.setBackgroundResource(R.drawable.radio_selected)
            male.setTextColor(Color.parseColor("#ffffff"))
            female.setBackgroundResource(R.drawable.radio_selector)
            female.setTextColor(Color.parseColor("#000000"))
        }
        female.setOnClickListener {
            gender = "female"
            female.setBackgroundResource(R.drawable.radio_selected)
            female.setTextColor(Color.parseColor("#ffffff"))
            male.setBackgroundResource(R.drawable.radio_selector)
            male.setTextColor(Color.parseColor("#000000"))
        }

        val expertise_multispinner =
            findViewById<MultiSpinnerSearch>(R.id.expertise_multispinner)
        val expertiseArray = arrayOf<String>("Powerlifting", "Strength Training", "Crossfit", "TRX", "Olympic Lifting", "Mobility & Flexibility")
        expertise_multispinner.setSearchEnabled(true);
        expertise_multispinner.setSearchHint("Select the expertise");
        expertise_multispinner.setEmptyTitle("Not Data Found!");
        expertise_multispinner.setShowSelectAllButton(true);
        expertise_multispinner.setClearText("Close & Clear");

        val ExpertiseArrayMutable: MutableList<KeyPairBoolData> = ArrayList()
        for (i in 0 until expertiseArray.size) {
            val h = KeyPairBoolData()
            h.id = (i + 1).toLong()
            h.name = expertiseArray.get(i)
            h.isSelected = false
            ExpertiseArrayMutable.add(h)
        }

        var expertise = ""
        expertise_multispinner.setItems(ExpertiseArrayMutable) { items ->
            expertise = ""
            for (i in items.indices) {
                if (items[i].isSelected) {
                    Log.i("expertise Spinner selected", i.toString() + " : " + items[i].name + " : " + items[i].isSelected)
                    expertise = expertise + items[i].name + ","
                }
            }
            expertise = expertise.substring(0, expertise.length - 1)
        }

        var con: Button = findViewById(R.id.contd)
        con.setOnClickListener(View.OnClickListener() {
            age = findViewById<Slider>(R.id.AgeInput).value.toInt().toString()
            exp = findViewById<EditText>(R.id.ExperienceInput)!!.text.toString()
            Log.i("expertise got", expertise)
            var intent = Intent(this, Coach_reg2::class.java)
            if (gender!!.isNotEmpty() && age!!.isNotEmpty() && exp!!.isNotEmpty() && expertise!!.isNotEmpty()) {
                intent.putExtra("email", email)
                intent.putExtra("pw", pw)
                intent.putExtra("firm_pw", firm_pw)
                intent.putExtra("id", id)
                intent.putExtra("first_name", first_name)
                intent.putExtra("last_name", last_name)
                intent.putExtra("username", username)
                intent.putExtra("address", address)
                intent.putExtra("gender", gender)
                intent.putExtra("age", age)
                intent.putExtra("exp", exp)
                intent.putExtra("expertise", expertise)
                startActivity(intent)
            } else {
                Toast.makeText(this@Coach_reg1, "Something is not completed. Please check", Toast.LENGTH_SHORT).show()
            }
        })

    }
}
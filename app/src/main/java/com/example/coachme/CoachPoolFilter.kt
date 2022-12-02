package com.example.coachme

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.androidbuts.multispinnerfilter.KeyPairBoolData
import com.androidbuts.multispinnerfilter.MultiSpinnerSearch
import com.jaygoo.widget.RangeSeekBar


class CoachPoolFilter : AppCompatActivity() {
    private var genders: ArrayList<String> = arrayListOf()
    private var expertise: ArrayList<String> = arrayListOf()
    private var qualification: ArrayList<String> = arrayListOf()
    private var locations: ArrayList<String> = arrayListOf()
    private lateinit var districtArrayList: ArrayList<KeyPairBoolData>
    private lateinit var expertiseArrayList: ArrayList<KeyPairBoolData>
    private lateinit var qualiArrayList: ArrayList<KeyPairBoolData>
    var femaleCheck = 1
    var maleCheck = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coach_pool_filter)

        /* val user_id = intent.getIntExtra("student_user_id", -1)
        val student_id = intent.getIntExtra("student_id", -1)
        val student_first_name = intent.getStringExtra("student_first_name")
        val student_username = intent.getStringExtra("student_username") */

        //Log.d("received extra", "$user_id, $student_id, $student_first_name, $student_username")

        val YearExp_input = findViewById<RangeSeekBar>(R.id.YearExp_input)
        val Address_multispinner = findViewById<MultiSpinnerSearch>(R.id.location_spinner)
        val filter_button = findViewById<ImageButton>(R.id.filter_button)
        YearExp_input.setIndicatorTextDecimalFormat("0")
        val quali_multispinner =
          findViewById<MultiSpinnerSearch>(R.id.quali_multispinner)

        val expertise_multispinner =
            findViewById<MultiSpinnerSearch>(R.id.expertise_multispinner)

        var maleButton: Button = findViewById(R.id.MaleButton)
        var femaleButton: Button = findViewById(R.id.FemaleButton)


        //should be able to select both of them
        maleButton.setOnClickListener {
            if (maleCheck == 1) {

                maleButton.setBackgroundResource(R.drawable.radio_selected)
                maleButton.setTextColor(Color.parseColor("#ffffff"))
                maleCheck = 0
                genders.add("male")
                Log.i("triggered male", genders.get(0))
            } else {
                maleButton.setBackgroundResource(R.drawable.radio_normal)
                maleButton.setTextColor(Color.parseColor("#000000"))
                maleCheck = 1
                if (genders.contains("male")){
                    genders.remove("male")
                }
            }
        }
        femaleButton.setOnClickListener {
            if (femaleCheck == 1) {
                femaleButton.setBackgroundResource(R.drawable.radio_selected)
                femaleButton.setTextColor(Color.parseColor("#ffffff"))
                femaleCheck = 0
                genders.add("female")
            } else {
                femaleButton.setBackgroundResource(R.drawable.radio_normal)
                femaleButton.setTextColor(Color.parseColor("#000000"))
                femaleCheck = 1
                if (genders.contains("female")){
                    genders.remove("female")
                }
            }
        }
//------------

        quali_multispinner.setSearchEnabled(true);
        quali_multispinner.setSearchHint("Select the qualification");
        quali_multispinner.setEmptyTitle("Not Data Found!");
        quali_multispinner.setShowSelectAllButton(true);
        quali_multispinner.setClearText("Close & Clear");

        expertise_multispinner.setSearchEnabled(true);
        expertise_multispinner.setSearchHint("Select the expertise");
        expertise_multispinner.setEmptyTitle("Not Data Found!");
        expertise_multispinner.setShowSelectAllButton(true);
        expertise_multispinner.setClearText("Close & Clear");

        Address_multispinner.setSearchEnabled(true);
        Address_multispinner.setSearchHint("Select the district");
        Address_multispinner.setEmptyTitle("Not Data Found!");
        Address_multispinner.setShowSelectAllButton(true);
        Address_multispinner.setClearText("Close & Clear");

        val districtArray = arrayOf<String>("Central and Western", "Eastern", "Southern", "Wan Chai", "Kowloon City", "Kwun Tong",
            "Sham Shui Po", "Wong Tai Sin", "Yau Tsim Mong", "Islands", "Kwai Tsing", "North", "Sai Kung",
            "Sha Tin", "Tai Po", "Tsuen Wan", "Tuen Mun", "Yuen Long")

        districtArrayList =  ArrayList<KeyPairBoolData>()
        for (i in 0 until districtArray.size) {
            val h = KeyPairBoolData()
            h.id = (i + 1).toLong()
            h.name = districtArray.get(i)
            h.isSelected = false
            districtArrayList.add(h)
        }

        //var district = listOf<String>()
        Address_multispinner.setItems(districtArrayList) { items ->
            //district = listOf()
            for (i in items.indices) {
                if (items[i].isSelected) {
                    //Log.i("district Spinner selected", i.toString() + " : " + items[i].name + " : " + items[i].isSelected)
                    //district = district + items[i].name
                }
            }
        }

        val qualiArray = arrayOf<String>("NASM", "CPT", "CIAR", "NSCA-CC", "AFPA", "AFAA")
        qualiArrayList = ArrayList<KeyPairBoolData>()
        for (i in 0 until qualiArray.size) {
            val h = KeyPairBoolData()
            h.id = (i + 1).toLong()
            h.name = qualiArray.get(i)
            h.isSelected = false
            qualiArrayList.add(h)
        }

        //var qualiArrayList = listOf<String>()
        quali_multispinner.setItems(qualiArrayList) { items ->
            //quali = listOf()
            for (i in items.indices) {
                if (items[i].isSelected) {
                    //Log.i("quali Spinner selected", i.toString() + " : " + items[i].name + " : " + items[i].isSelected)
                    //quali = quali + items[i].name
                }
            }
        }


        val expertiseArray = arrayOf<String>("Powerlifting", "Strength Training", "Crossfit", "TRX", "Olympic Lifting", "Mobility & Flexibility")

        expertiseArrayList =  ArrayList<KeyPairBoolData>()
        for (i in 0 until expertiseArray.size) {
            val h = KeyPairBoolData()
            h.id = (i + 1).toLong()
            h.name = expertiseArray.get(i)
            h.isSelected = false
            expertiseArrayList.add(h)
        }

        //var expertise = listOf<String>()
        expertise_multispinner.setItems(expertiseArrayList) { items ->
            //expertise = listOf()
            for (i in items.indices) {
                if (items[i].isSelected) {
                    //Log.i("expertise Spinner selected", i.toString() + " : " + items[i].name + " : " + items[i].isSelected)
                    //expertise = expertise + items[i].name
                }
            }
        }




        filter_button.setOnClickListener{
            val intent = Intent(this, CoachPoolActivity::class.java)
            var YearExp = -1
            for (item in Address_multispinner.selectedItems){
                locations.add(item.name)
            }
            for (item in quali_multispinner.selectedItems){
                qualification.add(item.name)
            }

            for (item in expertise_multispinner.selectedItems){
                expertise.add(item.name)
            }
            YearExp = YearExp_input.leftSeekBar.progress.toInt()
            Log.d("filter info", "$YearExp, $genders, $locations, $expertise, $qualification")
            //Log.d("ready to send extra", "$user_id, $student_id, $student_first_name, $student_username")
            intent.putExtra("filter_YearExp", YearExp)
            intent.putExtra("filter_gender", genders)
            intent.putExtra("filter_quali", qualification)
            intent.putExtra("filter_expertise", expertise)
            intent.putExtra("filter_location", locations)
            /*intent.putExtra("user_id", user_id)
            intent.putExtra("student_id", student_id)
            intent.putExtra("first_name", student_first_name)
            intent.putExtra("student_username", student_username) */
            intent.putExtra("Filtering", true)


            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);
        }
    }
}
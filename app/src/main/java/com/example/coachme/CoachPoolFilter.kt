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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coach_pool_filter)
        val YearExp_input = findViewById<RangeSeekBar>(R.id.YearExp_input)
        val Address_multispinner = findViewById<MultiSpinnerSearch>(R.id.location_spinner)
        val filter_button = findViewById<ImageButton>(R.id.filter_button)
        YearExp_input.setIndicatorTextDecimalFormat("0")
        val quali_multispinner =
          findViewById<MultiSpinnerSearch>(R.id.quali_multispinner)

        val expertise_multispinner =
            findViewById<MultiSpinnerSearch>(R.id.expertise_multispinner)
        var gender = ""
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

        val qualiArray = arrayOf<String>("NASM", "CPT", "CIAR", "NSCA-CC", "AFPA", "AFAA")
        val expertiseArray = arrayOf<String>("Powerlifting", "Strength Training", "Crossfit", "TRX", "Olympic Lifting", "Mobility & Flexibility")
        val districtArray = arrayOf<String>("Central and Western", "Eastern", "Southern", "Wan Chai", "Kowloon City", "Kwun Tong",
        "Sham Shui Po", "Wong Tai Sin", "Yau Tsim Mong", "Islands", "Kwai Tsing", "North", "Sai Kung",
        "Sha Tin", "Tai Po", "Tsuen Wan", "Tuen Mun", "Yuen Long")

        val qualiArrayMutable: MutableList<KeyPairBoolData> = ArrayList()
        for (i in 0 until qualiArray.size) {
            val h = KeyPairBoolData()
            h.id = (i + 1).toLong()
            h.name = qualiArray.get(i)
            h.isSelected = false
            qualiArrayMutable.add(h)
        }


        val ExpertiseArrayMutable: MutableList<KeyPairBoolData> = ArrayList()
        for (i in 0 until expertiseArray.size) {
            val h = KeyPairBoolData()
            h.id = (i + 1).toLong()
            h.name = expertiseArray.get(i)
            h.isSelected = false
            ExpertiseArrayMutable.add(h)
        }

        val DistrictArrayMutable: MutableList<KeyPairBoolData> = ArrayList()
        for (i in 0 until districtArray.size) {
            val h = KeyPairBoolData()
            h.id = (i + 1).toLong()
            h.name = districtArray.get(i)
            h.isSelected = false
            DistrictArrayMutable.add(h)
        }

        var quali = listOf<String>()
        quali_multispinner.setItems(qualiArrayMutable) { items ->
            quali = listOf()
            for (i in items.indices) {
                if (items[i].isSelected) {
                    Log.i("quali Spinner selected", i.toString() + " : " + items[i].name + " : " + items[i].isSelected)
                    quali = quali + items[i].name
                }
            }
        }

        var expertise = listOf<String>()
        expertise_multispinner.setItems(ExpertiseArrayMutable) { items ->
            expertise = listOf()
            for (i in items.indices) {
                if (items[i].isSelected) {
                    Log.i("expertise Spinner selected", i.toString() + " : " + items[i].name + " : " + items[i].isSelected)
                    expertise = expertise + items[i].name
                }
            }
        }

        var district = listOf<String>()
        Address_multispinner.setItems(DistrictArrayMutable) { items ->
            district = listOf()
            for (i in items.indices) {
                if (items[i].isSelected) {
                    Log.i("district Spinner selected", i.toString() + " : " + items[i].name + " : " + items[i].isSelected)
                    district = district + items[i].name
                }
            }
        }



        filter_button.setOnClickListener{
            var YearExp = -1
            YearExp = YearExp_input.leftSeekBar.progress.toInt()
            Log.d("filter info", "$YearExp, $gender, $quali, $expertise, $district")
            val intent = Intent(this, CoachPoolActivity::class.java)
            startActivity(intent)

        }
    }
}
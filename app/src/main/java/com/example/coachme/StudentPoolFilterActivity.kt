package com.example.coachme

import android.content.Intent
import android.graphics.Color
import android.os.Bundle;
import android.util.Log
import android.widget.Button
import android.widget.Filter
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.androidbuts.multispinnerfilter.KeyPairBoolData
import com.androidbuts.multispinnerfilter.MultiSpinnerSearch
import com.jaygoo.widget.RangeSeekBar


// TO-DO: set up the RangeSeekBar, e.g. turn float into integer

class StudentPoolFilterActivity : AppCompatActivity()  {
    /** Called when the activity is first created. */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // TODO: Pass in extra using sharedPreference
        setContentView(R.layout.studentpool_filter)

        val location_spinner =
            findViewById<MultiSpinnerSearch>(R.id.location_spinner)
        val goal_spinner =
            findViewById<MultiSpinnerSearch>(R.id.goal_spinner)

        location_spinner.setSearchEnabled(true);
        location_spinner.setSearchHint("Select the qualification");
        location_spinner.setEmptyTitle("Not Data Found!");
        location_spinner.setShowSelectAllButton(true);
        location_spinner.setClearText("Close & Clear")

        goal_spinner.setSearchEnabled(true);
        goal_spinner.setSearchHint("Select the expertise");
        goal_spinner.setEmptyTitle("Not Data Found!");
        goal_spinner.setShowSelectAllButton(true);
        goal_spinner.setClearText("Close & Clear");

        val Exp_array = arrayOf<String>("Beginner", "Intermediate", "Advanced")
        val goalArray = arrayOf<String>("Toning", "Endurance", "Mobility", "Strength")
        val districtArray = arrayOf<String>("Central and Western", "Eastern", "Southern", "Wan Chai", "Kowloon City", "Kwun Tong",
            "Sham Shui Po", "Wong Tai Sin", "Yau Tsim Mong", "Islands", "Kwai Tsing", "North", "Sai Kung",
            "Sha Tin", "Tai Po", "Tsuen Wan", "Tuen Mun", "Yuen Long")

        val goalArrayMutable: MutableList<KeyPairBoolData> = ArrayList()
        for (i in 0 until goalArray.size) {
            val h = KeyPairBoolData()
            h.id = (i + 1).toLong()
            h.name = goalArray.get(i)
            h.isSelected = false
            goalArrayMutable.add(h)
        }

        val DistrictArrayMutable: MutableList<KeyPairBoolData> = ArrayList()
        for (i in 0 until districtArray.size) {
            val h = KeyPairBoolData()
            h.id = (i + 1).toLong()
            h.name = districtArray.get(i)
            h.isSelected = false
            DistrictArrayMutable.add(h)
        }

        var goal = listOf<String>()
        goal_spinner.setItems(goalArrayMutable) { items ->
            goal = listOf()
            for (i in items.indices) {
                if (items[i].isSelected) {
                    Log.i("quali Spinner selected", i.toString() + " : " + items[i].name + " : " + items[i].isSelected)
                    goal = goal + items[i].name
                }
            }
        }


        var district = listOf<String>()
        location_spinner.setItems(DistrictArrayMutable) { items ->
            district = listOf()
            for (i in items.indices) {
                if (items[i].isSelected) {
                    Log.i("district Spinner selected", i.toString() + " : " + items[i].name + " : " + items[i].isSelected)
                    district = district + items[i].name
                }
            }
        }


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

        val Exp_input = findViewById<RangeSeekBar>(R.id.range_seekbar_experience)


        val RangeSeeker_pay = findViewById<RangeSeekBar>(R.id.range_seekbar_pay)
        RangeSeeker_pay.setIndicatorTextDecimalFormat("0")

        val Filter_button = findViewById<ImageButton>(R.id.filter_button)
        Filter_button.setOnClickListener{
            var Exp_low = Exp_array.get(Exp_input.leftSeekBar.progress.toInt())
            var Exp_high = Exp_array.get(Exp_input.rightSeekBar.progress.toInt())

            var low_pay = RangeSeeker_pay.leftSeekBar.progress.toInt()
            var high_pay = RangeSeeker_pay.rightSeekBar.progress.toInt()

            val goal_array: Array<String> = goal.map {it.toString()}.toTypedArray()
            val district_array: Array<String> = goal.map {it.toString()}.toTypedArray()
            //Gender return "" if nth selected
            Log.d("filter info", "$Exp_low, $Exp_high, $gender, $low_pay, $high_pay,${goal_array.toString()}, ${district_array.toString()}")
            //Log.d("ready to send extra", "$user_id, $student_id, $student_first_name, $student_username")
            intent.putExtra("Exp_low", Exp_low)
            intent.putExtra("Exp_high", Exp_high)
            intent.putExtra("Gender", gender)
            intent.putExtra("low_pay", low_pay)
            intent.putExtra("high_pay", high_pay)
            intent.putExtra("goal", goal_array)
            intent.putExtra("district", district_array)
            val intent = Intent(this, StudentPoolActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);
        }

    }
}
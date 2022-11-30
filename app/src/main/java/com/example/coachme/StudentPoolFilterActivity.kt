package com.example.coachme

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity
import com.jaygoo.widget.RangeSeekBar


// TO-DO: set up the RangeSeekBar, e.g. turn float into integer

class StudentPoolFilterActivity : AppCompatActivity()  {
    /** Called when the activity is first created. */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.studentpool_filter)

        val RangeSeeker_pay = findViewById<RangeSeekBar>(R.id.range_seekbar_pay)
        RangeSeeker_pay.setIndicatorTextDecimalFormat("0")

    }
}
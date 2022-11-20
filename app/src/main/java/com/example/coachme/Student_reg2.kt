package com.example.coachme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView

private var numperwwek: String? = null

class Student_reg2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_reg2)


        val spinner = findViewById<Spinner>(R.id.weekInput)
        val options = arrayListOf("1", "2", "3", "4", "5 or more")
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, options)

        spinner.adapter = arrayAdapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                numperwwek = options.get(p2).toString()

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                numperwwek = null
            }

        }

        val con = findViewById<Button>(R.id.contd)
        con.setOnClickListener(View.OnClickListener() {
            var intent = Intent(this, Student_reg3::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);

        })


    }
}
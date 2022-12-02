package com.example.coachme

import android.graphics.drawable.Drawable

//rating is the accumulated rating sum got by the coach,
//then the final rating displayed is
// (rating/ rated_ppl) * 5
// Gonna remove the rated_ppl attribute
data class Coach(
    var user_id: Int,
    var coach_id: Int,
    var gender: String,
    var name: String,
    var image: Drawable?,
    var yearExp: String, //this includes the string " years" now
    var qualification: String,
    var expertise: String,
    var rating: String,
    var bookmark: String,
    var accum_rating: Int,
    var rated_ppl: Int,
    var location: String,
    var age:Int
)
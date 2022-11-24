package com.example.coachme

import android.graphics.drawable.Drawable


data class Coach(
    var coach_id: Int,
    var name: String,
    var image: Drawable?,
    var yearExp: String,
    var qualification: String,
    var rating: String,
    var bookmark: String
)
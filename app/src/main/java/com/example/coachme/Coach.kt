package com.example.coachme

import android.graphics.drawable.Drawable


data class Coach(
    var name: String,
    var image: Drawable?,
    var experience: String,
    var qualification: String,
    var rating: String,
    var bookmark: String
)
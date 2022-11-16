package com.example.coachme

import android.graphics.drawable.Drawable


data class Student(
    var name: String,
    var image: Drawable?,
    var location: String,
    var goals: String,
    var experience: String,
    var price: String
)
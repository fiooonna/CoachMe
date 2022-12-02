package com.example.coachme

import android.graphics.drawable.Drawable

data class Student(
    var user_id: Int,
    var student_id: Int,
    var gender: String,
    var name: String,
    var image: Drawable? = null,
    var location: String,
    var goals: String,
    var experience: String,
    var price: String,
    var age: Int
)
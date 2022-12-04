package com.example.coachme.Retrofit

import com.example.coachme.Model.MyResponse
import retrofit2.Call
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Field

interface IreCAPTCHA {
    @FormUrlEncoded
    @POST("recaptcha.php")

    fun validate(@Field("recaptcha-response") response: String) :Call<MyResponse>

}
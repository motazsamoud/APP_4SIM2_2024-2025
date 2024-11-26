package com.example.Eco.retrofit


import com.example.Eco.data.ActiviteRating
import com.example.Eco.data.ActiviteRecyclage
import com.example.Eco.data.ChangePasswordRequest
import com.example.Eco.data.ForgotPasswordRequest
import com.example.Eco.data.LoginRequest
import com.example.Eco.data.LoginResponse
import com.example.Eco.data.MaterialStatistics
import com.example.Eco.data.SignupRequest
import com.example.Eco.data.SignupResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface RetrofitServiceInterface {

    // Activité routes
    @GET("/activite")
    fun getActiviteRecyclage(): Call<List<ActiviteRecyclage>>

    @GET("/activite/get/{id}")
    fun getActiviteRecyclageByUser(@Path("id") id: String): Call<List<ActiviteRecyclage>>

    @POST("/activite/add")
    fun addActiviteRecyclage(@Body params: ActiviteRecyclage): Call<ActiviteRecyclage>

    @PATCH("activite/update/{id}")
    fun updateActivite(@Path("id") id: String, @Body params: ActiviteRecyclage): Call<ActiviteRecyclage>

    @DELETE("/activite/delete/{id}")
    fun deleteActivite(@Path("id") id: String): Call<ActiviteRecyclage>

    @GET("/activite/statistics/material")
    fun getMaterialStatistics(): Call<List<MaterialStatistics>>

    @GET("activite/{id}")
    fun getActiviteById(@Path("id") activiteId: String): Call<ActiviteRecyclage>

    @GET("ratings/activite/{id}")
    fun getAllRatingsForActivite(@Path("id") activiteId: String): Call<List<ActiviteRating>>
    @POST("/rating/add")
    fun addRatingActivte(@Body params: ActiviteRating): Call<ActiviteRating>
    @POST("/auth/login")
    fun login(@Body params: LoginRequest): Call<LoginResponse>
    // RetrofitServiceInterface.kt
    @POST("/auth/signup")
    fun signup(@Body params: SignupRequest): Call<SignupResponse>


    @PUT("/auth/change-password")
    fun changePassword(
        @Header("Authorization") accessToken: String,
        @Body changePasswordRequest: ChangePasswordRequest
    ): Call<Void>
    @POST("/auth/forgot-password")
    fun forgotPassword(@Body forgotPasswordRequest: ForgotPasswordRequest): Call<Void>



}
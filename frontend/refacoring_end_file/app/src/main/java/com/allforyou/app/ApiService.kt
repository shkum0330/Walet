package com.allforyou.app
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query


interface ApiService {
    @Headers("accept: application/json","Content-Type: application/json")

//    @POST("api/auth/login")
//    fun login(@Body loginRequest: LoginRequest): Call<Any>
    @POST("api/auth/login")
    fun login(@Body loginRequest: LoginRequest): Call<AccessTokenResponse>

    @POST("api/auth/signup")
    fun register(@Body registerRequest: RegisterRequest) : Call<RegisterRequest>

    @POST("api/auth/sendcode")
    fun sendCode(@Body phoneAuthenticationRequest: PhoneAuthenticationRequest) : Call<Unit>

    @POST("api/auth/checkcode")
    fun checkCode(@Body phoneAuthenticationRequest: PhoneAuthenticationRequest) : Call<Unit>

    @GET("api/notice/pop")
    fun loadNotice(@Header("Authorization") accessToken : String) : Call<NoticeResponse>
}
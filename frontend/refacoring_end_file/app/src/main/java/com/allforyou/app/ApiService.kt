package com.allforyou.app

import com.allforyou.app.retrofit.ListAllAccountResponse
import com.allforyou.app.retrofit.PaymentRequest
import com.allforyou.app.retrofit.PaymentRequestResponse
import com.allforyou.app.retrofit.TransferConfirmResponse
import com.allforyou.app.retrofit.TransferGetAccountInfoResponse
import com.allforyou.app.retrofit.TransferGetInfoResponse
import com.allforyou.app.retrofit.TransferRequest
import com.allforyou.app.retrofit.TransferResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path


interface ApiService {
    @Headers("accept: application/json", "Content-Type: application/json")


//    로그인관련 api
//    @POST("api/auth/login")
//    fun login(@Body loginRequest: LoginRequest): Call<Any>
    @POST("api/auth/login")
    fun login(@Body loginRequest: LoginRequest): Call<AccessTokenResponse>

    @POST("api/auth/signup")
    fun register(@Body registerRequest: RegisterRequest): Call<RegisterRequest>

    @POST("api/auth/sendcode")
    fun sendCode(@Body phoneAuthenticationRequest: PhoneAuthenticationRequest): Call<Unit>

    @POST("api/auth/checkcode")
    fun checkCode(@Body phoneAuthenticationRequest: PhoneAuthenticationRequest): Call<Unit>

    @GET("api/notice/pop")
    fun loadNotice(@Header("Authorization") accessToken: String): Call<NoticeResponse>


//    계좌 관련 api

    @GET("/api/account/list/all-account")
    fun getAllAccount(@Header("Authorization") accessToken: String):Call<ListAllAccountResponse>


//    @POST("/api/acoount/payment/rfid/{paymentId}")
//    fun paymentRFID(@Header("Authorization") accessToken: String, @Path("paymentId") paymentId:Long):Call<>

    @POST("/api/account/payment/request")
    fun payemntRequest(
        @Header("Authorization") accessToken: String,
        @Body paymentRequest: PaymentRequest
    ): Call<PaymentRequestResponse>


    @POST("/api/account/transfer/confirm/{transferId}")
    suspend fun transferConfirm(
        @Header("Authorization") accessToken: String,
        @Path("transferId") transferId: Long
    ): Call<TransferConfirmResponse>

    @GET("/api/account/transfer/get-account-info")
    fun transferGetAccountInfo(@Header("Authorization") accessToken: String): Call<TransferGetAccountInfoResponse>

    @GET("/api/account/transfer/get-info")
    fun transferGetInfo(@Header("Authorization") accessToken: String): Call<TransferGetInfoResponse>

    @POST("/api/account/transfer/request")
    fun sendTransferRequest(
        @Header("Authorization") accessToken: String,
        @Body transferRequest: TransferRequest
    ): Call<TransferResponse>




}
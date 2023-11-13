package com.allforyou.app
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.HeaderMap
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {
    @Headers("accept: application/json","Content-Type: application/json")

    @POST("api/auth/login")
    fun login(@Body loginRequest: LoginRequest): Call<AccessTokenResponse>

    @POST("api/auth/pincheck")
    fun pinCheck(@Header("Authorization") accessToken : String, @Body pinNumberRequest: PinNumberRequest): Call<Unit>

    @POST("api/auth/signup")
    fun register(@Body registerRequest: RegisterRequest) : Call<RegisterRequest>

    @POST("api/auth/sendcode")
    fun sendCode(@Body phoneAuthenticationRequest: PhoneAuthenticationRequest) : Call<Unit>

    @POST("api/auth/checkcode")
    fun checkCode(@Body phoneAuthenticationRequest: PhoneAuthenticationRequest) : Call<Unit>

    @GET("api/notice/pop")
    fun loadNotice(@Header("Authorization") accessToken : String ) : Call<NoticeResponse>

//    @GET("api/notice/ㅋㅋ/경로")
//    fun 뭐시기(@HeaderMap headers : Map<String,String> ) : Call<NoticeResponse>

    @GET("api/account/list/business-account")
    fun loadBusinessAccounts(@Header("Authorization") accessToken : String) : Call<BusinessAccountResponse>
    @GET("api/account/list/general-account")
    fun loadGeneralAccounts(@Header("Authorization") accessToken : String) : Call<HomeAccountResponse>
    @GET("api/account/list/pet-account")
    fun loadPetAccounts(@Header("Authorization") accessToken : String) : Call<AnimalAccountDetailResponse>
    @GET("api/account/transaction/list/{accountId}")
    fun loadTransactionLog(@Header("Authorization") accessToken : String, @Path("accountId") id : Long) : Call<TransactionLogResponse>

    
}
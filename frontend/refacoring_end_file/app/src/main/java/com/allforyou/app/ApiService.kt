package com.allforyou.app


import com.allforyou.app.retrofit.AccessRequestRespond
import com.allforyou.app.retrofit.AccessRespond
import com.allforyou.app.retrofit.AccessSaveRequest
import com.allforyou.app.retrofit.AccessStatusChangeRequest
import com.allforyou.app.retrofit.ApiRespond
import com.allforyou.app.retrofit.ListAllAccountResponse
import com.allforyou.app.retrofit.PaymentRequest
import com.allforyou.app.retrofit.PaymentRequestResponse
import com.allforyou.app.retrofit.PetAccountRequest
import com.allforyou.app.retrofit.TransferConfirmResponse
import com.allforyou.app.retrofit.TransferGetAccountInfoResponse
import com.allforyou.app.retrofit.TransferGetInfoResponse
import com.allforyou.app.retrofit.TransferRequest
import com.allforyou.app.retrofit.TransferResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.HeaderMap
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {
    @Headers("accept: application/json", "Content-Type: application/json")


//    로그인관련 api
//    @POST("api/auth/login")
//    fun login(@Body loginRequest: LoginRequest): Call<Any>
    @POST("api/auth/login")
    fun login(@Body loginRequest: LoginRequest): Call<AccessTokenResponse>

    @POST("api/auth/pincheck")
    fun pinCheck(@Header("Authorization") accessToken : String, @Body pinNumberRequest: PinNumberRequest): Call<Unit>

    @POST("api/auth/signup")
    fun register(@Body registerRequest: RegisterRequest): Call<RegisterRequest>

    @POST("api/auth/sendcode")
    fun sendCode(@Body phoneAuthenticationRequest: PhoneAuthenticationRequest): Call<Unit>
    @GET("api/notice/pop")
    fun loadNotice(@Header("Authorization") accessToken: String): Call<NoticeResponse>

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

    @GET("/api/account/transaction/recipient-info?")
    fun getTransactionRecipientInfo(@Header("Authorization") accessToken : String, @Query("accountNumber") accountNumber : String, @Query("paymentAmount") amount : Long) : Call<RecipientInfoResponse>

    @POST("/api/account/transaction/pet-info")
    fun remittance(@Header("Authorization") accessToken : String, @Query("accountNumber") rfidCode : String) : Call<PetInfoResponse>

    @POST("api/auth/checkcode")
    fun checkCode(@Body phoneAuthenticationRequest: PhoneAuthenticationRequest): Call<Unit>



//    계좌 관련 api

    @POST("/api/account/access/request")
    suspend fun applyAccessRequest(
        @Header("Authorization") accessToken: String,
        @Body accessSaveRequest: AccessSaveRequest
    ): Response<ApiRespond<AccessRequestRespond>>

    @GET("/api/account/access/receive/list")
    suspend fun getPetAccountAccessApplyList(
        @Header("Authorization") accessToken: String,
        @Query("accountNumber") accountNumber: Int
    ): Response<ApiRespond<List<AccessRespond>>>

        @PUT("/api/account/access/accept")
        suspend fun applyAccept(
            @Header("Authorization") accessToken: String,
            @Body accessId: AccessStatusChangeRequest
        ): Response<ApiRespond<Int>>

        @DELETE("/api/account/access/reject")
        suspend fun applyReject(
            @Header("Authorization") accessToken: String,
            @Body accessId: AccessStatusChangeRequest
        ): Response<ApiRespond<Int>>


        @POST("/api/account/register/pet-account")
        suspend fun makePetAccount(
            @Header("Authorization") accessToken: String,
            @Body petAcountRequest: PetAccountRequest
        ):Response<ApiRespond<Int>>

        @GET("/api/account/charging-account-list")
        suspend fun getChargingAccountList(
            @Header("Authorization") accessToken: String):Response<ApiRespond<List<ChargingAccount>>>




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
    suspend  fun sendTransferRequest(
        @Header("Authorization") accessToken: String,
        @Body transferRequest: TransferRequest
    ): Response<ApiRespond<Int>>

//    @GET("/api/account/transfer/get-info")
//    suspend fun



}
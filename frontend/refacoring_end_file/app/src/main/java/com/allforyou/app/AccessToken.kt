package com.allforyou.app

import com.google.gson.annotations.SerializedName

data class AccessTokenResponse (
    val message: String,
    val code: String,
    val data : AccessToken? = null
){
    data class AccessToken(
        val accessToken: String,
        val refreshToken: String,
        val userName : String
    )
}
object AccessTokenManager {
    private var data: AccessTokenResponse.AccessToken? = null
        fun init(accessToken: AccessTokenResponse.AccessToken){
        data = accessToken
    }
    fun getBearer() : String{
        if(data == null){
            return ""
        }
        return "Bearer "+data!!.accessToken
    }
    fun getInstance(): AccessTokenResponse.AccessToken {
        return data!!
    }
}
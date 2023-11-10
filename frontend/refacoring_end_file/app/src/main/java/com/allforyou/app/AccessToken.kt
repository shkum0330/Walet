package com.allforyou.app

import java.io.Serializable

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
data class AccessToken (
    val accessToken: String,
    val refreshToken: String,
    val userName : String
)
object AccessTokenManager {
    private var data: AccessToken? = null
    fun init(accessToken: AccessToken){
        data = accessToken
    }
    fun getBearer() : String{
        if(data == null){
            return ""
        }
        return "Bearer "+data!!.accessToken
    }
    fun getInstance(): AccessToken {
        return data!!
    }
}
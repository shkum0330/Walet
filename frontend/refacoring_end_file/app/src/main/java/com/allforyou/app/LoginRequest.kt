package com.allforyou.app

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
data class LoginRequest(
    val email: String,
    val password: String,
)
object LoginRequestManager {
    private var data: LoginRequest? = null
    fun initData(request: LoginRequest) {
        data = request
    }

    fun getInstance(): LoginRequest {
        if (data == null) {
            data = LoginRequest("", "")
        }
        return data!!
    }
}
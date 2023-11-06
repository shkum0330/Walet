package com.allforyou.app

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
data class AccessToken(
    val accessToken: String,
    val refreshToken: String
)
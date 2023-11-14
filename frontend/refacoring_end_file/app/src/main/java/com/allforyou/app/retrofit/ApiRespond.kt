package com.allforyou.app.retrofit

data class ApiRespond<T>(
    val code: Int,
    val message: String,
    val data: T
)

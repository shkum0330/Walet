package com.allforyou.app.retrofit

import java.time.LocalDateTime

data class AccessRespond(
    val id:Long,
    val requestName:String,
    val petName:String,
    val content:String,
    val accountNumber:String,
    val requestedTime:LocalDateTime
)

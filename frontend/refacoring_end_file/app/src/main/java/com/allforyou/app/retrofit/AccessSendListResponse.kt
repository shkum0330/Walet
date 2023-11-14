package com.allforyou.app.retrofit

import java.time.LocalDateTime

data class AccessSendListResponse(
    val id: Long,
    val requesterName: String,
    val petName: String,
    val content: String,
    val accountNumber: String,
    val requestedTime: LocalDateTime
)
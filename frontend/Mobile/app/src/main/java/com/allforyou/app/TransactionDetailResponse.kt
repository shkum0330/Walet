package com.allforyou.app

import java.time.LocalDateTime

data class TransactionDetailResponse(
    val recipient:String,
    val businessCategory:String,
    val paymentAmount:Long,
    val balance:Long,
    val transactionTime: LocalDateTime,
    val phoneNumber:String

)

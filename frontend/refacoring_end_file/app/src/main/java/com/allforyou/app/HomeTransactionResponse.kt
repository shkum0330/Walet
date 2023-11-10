package com.allforyou.app

data class HomeTransactionResponse (
    val sender : String,
    val recipient : String,
    var paymentAmount : Long,
    val transactionType : String
)
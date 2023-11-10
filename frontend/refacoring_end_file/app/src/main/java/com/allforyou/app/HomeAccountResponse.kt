package com.allforyou.app

data class HomeAccountResponse (
    val accountId : Long,
    val accountName : String,
    val accountNumber : String,
    var balance : Long,
    val transactions : List<HomeTransactionResponse>
)
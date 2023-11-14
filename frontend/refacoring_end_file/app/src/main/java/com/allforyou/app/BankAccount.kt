package com.allforyou.app

data class BankAccount(
    val accountId: Long,
    val accountName: String,
    val accountNumber: String,
    var balance: Long,
    val transactions: List<HomeTransaction>
)
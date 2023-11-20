package com.allforyou.app

import java.io.Serializable

data class BankAccount(
    val accountId: Long,
    val accountName: String,
    val accountNumber: String,
    var balance: Long,
    val transactions: List<HomeTransaction>
)
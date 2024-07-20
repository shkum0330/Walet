package com.allforyou.app

import android.accounts.Account

data class BusinessAccountResponse (
    val message: String,
    val code: String,
    val data : List<BankAccount>? = null
)
//{
//    data class BusinessAccount(
//        val accountId: Long,
//        val accountName: String,
//        val accountNumber: String,
//        var balance: Long,
//        val transactions: List<HomeTransaction>
//    )
//}
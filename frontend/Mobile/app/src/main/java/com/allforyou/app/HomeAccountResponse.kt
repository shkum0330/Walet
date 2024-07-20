package com.allforyou.app

data class HomeAccountResponse (
    val message: String,
    val code: String,
    val data : List<BankAccount>? = null
)
//{
//    data class HomeAccount(
//        val accountId : Long,
//        val accountName : String,
//        val accountNumber : String,
//        var balance : Long,
//        val transactions : List<HomeTransaction>
//    )
//}

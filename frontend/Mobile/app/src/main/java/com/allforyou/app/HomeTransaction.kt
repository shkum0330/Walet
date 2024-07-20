package com.allforyou.app
data class HomeTransaction(
    val counterPart : String,
    val paymentAmount : Long,
    val transactionType : String
)

//data class HomeTransactionResponse (
//    val message: String,
//    val code: String,
//    val data : HomeTransaction? = null
//){
//    data class HomeTransaction(
//        val sender : String,
//        val recipient : String,
//        var paymentAmount : Long,
//        val transactionType : String
//    )
//}
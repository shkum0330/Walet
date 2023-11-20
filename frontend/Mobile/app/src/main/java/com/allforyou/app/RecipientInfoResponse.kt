package com.allforyou.app

data class RecipientInfoResponse (
    val message: String,
    val code: String,
    val data : RecipientInfo
){
    data class RecipientInfo(
        val accountId : Long,
        val receiverName : String,
        val accountNumber : String,
        var paymentAmout : Long,
    )
}

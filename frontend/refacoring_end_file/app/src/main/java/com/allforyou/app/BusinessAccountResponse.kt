package com.allforyou.app

data class BusinessAccountResponse (
    val message: String,
    val code: String,
    val data : List<HomeAccountResponse>? = null
) {
    data class BusinessAccount(
        val accountId: Long,
        val accountName: String,
        val accountNumber: String,
        var balance: Long,
        val transactions: List<HomeTransaction>
    )
}
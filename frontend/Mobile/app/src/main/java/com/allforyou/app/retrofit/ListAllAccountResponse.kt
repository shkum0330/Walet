package com.allforyou.app.retrofit

data class ListAllAccountResponse(
    val code: Int,
    val data: List<Account1>,
    val message: String
) {
    data class Account1(
        val accountId: Int,
        val accountName: String,
        val accountNumber: String,
        val balance: Int,
        val memberId: Int
    )
}
package com.allforyou.app.retrofit

data class TransferGetAccountInfoResponse(
    val code: Int,
    val `data`: List<Data>,
    val message: String
) {
    data class Data(
        val accountId: Int,
        val accountName: String,
        val accountNumber: String,
        val accountType: String,
        val balance: Int
    )
}
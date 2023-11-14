package com.allforyou.app.retrofit

data class PaymentRequestResponseException(
    val code: Int,
    val `data`: Any,
    val message: String
)
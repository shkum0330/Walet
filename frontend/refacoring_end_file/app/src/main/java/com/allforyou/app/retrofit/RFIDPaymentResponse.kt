package com.allforyou.app.retrofit

data class RFIDPaymentResponse(
    val code: Int,
    val `data`: Data,
    val message: String
) {
    data class Data(
        val petAge: String,
        val petBirth: String,
        val petBreed: String,
        val petGender: String,
        val petImage: String,
        val petName: String,
        val petNeutered: String
    )
}
package com.allforyou.app.retrofit

data class TransferGetInfoResponse(
    val code: Int,
    val `data`: Data,
    val message: String
) {
    data class Data(
        val newOwnerInfo: NewOwnerInfo,
        val petInfo: PetInfo,
        val transferId: Int
    ) {
        data class NewOwnerInfo(
            val content: String,
            val date: String,
            val name: String
        )

        data class PetInfo(
            val petAge: String,
            val petBirth: String,
            val petBreed: String,
            val petGender: String,
            val petImage: String,
            val petName: String,
            val petNeutered: String
        )
    }
}
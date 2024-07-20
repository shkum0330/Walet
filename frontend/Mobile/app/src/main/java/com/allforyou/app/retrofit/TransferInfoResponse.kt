package com.allforyou.app.retrofit

data class TransferInfoResponse(
    val newOwnerInfo: NewOwnerInfo,
    val petInfo: PetInfo,
    val transferId: Long
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
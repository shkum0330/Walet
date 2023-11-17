package com.allforyou.app
data class PetInfoResponse (
    val message: String,
    val code: String,
    val data : PetInfoResponse? = null
){
    data class PetInfoResponse(
        val accountId: Long,
        val petName : String,
        val petGender : String,
        val petBirth : String,
        val petBreed : String,
        val petNeutered : Boolean,
        val petRegistrationDate : String,
        val petWeight : Float,
        val petPhoto : String
    )
}
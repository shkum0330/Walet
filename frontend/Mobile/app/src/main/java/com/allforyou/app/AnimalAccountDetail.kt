package com.allforyou.app
data class AnimalAccountDetailResponse (
    val message: String,
    val code: String,
    val data : List<AnimalAccountDetail>? = null
){
    data class AnimalAccountDetail(
        val id: Long,
        val accountName: String,
        val accountNumber : String,
        val balance : Long,
        val state : String,
        val createdAt : String,
        val accountLimit : Long,
        val accountType : String,
        val linkedAccountId : Long,
        val linkedAccountNumber:String,
        val petName : String,
        val petGender : String,
        val petAge : String,
        val petBreed : String,
        val petNeutered : Boolean,
        val petRegistrationDate : String,
        val petWeight : Float,
        val petPhoto : String,
        val transactions : List<HomeTransaction>
    )
}
package com.allforyou.app.retrofit

data class PetAccountRequest(
    val accountName: String,
    val accountPwd: String,
    val linkedAccountId: Any,
    val petBirth: String,
    val petBreed: String,
    val petGender: String,
    val petName: String,
    val petNeutered: Boolean,
    val petPhoto: String,
    val petWeight: Double,
    val rfidCode: String,
    val limitTypeIdList: List<Int> = listOf()

)
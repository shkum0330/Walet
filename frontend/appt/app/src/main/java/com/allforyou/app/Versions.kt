package com.allforyou.app

data class Versions(
//    private lateinit var codeName, version, apiLevel, description : String
    val codeName : String,
    val version : String,
    val apiLevel : String,
    val description : String,


){
    override fun toString(): String {
        return codeName + version + apiLevel + description
    }
}
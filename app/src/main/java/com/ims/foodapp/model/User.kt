package com.ims.foodapp.model

data class User(
    val userId:String? = null,
    val username:String? = null,
    val email:String? = null,
    val location:String? = null,
    val phoneNumber:String? = null
){
    fun toMap() = mapOf(
        "userId" to userId,
        "username" to username,
        "email" to email,
        "location" to location,
        "phoneNumber" to phoneNumber
    )
}

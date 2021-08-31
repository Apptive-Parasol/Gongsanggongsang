package com.example.userapp.data.dto

import com.example.userapp.data.entity.User

data class UserModel(
    val id: String = "",
    val name: String = "",
    val nickname: String = "",
    val birth: String = "",
    val phone: String = "",
    val agency : String = ""/*,
    val fcmTokenList : List<String> = listOf()*/
){
    fun getUserEntity() : User { return User(id, name, nickname, birth, phone, agency) }
}
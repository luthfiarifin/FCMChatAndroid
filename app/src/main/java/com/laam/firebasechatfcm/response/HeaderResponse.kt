package com.laam.firebasechatfcm.response

data class HeaderResponse(
    val id: Int,
    val lastMessage: LastMessage?,
    val user1: User,
    val user2: User
)
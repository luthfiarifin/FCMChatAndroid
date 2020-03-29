package com.laam.firebasechatfcm.response

data class LastMessage(
    val createdAt: Long,
    val id: Int,
    val message: String?,
    val user: User
)
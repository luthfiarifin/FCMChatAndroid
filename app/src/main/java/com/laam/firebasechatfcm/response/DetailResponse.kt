package com.laam.firebasechatfcm.response

data class DetailResponse(
    val createdAt: Long,
    val header: Int,
    val id: Int,
    val message: String,
    val pictureUrl: String?,
    val updatedAt: Long,
    val sender: User
)
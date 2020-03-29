package com.laam.firebasechatfcm.response

data class SendMessageResponse(
    val message: String,
    val result: List<DetailResponse>
)
package com.laam.firebasechatfcm.network

import com.laam.firebasechatfcm.response.DetailResponse
import com.laam.firebasechatfcm.response.HeaderResponse
import com.laam.firebasechatfcm.response.SendMessageResponse
import com.laam.firebasechatfcm.response.UpdateTokenResponse
import retrofit2.Call
import retrofit2.http.*

interface Api {

    @GET("headerchatperson/find_header/{id}")
    fun getHeader(
        @Path("id") id: Int
    ): Call<List<HeaderResponse>>

    @GET("detailchatperson/find_detail/{header}")
    fun getDetail(
        @Path("header") headerId: Int
    ): Call<List<DetailResponse>>

    @FormUrlEncoded
    @POST("detailchatperson/send_message")
    fun sendMessage(
        @Field("message") message: String,
        @Field("header") header: Int,
        @Field("sender") sender: Int
    ): Call<SendMessageResponse>

    @FormUrlEncoded
    @PATCH("user/update_token/{id}")
    fun updateToken(
        @Path("id") id: Int,
        @Field("token") token: String
    ): Call<UpdateTokenResponse>
}
package com.laam.firebasechatfcm.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.iid.FirebaseInstanceId
import com.laam.firebasechatfcm.R
import com.laam.firebasechatfcm.adapter.HeaderAdapter
import com.laam.firebasechatfcm.id
import com.laam.firebasechatfcm.network.Api
import com.laam.firebasechatfcm.network.ServiceBuilder
import com.laam.firebasechatfcm.response.HeaderResponse
import kotlinx.android.synthetic.main.activity_header_chat.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HeaderChatActivity : AppCompatActivity() {
    val TAG = "HeaderChatActivity";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_header_chat)

        rv_header.layoutManager = LinearLayoutManager(this)

        ServiceBuilder.buildService(Api::class.java)
            .getHeader(id)
            .enqueue(object : Callback<List<HeaderResponse>> {
                override fun onFailure(call: Call<List<HeaderResponse>>, t: Throwable) {
                    Log.d(TAG, "onFailure: ${t.message}")
                }

                override fun onResponse(
                    call: Call<List<HeaderResponse>>,
                    response: Response<List<HeaderResponse>>
                ) {
                    response.body()?.let {
                        rv_header.adapter = HeaderAdapter(it)
                    }
                }
            })
    }
}

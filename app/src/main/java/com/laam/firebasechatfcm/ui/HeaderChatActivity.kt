package com.laam.firebasechatfcm.ui

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.laam.firebasechatfcm.R
import com.laam.firebasechatfcm.adapter.HeaderAdapter
import com.laam.firebasechatfcm.firebase.MessageEvent
import com.laam.firebasechatfcm.id
import com.laam.firebasechatfcm.network.Api
import com.laam.firebasechatfcm.network.ServiceBuilder
import com.laam.firebasechatfcm.response.HeaderResponse
import kotlinx.android.synthetic.main.activity_header_chat.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HeaderChatActivity : AppCompatActivity() {
    val TAG = "HeaderChatActivity";

    private val adapter = HeaderAdapter(listOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_header_chat)

        EventBus.getDefault().register(this)

        rv_header.layoutManager = LinearLayoutManager(this)
        rv_header.adapter = adapter

        refreshRv()
    }

    private fun refreshRv() {
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
                        adapter.updateList(it)
                    }
                }
            })
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(messageEvent: MessageEvent) {
        Toast.makeText(this, "msg : ${messageEvent.data}", Toast.LENGTH_SHORT).show()
        refreshRv()
    }

    override fun onDestroy() {
        super.onDestroy()

        EventBus.getDefault().unregister(this)
    }
}

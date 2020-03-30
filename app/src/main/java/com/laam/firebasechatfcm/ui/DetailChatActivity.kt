package com.laam.firebasechatfcm.ui

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.gson.GsonBuilder
import com.laam.firebasechatfcm.firebase.MessageEvent
import com.laam.firebasechatfcm.R
import com.laam.firebasechatfcm.adapter.DetailAdapter
import com.laam.firebasechatfcm.id
import com.laam.firebasechatfcm.network.Api
import com.laam.firebasechatfcm.network.ServiceBuilder
import com.laam.firebasechatfcm.response.DetailResponse
import com.laam.firebasechatfcm.response.SendMessageResponse
import kotlinx.android.synthetic.main.activity_detail_chat.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailChatActivity : AppCompatActivity() {
    val TAG = "DetailChatActivity";
    var adapter = DetailAdapter(mutableListOf())
    var headerID = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_chat)

        EventBus.getDefault().register(this)

        headerID = intent.getIntExtra("header", 0)

        rv_detail.layoutManager = LinearLayoutManager(this)
        rv_detail.adapter = adapter

        refreshRv()

        btn_send_message.setOnClickListener {
            val message = et_send_message.text.toString()

            ServiceBuilder.buildService(Api::class.java).sendMessage(
                message,
                headerID,
                id
            ).enqueue(object : Callback<SendMessageResponse> {
                override fun onFailure(call: Call<SendMessageResponse>, t: Throwable) {
                    Log.d(TAG, "onFailure: ${t.message}")
                }

                override fun onResponse(
                    call: Call<SendMessageResponse>,
                    response: Response<SendMessageResponse>
                ) {
                    response.body()?.let {
                        addList(it.result)
                        Log.d(TAG, "${it.result}")
                    }
                }
            })
        }

        btn_send_library.setOnClickListener {
            ImagePicker.with(this)
                .compress(512)
                .start { resultCode, data ->
                    if (resultCode == Activity.RESULT_OK) {
                        val file = ImagePicker.getFile(data)!!

                        ServiceBuilder.buildService(Api::class.java)
                            .sendMessageWithImage(
                                MultipartBody.Part.createFormData(
                                    "imageFile",
                                    file.name,
                                      RequestBody.create(MediaType.parse("multipart/form-file"), file)
                                ),
                                headerID,
                                id
                            )
                            .enqueue(object : Callback<SendMessageResponse> {
                                override fun onFailure(call: Call<SendMessageResponse>, t: Throwable) {
                                    Log.d(TAG, "onFailure: ${t.message}")
                                }

                                override fun onResponse(
                                    call: Call<SendMessageResponse>,
                                    response: Response<SendMessageResponse>
                                ) {
                                    response.body()?.let {
                                        Log.d(TAG, "${it.result}")
                                        addList(it.result)
                                    }
                                    Log.d(TAG, "onErrorResponse: ${response.errorBody()?.string().toString()}")
                                }
                            })
                    } else {
                        Toast.makeText(this, "task cancelled", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    private fun refreshRv() {
        ServiceBuilder.buildService(Api::class.java)
            .getDetail(headerID)
            .enqueue(object : Callback<List<DetailResponse>> {
                override fun onFailure(call: Call<List<DetailResponse>>, t: Throwable) {
                    Log.d(TAG, "onFailure: ${t.message}")
                }

                override fun onResponse(
                    call: Call<List<DetailResponse>>,
                    response: Response<List<DetailResponse>>
                ) {
                    response.body()?.let {
                        updateList(it)
                    }
                }
            })
    }

    fun updateList(list: List<DetailResponse>) {
        adapter.updateList(list)

        rv_detail.scrollToPosition(adapter.itemCount - 1)
        rv_detail.scrollTo(0, adapter.itemCount - 1)
    }

    fun addList(item: DetailResponse) {
        adapter.addItem(item)

        rv_detail.scrollToPosition(adapter.itemCount - 1)
        rv_detail.scrollTo(0, adapter.itemCount - 1)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(messageEvent: MessageEvent) {
        val detailResponse = GsonBuilder().create()
            .fromJson(messageEvent.data["body"].toString(), DetailResponse::class.java)
        addList(detailResponse)
    }

    override fun onDestroy() {
        super.onDestroy()

        EventBus.getDefault().unregister(this)
    }
}

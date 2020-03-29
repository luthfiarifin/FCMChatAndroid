package com.laam.firebasechatfcm.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.iid.FirebaseInstanceId
import com.laam.firebasechatfcm.R
import com.laam.firebasechatfcm.id
import com.laam.firebasechatfcm.network.Api
import com.laam.firebasechatfcm.network.ServiceBuilder
import com.laam.firebasechatfcm.response.UpdateTokenResponse
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_login.setOnClickListener {
            id = et_id.text.toString().toInt()

            Log.d(TAG, "token: ${FirebaseInstanceId.getInstance().token}")

            ServiceBuilder.buildService(Api::class.java)
                .updateToken(id, FirebaseInstanceId.getInstance().token.toString())
                .enqueue(object : Callback<UpdateTokenResponse>{
                    override fun onFailure(call: Call<UpdateTokenResponse>, t: Throwable) {
                        Log.d(TAG, "onFailure: ${t.message}")
                    }

                    override fun onResponse(
                        call: Call<UpdateTokenResponse>,
                        response: Response<UpdateTokenResponse>
                    ) {
                        response.body()?.let {
                            Toast.makeText(this@MainActivity, it.message, Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this@MainActivity, HeaderChatActivity::class.java))
                        }
                    }
                })
        }
    }
}

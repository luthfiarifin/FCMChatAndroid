package com.laam.firebasechatfcm.firebase

import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.greenrobot.eventbus.EventBus

class MyFirebaseInstanceIDService : FirebaseMessagingService() {
    private val TAG = "MyFirebaseInstanceID";

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)

        Log.d(TAG, "onNewToken: ${FirebaseInstanceId.getInstance().token}")
        Log.d(TAG, "onNewToken: string : $p0")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        Log.d(TAG, "From: ${remoteMessage.from}")

        // Check if message contains a data payload.
        remoteMessage.data.isNotEmpty().let {
            Log.d(TAG, "Message data payload: " + remoteMessage.data)

            if (remoteMessage.data["type"] == "personal_message") {
                EventBus.getDefault().post(
                    MessageEvent(
                        remoteMessage.data
                    )
                )
            }
        }

        // Check if message contains a notification payload.
        remoteMessage.notification?.let {
            Log.d(TAG, "Message Notification Body: ${it.body}")
        }

    }
}
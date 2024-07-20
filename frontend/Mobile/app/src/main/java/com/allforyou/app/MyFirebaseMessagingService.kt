package com.allforyou.app

import android.content.Intent
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // Handle incoming messages here
        Log.d(TAG, "From: ${remoteMessage.from}")

        // Check if message contains a data payload
        remoteMessage.data.isNotEmpty().let {
            Log.d(TAG, "Message data payload: ${remoteMessage.data}")


            // Handle the data payload
        }

        // Check if message contains a notification payload
        remoteMessage.notification?.let {
            Log.d(TAG, "Message Notification Body: ${it.body}")
            // Handle the notification payload
        }
    }

    override fun onNewToken(token: String) {
        Log.d(TAG, "Refreshed token: $token")

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // FCM registration token to your app server.
        sendRegistrationToServer(token)
    }

    fun sendRegistrationToServer(token : String){
        var retrofitAPI = RetrofitClient.getClient()
//        Log.d("FCM",)
        retrofitAPI.sendFCMToken(AccessTokenManager.getBearer(),FCMTokenRequest(token)).enqueue(object :
            Callback<Unit> {
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if (response.isSuccessful) {
                    Log.d("my_tag", "ResponseBody $response");

                } else {
                    // Handle unsuccessful response
//                    onFailure(call, retrofitAPI.)
                }
            }

            override fun onFailure(call: Call<Unit>, t: Throwable) {
            }

        })
    }

    companion object {
        private const val TAG = "MyFirebaseMsgService"
    }
}
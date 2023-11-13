package com.allforyou.app

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.allforyou.app.databinding.ActivityTextBinding
import com.allforyou.app.retrofit.TransferRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.awaitResponse

class TextActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTextBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTextBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
//            GlobalScope.launch {
//                sendTransferRequest()
//            }


        }
    }

//    private suspend fun sendTransferRequest() {
//        val newOwner = binding.newOwner.text.toString()
//        val accountNumber = binding.accountNumber.text.toString()
//        val content = binding.content.text.toString()
//
//        // 여기에서 Retrofit을 사용하여 API 호출 수행
//        val retrofitAPI = RetrofitClient.getClient()
//
//        val header:String=AccessTokenManager.getBearer()
//
//        try {
//            val transferResponse = withContext(Dispatchers.IO) {
//                retrofitAPI.sendTransferRequest(header, TransferRequest(newOwner, accountNumber, content))
//                    .awaitResponse()
//                    .body()
//            }
//
//            if (transferResponse != null) {
//                Log.d("TextActivity", "Transfer ID: ${transferResponse.data?.transferId}")
//            } else {
//                Log.e("TextActivity", "Failed to send transfer request")
//            }
//        } catch (e: Exception) {
//            Log.e("TextActivity", "Exception during transfer request", e)
//        }
//    }
}
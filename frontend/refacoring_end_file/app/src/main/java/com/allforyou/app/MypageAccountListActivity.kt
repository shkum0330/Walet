package com.allforyou.app

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.allforyou.app.databinding.ActivityMypageAccountListBinding
import com.allforyou.app.retrofit.ListAllAccountResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Response

class MypageAccountListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMypageAccountListBinding
    private val retrofitAPI = RetrofitClient.getClient()
//    private val accountListAdapter = MypageAccountListAdapter(emptyList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMypageAccountListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recycler.layoutManager = LinearLayoutManager(this)
//        binding.recycler.adapter = accountListAdapter

//        fetchAccountList()
    }

//    private fun fetchAccountList() {
//        val accessToken = "your_access_token_here" // Replace with your actual access token
//
//        // Use MainScope to launch a coroutine on the main thread
//        MainScope().launch {
//            try {
//                val response = withContext(Dispatchers.IO) {
//                    retrofitAPI.getAllAccount(AccessTokenManager.getBearer())
//                }
//                Log.d("api 호출 결과", response.toString())
//                handleResponse(response)
//            } catch (e: Exception) {
//                handleException(e)
//            }
//        }
//    }
//
//    private fun handleResponse(response: Response<ListAllAccountResponse>?) {
//        if (response != null && response.isSuccessful) {
//            val responseData = response.body()?.data
//            if (responseData != null) {
//                // Update the adapter with the fetched data
//                accountListAdapter.updateData(responseData)
//            } else {
//                Log.e("MypageAccountListActivity", "Response body is null or empty")
//            }
//        } else {
//            Log.e("MypageAccountListActivity", "Failed to fetch account list. Response: $response")
//        }
//    }
//
//
//
//    private fun handleException(exception: Exception) {
//        // Handle the exception, e.g., show an error message
//        Log.e("MypageAccountListActivity", "Exception: ${exception.message}", exception)
//    }
}

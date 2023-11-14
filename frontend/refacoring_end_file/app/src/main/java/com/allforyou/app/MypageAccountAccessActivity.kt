package com.allforyou.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.allforyou.app.databinding.ActivityMypageAccountAccessBinding
import com.allforyou.app.retrofit.AccessRequestRespond
import com.allforyou.app.retrofit.AccessRespond
import com.allforyou.app.retrofit.AccessSaveRequest
import com.allforyou.app.retrofit.AccessStatusChangeRequest
import com.allforyou.app.retrofit.ApiRespond
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Response

class MypageAccountAccessActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMypageAccountAccessBinding
    private lateinit var apiService: ApiService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMypageAccountAccessBinding.inflate(layoutInflater)
        setContentView(binding.root)

        apiService = RetrofitClient.getClient()
        val applyName = binding.accessApplyName
        val applyDate = binding.accessApplyDate
        val applyContent = binding.accessApplyContent
        val applyOkBtn = binding.accessApplyOk
        val applyNoBtn = binding.accessApplyNo

        // UI elements to display access applications
        val accessListLayout = binding.accessListLayout  // Assuming it's a LinearLayout or another ViewGroup

        GlobalScope.launch(Dispatchers.Main) {
            try {
                // ApiService 인터페이스를 통해 호출
                val response: Response<ApiRespond<List<AccessRespond>>> =
                    apiService.getPetAccountAccessApplyList(AccessTokenManager.getBearer(), 36)

                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    Log.d("접근 신청 목록 결과",apiResponse.toString())

                    // Check the code and handle the response accordingly
                    when (apiResponse?.code) {
                        200 -> {
                            // Success, handle the list of access applications
                            val accessList = apiResponse.data

                            // Iterate through the list and display information
                            for (access in accessList) {
                                applyName.text = access.requestName
                                applyDate.text = access.requestedTime.toString()
                                applyContent.text = access.content

                                applyOkBtn.setOnClickListener {
                                    // Call the applyAccept function when the "수락" button is clicked
                                    acceptAccessRequest(access.id)
                                }

                                applyNoBtn.setOnClickListener({
                                    rejectAccessRequest(access.id)
                                })

//                                accessListLayout.addView(applyName)

                                // You can similarly add other TextViews or UI elements
                            }
                        }
                        // Handle other response codes if needed
                        else -> {
                            // Handle other cases
                        }
                    }
                } else {
                    // Handle unsuccessful response
                    // You might want to check response.errorBody() for more details
                }
            } catch (e: Exception) {
                // Handle exception
                e.printStackTrace()
            }
        }
    }
    private fun acceptAccessRequest(accessId: Long) {
        lifecycleScope.launch {
            try {
                // Call the applyAccept function from ApiService to accept the access request
                val response: Response<ApiRespond<Int>> =
                    apiService.applyAccept(AccessTokenManager.getBearer(), AccessStatusChangeRequest(accessId))



                if (response.isSuccessful) {
                    // Handle successful acceptance
                    Log.d("열람권한 수락 api 요청 결과", response.body().toString())
                    Toast.makeText(this@MypageAccountAccessActivity, "Access request accepted", Toast.LENGTH_SHORT).show()
                } else {
                    // Handle unsuccessful response
                    // You might want to check response.errorBody() for more details
                    Log.d("열람권한 수락 api 요청 결과", response.body().toString())
                    Toast.makeText(this@MypageAccountAccessActivity, "Failed to accept access request", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                // Handle exception
                e.printStackTrace()
            }
        }
    }

    private fun rejectAccessRequest(accessId: Long) {
        lifecycleScope.launch {
            try {
                // Call the applyAccept function from ApiService to accept the access request
                val response: Response<ApiRespond<Int>> =
                    apiService.applyReject(AccessTokenManager.getBearer(), AccessStatusChangeRequest(accessId))



                if (response.isSuccessful) {
                    // Handle successful acceptance
                    Log.d("열람권한 거철 api 요청 결과", response.body().toString())
                    Toast.makeText(this@MypageAccountAccessActivity, "Access request rejected", Toast.LENGTH_SHORT).show()
                } else {
                    // Handle unsuccessful response
                    // You might want to check response.errorBody() for more details
                    Log.d("열람권한 거절 api 요청 결과", response.body().toString())
                    Toast.makeText(this@MypageAccountAccessActivity, "Failed to reject access request", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                // Handle exception
                e.printStackTrace()
            }
        }
    }

}
package com.allforyou.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telecom.Call
import android.util.Log
import android.widget.Toast
import com.allforyou.app.databinding.ActivityRequestViewBinding
import com.allforyou.app.retrofit.AccessRequestRespond
import com.allforyou.app.retrofit.AccessSaveRequest
import com.allforyou.app.retrofit.ApiRespond
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Response

class RequestViewActivity : AppCompatActivity() {

    private lateinit var binding:ActivityRequestViewBinding
    private lateinit var apiService: ApiService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityRequestViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        apiService = RetrofitClient.getClient()

        binding.doApply.setOnClickListener({
            val petName :String= binding.petName.text.toString()
            val petAccountNumber:String = binding.petAccountNumber.text.toString()
            val petViewRequest:String = binding.petViewRequest.text.toString()

            val accessSaveRequest = AccessSaveRequest(petName, petAccountNumber, petViewRequest)

            GlobalScope.launch(Dispatchers.Main) {
                try {
                    // ApiService 인터페이스를 통해 호출
                    val response: Response<ApiRespond<AccessRequestRespond>> =
                        apiService.applyAccessRequest(AccessTokenManager.getBearer(), accessSaveRequest)

                    if (response.isSuccessful) {
                        val apiResponse = response.body()
                        Log.d("신청 결과", apiResponse.toString())

                        // Check the code and handle the response accordingly
                        when (apiResponse?.code) {
                            200 -> {
                                val accessRequestPK = apiResponse.data.accessRequestPK
                                val intent = Intent(this@RequestViewActivity, RequestViewEndActivity::class.java)
                                startActivity(intent)
                            }
                            204 -> {
                                Toast.makeText(
                                    this@RequestViewActivity,
                                    "반려동물의 이름 또는 계좌번호가 잘못 입력되었습니다.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            400 -> {
                                Toast.makeText(
                                    this@RequestViewActivity,
                                    "이미 허용되었거나 현재 요청 중입니다.",
                                    Toast.LENGTH_SHORT
                                ).show()
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

        })


    }
}
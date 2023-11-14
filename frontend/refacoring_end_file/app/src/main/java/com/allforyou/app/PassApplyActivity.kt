package com.allforyou.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.allforyou.app.databinding.ActivityPassApplyBinding
import com.allforyou.app.retrofit.ApiRespond
import com.allforyou.app.retrofit.TransferRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Response

class PassApplyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPassApplyBinding
    private lateinit var apiService: ApiService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityPassApplyBinding.inflate(layoutInflater)

        setContentView(binding.root)




        apiService = RetrofitClient.getClient()
        binding.doApply.setOnClickListener({

            val newOwner=binding.newOwner.text.toString()
            val accountNumber=binding.accountNumber.text.toString()
            val content=binding.content.text.toString()

           val transferRequest=TransferRequest(newOwner,accountNumber,content)
            Log.d("입력된 내용","${newOwner} ${accountNumber} ${content}")
            Log.d("입력된 내용","${transferRequest} ")

            GlobalScope.launch(Dispatchers.Main) {
                try {
                    // ApiService 인터페이스를 통해 호출
                    val response: Response<ApiRespond<Int>> =
                        apiService.sendTransferRequest(AccessTokenManager.getBearer(), transferRequest)

                    if (response.isSuccessful) {
                        val apiResponse = response.body()
                        Log.d("양도 신청 결과", apiResponse.toString())

                        // Check the code and handle the response accordingly
                        when (apiResponse?.code) {

                            200 -> {
                                // Success, handle the list of access applications
                                val accessList = apiResponse.data
                                Toast.makeText(
                                    this@PassApplyActivity,
                                    "양도 신청 성공!",
                                    Toast.LENGTH_SHORT
                                ).show()

                                val intent= Intent(this@PassApplyActivity, PassCompleteActivity::class.java)
                                startActivity(intent)


                            }
                            // Handle other response codes if needed
                            else -> {
                                // 처리할 다른 응답 코드가 있다면 여기에 작성
                            }
                        }
                    } else {
                        // Handle unsuccessful response
                        // You might want to check response.errorBody() for more details
                    }
                } catch (e: Exception) {
                    // Handle exception
                    Log.d("예외발생", "계좌 불러오기 실패")
                    e.printStackTrace()
                }
            }






        })



    }
}
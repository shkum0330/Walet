package com.allforyou.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.allforyou.app.databinding.ActivityPassAccessAccountListBinding
import com.allforyou.app.databinding.ActivityPassRfidactivityBinding
import com.allforyou.app.retrofit.ApiRespond
import com.allforyou.app.retrofit.TransferConfirmRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class PassRFIDActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPassRfidactivityBinding
    private val apiService = RetrofitClient.getClient()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityPassRfidactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.next.setOnClickListener {

            val senderId: Long = intent.getLongExtra("senderId", -1)
            Log.d("senderID!!!!!!!!!!!!!!!!!!!!!!!", "${senderId}")

            val transferConfirmRequest = TransferConfirmRequest(senderId, "4127421015")

            lifecycleScope.launch(Dispatchers.Main) {
                try {
                    // 비동기적으로 API 호출 수행
                    val response: Response<ApiRespond<Int?>> =
                        apiService.doConfirm(AccessTokenManager.getBearer(), transferConfirmRequest)

                    if (response.isSuccessful) {
                        val apiResponse = response.body()

                        Log.d("수락해본 결과는 !!!!", apiResponse.toString())

                        // 코드를 확인하고 응답에 따라 처리
                        when (apiResponse?.code) {
                            200 -> {
                                // 성공 시 처리
                                val result = apiResponse.data
                                Toast.makeText(
                                    this@PassRFIDActivity,
                                    "양도신청을 수락했습니다",
                                    Toast.LENGTH_SHORT
                                ).show()

                                val intent= Intent(this@PassRFIDActivity, PassAcceptCompleteActivity::class.java)
                                startActivity(intent)
                            }
                            // 다른 응답 코드에 대한 처리도 추가 가능
                            else -> {
                                // 다른 경우 처리
                            }
                        }
                    } else {
                        // 응답이 성공하지 않았을 때 처리
                        // response.errorBody()를 사용하여 자세한 내용을 확인할 수 있습니다.
                    }
                } catch (e: Exception) {
                    // 예외 처리
                    e.printStackTrace()
                }
            }
        }

    }
}
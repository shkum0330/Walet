package com.allforyou.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.allforyou.app.databinding.ActivityPayIdentificationBinding
import com.allforyou.app.databinding.ActivityPayRequestBinding
import com.allforyou.app.retrofit.ApiRespond
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Response

class PayRequestActivity : AppCompatActivity() {
    private lateinit var binding : ActivityPayRequestBinding
    private lateinit var apiService: ApiService
    private var paymentId:Long=0L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityPayRequestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        apiService = RetrofitClient.getClient()

        binding.btnAccept.setOnClickListener {
            doPay()




            val intent = Intent(this, PayCompletePositiveActivity::class.java)
            startActivity(intent)
        }
        // 실패시 PayCompleteNegativeActivity로 이동

        binding.btnCancel.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }






        //FCM기반으로 결제요청 내용 확인
        updateUI()



    }

    private fun doPay() {
        //결제 요청
        GlobalScope.launch(Dispatchers.Main) {
            try {
                // ApiService 인터페이스를 통해 호출
                val response: Response<ApiRespond<Long>> =
                    apiService.doPayment(AccessTokenManager.getBearer(), paymentId)

                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    Log.d("결제 요청함", apiResponse.toString())


                    // Check the code and handle the response accordingly
                    when (apiResponse?.code) {
                        200 -> {
                            // Success, handle the list of access applications
                            val info = apiResponse.data

                            val intent = Intent(this@PayRequestActivity, PayCompletePositiveActivity::class.java)
                            RemittanceRequestManager.paymentId=paymentId
                            startActivity(intent)

                        }
                        // Handle other response codes if needed
                        else -> {
//                            Toast.makeText(
//                                this@PayRequestActivity,
//                                "결제정보를 불러오는데 실패했습니다",
//                                Toast.LENGTH_SHORT
//                            ).show()
                            // Handle other cases
                        }
                    }
                } else {
                }
            } catch (e: Exception) {
                // Handle exception
                Log.d("계좌 발급 결과", "실패 삐빅")
                e.printStackTrace()
            }
        }


    }


    private fun updateUI(){

        //FCM기반으로 결제요청 내용 확인
        GlobalScope.launch(Dispatchers.Main) {
            try {
                // ApiService 인터페이스를 통해 호출
                val response: Response<ApiRespond<PaymentInfo>> =
                    apiService.getPaymentInfo(AccessTokenManager.getBearer())

                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    Log.d("결제요청내용 확인", apiResponse.toString())


                    // Check the code and handle the response accordingly
                    when (apiResponse?.code) {
                        200 -> {
                            // Success, handle the list of access applications
                            val info:PaymentInfo = apiResponse.data


                            binding.storeName.text=info.name+" "+info.businessType
                            binding.storeAddress.text="서울특별시 강남구 테헤란로212 1층 102호"
                            binding.storeDate.text=info.date
                            paymentId=info.paymentId
                            binding.storeAmount.text=info.amount.toString()


//                            binding.storeCall.text=info.phoneNumber
                        }
                        // Handle other response codes if needed
                        else -> {
                            Toast.makeText(
                                this@PayRequestActivity,
                                "결제정보를 불러오는데 실패했습니다",
                                Toast.LENGTH_SHORT
                            ).show()
                            // Handle other cases
                        }
                    }
                } else {
                }
            } catch (e: Exception) {
                // Handle exception
                Log.d("계좌 발급 결과", "실패 삐빅")
                e.printStackTrace()
            }
        }

    }
}
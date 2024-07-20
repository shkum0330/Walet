package com.allforyou.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.allforyou.app.databinding.ActivityPayIdentificationBinding
import com.allforyou.app.databinding.ActivityPayRecognitionChipBinding
import com.allforyou.app.retrofit.ApiRespond
import com.allforyou.app.retrofit.PaymentRequest
import com.bumptech.glide.Glide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Response
import com.squareup.picasso.Picasso

class PayIdentificationActivity : AppCompatActivity() {
    private lateinit var binding : ActivityPayIdentificationBinding
    private lateinit var petInfo : PetInfoResponse
    private lateinit var apiService: ApiService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityPayIdentificationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        apiService = RetrofitClient.getClient()

        binding.confirm.setOnClickListener {

            //결제 요청을 날림

            GlobalScope.launch(Dispatchers.Main) {
                try {
                    // ApiService 인터페이스를 통해 호출
                    val response: Response<ApiRespond<Long>> =
                        apiService.sendPayRequest(
                            AccessTokenManager.getBearer(), PaymentAmount(RemittanceRequestManager.getInstance().remittanceAmount)
                        )

                    if (response.isSuccessful) {
                        val apiResponse = response.body()
                        Log.d("결제요청을 고객에게 보냅니다", apiResponse.toString())


                        // Check the code and handle the response accordingly
                        when (apiResponse?.code) {
                            200 -> {
//                                결제 아이디
                                val payId= apiResponse.data

                                    Toast.makeText(
                                        this@PayIdentificationActivity,
                                        "결제 요청이 전송되었습니다",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                val inp=apiResponse.data
                                Log.d("강아지 정보 : ", inp.toString())



                                val intent =
                                    Intent(
                                        this@PayIdentificationActivity,
                                        PayCompleteStoreActivity::class.java
                                    )

                                startActivity(intent)
                            }
                            // Handle other response codes if needed
                            else -> {
                                Toast.makeText(
                                    this@PayIdentificationActivity,
                                    "결제 요청 전송에 실패했습니다",
                                    Toast.LENGTH_SHORT
                                ).show()
                                Log.d("결제 요청 전송에 실패","결제 요청 전송에 실패" )



                            }
                        }
                    } else {
                        // Handle unsuccessful response
                        // You might want to check response.errorBody() for more details
                    }
                } catch (e: Exception) {
                    // Handle exception
                    Log.d("결제 요청 전송에 실패","결제 요청 전송에 실패" )
                    e.printStackTrace()
                }
                val intent =
                    Intent(
                        this@PayIdentificationActivity,
                        MainActivity::class.java
                    )

                startActivity(intent)


            }


            // 사업장 쪽은 원래 MainActivity로 가야함
            val intent = Intent(this, PayRequestActivity::class.java)
            startActivity(intent)


        }

        binding.no.setOnClickListener {
            // 사업장 쪽은 원래 MainActivity로 가야함
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


        setUI()



    }

    private fun setUI() {
        @Suppress("DEPRECATION")
        val petInfo:PetInfoResponse? = intent.getParcelableExtra<PetInfoResponse>("petInfo")

        if(petInfo==null){
            Toast.makeText(this, "펫 정보를 불러오는데 실패했습니다",Toast.LENGTH_SHORT).show()
            Log.d("문제 발생", "펫 정보 불러오기 실패")


            Log.d("문제 발생", "내장칩 인식페이지로 재 이동")
            val intent=Intent(this, PayRecognitionActivity::class.java)
            startActivity(intent)
        }

//        pet info가 null이 아니라면
        else{
            binding.accountId.text=petInfo.accountId.toString()
            binding.petAge.text=petInfo.petAge+"살"
            binding.petName.text=petInfo.petName
            binding.petBirth.text=petInfo.petBirth+"생"
            binding.petGender.text=petInfo.petGender
            binding.petBreed.text=petInfo.petBreed
            binding.petNeutered.text=petInfo.petNeutered


            Glide.with(this@PayIdentificationActivity)
                .load(petInfo.petPhoto)
                .into(binding.petImage)

        }



    }
}
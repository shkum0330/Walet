package com.allforyou.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.allforyou.app.databinding.ActivityPassDetailBinding
import com.allforyou.app.retrofit.ApiRespond
import com.allforyou.app.retrofit.TransferConfirmRequest
import com.allforyou.app.retrofit.TransferInfoResponse
import com.bumptech.glide.Glide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Response

class PassDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPassDetailBinding
    private val apiService = RetrofitClient.getClient()
    private var senderId: Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPassDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Call the function to retrieve transfer info
        getTransferInfo()

        binding.btnPrevious.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }



//        binding.seeAccessableAccounts.setOnClickListener{
//            val intent = Intent(this, PassAccessAccountListActivity::class.java)
//            startActivity(intent)
//        }

        binding.btnDoConfirm.setOnClickListener{



            GlobalScope.launch(Dispatchers.Main) {
                try {
                    // ApiService 인터페이스를 통해 호출
                    val response: Response<ApiRespond<Long>> =
                        apiService.getPetAccount(AccessTokenManager.getBearer(), RemittanceRequestManager.transferId, Reqquest( "FA06F181"))

                    if (response.isSuccessful) {
                        val apiResponse = response.body()
                        Log.d("계좌 발급 결과", apiResponse.toString())


                        // Check the code and handle the response accordingly
                        when (apiResponse?.code) {
                            200 -> {
                                // Success, handle the list of access applications
                                val accessList = apiResponse.data
                                Toast.makeText(
                                    this@PassDetailActivity,
                                    "펫계좌 양도가 완료되었습니다",
                                    Toast.LENGTH_SHORT
                                ).show()
                                val intent =
                                    Intent(this@PassDetailActivity, PassCompleteActivity::class.java)
                                startActivity(intent)
                            }
                            // Handle other response codes if needed
                            else -> {
                                Toast.makeText(
                                    this@PassDetailActivity,
                                    "펫계좌가 양도에 실패했습니다",
                                    Toast.LENGTH_SHORT
                                ).show()
                                // Handle other cases
                            }
                        }
                    } else {
                        // Handle unsuccessful response
                        // You might want to check response.errorBody() for more details
                    }
                } catch (e: Exception) {
                    // Handle exception
                    Log.d("계좌 발급 결과", "실패 삐빅ㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱ")
                    e.printStackTrace()

                    val intent =
                        Intent(this@PassDetailActivity, PassCompleteActivity::class.java)
                    startActivity(intent)
                }
            }







//
//            val intent=Intent(this, PassRFIDActivity::class.java)
//            intent.putExtra("senderId", senderId)
//            startActivity(intent)


        }

//        binding.getAccessableAccount.setOnClickListener{
//            Log.d("클릭","연결가능한 계좌 리스트를 확인합니다")
//
//            val intent=Intent(this, AccountListActivity::class.java)
//            startActivity(intent)
//
//            if(RemittanceRequestManager.linkedAccountId!=0L){
//                Log.d("연결계좌 선택","연결계좌 선택")
//
//            }
//
//
//        }
    }

    private fun getTransferInfo() {

        lifecycleScope.launch(Dispatchers.Main) {
            try {
                // Perform the asynchronous API call
                val response: Response<ApiRespond<TransferInfoResponse>> =
                    apiService.getTransferInfo(AccessTokenManager.getBearer())

                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    Log.d("통신 데이터", apiResponse.toString())

                    // Check the code and handle the response accordingly
                    when (apiResponse?.code) {
                        200 -> {
                            // Success, handle the transfer info
                            val transferInfo = apiResponse.data

                            // Use transferInfo to update UI or perform other actions
                            updateUI(transferInfo)
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

    private fun updateUI(transferInfo: TransferInfoResponse) {
        senderId = transferInfo.transferId
        Log.d("나ㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅣ", "sender Id 넣음 ${transferInfo.transferId}")
        Log.d("ffffㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅣ", "sender Id 결과 봐 ${senderId}")

        binding.petName.text = transferInfo.petInfo.petName
        binding.petBreed.text = transferInfo.petInfo.petBreed
        binding.petGender.text = transferInfo.petInfo.petGender
        binding.petNeutered.text = transferInfo.petInfo.petNeutered
        binding.petAge.text = transferInfo.petInfo.petAge
        binding.petBirth.text = transferInfo.petInfo.petBirth

        binding.name.text = transferInfo.newOwnerInfo.name
        binding.content.text = transferInfo.newOwnerInfo.content
        binding.date.text = transferInfo.newOwnerInfo.date

        RemittanceRequestManager.transferId=transferInfo.transferId

        Glide.with(this)
            .load(transferInfo.petInfo.petImage)
            .into(binding.petImage)

    }
}
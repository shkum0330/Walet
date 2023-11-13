package com.allforyou.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.allforyou.app.databinding.ActivityTransferIdentificationBinding
import com.allforyou.app.databinding.ActivityTransferPaymentBinding
import com.allforyou.app.databinding.ActivityTransferTargetBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TransferPaymentActivity : AppCompatActivity() {

    private lateinit var binding : ActivityTransferPaymentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transfer_payment)

        binding = ActivityTransferPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.identify.setOnClickListener {

            // API 요청 누구한테 보낼지
//            getRecipientInfo("000000000")
            val intent = Intent(this, TransferIdentificationActivity::class.java)
            startActivity(intent)
        }
    }
    fun getRecipientInfo(accountNumber : String){
        var retrofitAPI = RetrofitClient.getClient()
        retrofitAPI.getTransactionRecipientInfo(AccessTokenManager.getBearer(), accountNumber, 1).enqueue(object :
            Callback<RecipientInfoResponse> {
            override fun onResponse(call: Call<RecipientInfoResponse>, response: Response<RecipientInfoResponse>) {
                Log.d("my_tag","요청 사항 : "+AccessTokenManager.getBearer())
                if (response.isSuccessful) {
                    val recipient : RecipientInfoResponse.RecipientInfo = response.body()!!.data
                    Log.d("my_tag",recipient.toString())
                    if (recipient != null) {
                        Log.d("my_tag","예금주 로딩 성공")

                        RemittanceRequestManager.name = recipient.receiverName
                        RemittanceRequestManager.getInstance().receiverAccountId = recipient.accountId

                        val intent = Intent(this@TransferPaymentActivity, TransferIdentificationActivity::class.java)
                        startActivity(intent)
                    } else {
                        Log.d("my_tag","예금주 NULL 반화됨")
                        // Handle null response body
                    }
                } else {
                    Log.d("my_tag","예금주 로딩 실패")
                    // Handle unsuccessful response
                }
            }
            override fun onFailure(call: Call<RecipientInfoResponse>, t: Throwable) {
                Log.d("my_tag","거래 내역: 네트워크 오류")
            }
        })
    }
}
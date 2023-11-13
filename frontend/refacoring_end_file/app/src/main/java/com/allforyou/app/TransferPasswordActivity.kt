package com.allforyou.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.allforyou.app.databinding.ActivityTransferIdentificationBinding
import com.allforyou.app.databinding.ActivityTransferPasswordBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TransferPasswordActivity : AppCompatActivity() {

    private lateinit var binding : ActivityTransferPasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transfer_password)

        binding = ActivityTransferPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.finish.setOnClickListener {
            // 여기서 송금 시작
            remittance()
            val intent = Intent(this, TransferCompleteActivity::class.java)
            startActivity(intent)
        }
    }
    fun remittance(){
        RemittanceRequestManager.getInstance().password = "0000"

        var retrofitAPI = RetrofitClient.getClient()
        retrofitAPI.remittance(AccessTokenManager.getBearer(),RemittanceRequestManager.getInstance()).enqueue(object :
            Callback<Unit> {
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                Log.d("my_tag","요청 사항 : "+AccessTokenManager.getBearer())
                if (response.isSuccessful) {
                    Log.d("my_tag","송금 성공")
                    val intent = Intent(this@TransferPasswordActivity, TransferCompleteActivity::class.java)
                    startActivity(intent)
                } else {
                    Log.d("my_tag","송금 실패")
                    // Handle unsuccessful response
                }
            }
            override fun onFailure(call: Call<Unit>, t: Throwable) {
                Log.d("my_tag","거래 내역: 네트워크 오류")
            }
        })
    }
}
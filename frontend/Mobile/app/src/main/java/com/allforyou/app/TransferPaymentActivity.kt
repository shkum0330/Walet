package com.allforyou.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.allforyou.app.databinding.ActivityTransferIdentificationBinding
import com.allforyou.app.databinding.ActivityTransferPaymentBinding
import com.allforyou.app.databinding.ActivityTransferTargetBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TransferPaymentActivity : AppCompatActivity(),
    BottomSheetWithEasypayFragment.OnPriceUpdateListener {

    private lateinit var binding: ActivityTransferPaymentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transfer_payment)

        binding = ActivityTransferPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

//입력받은 계좌 번호 띄우기
        Log.d("넘겨받은 계좌번호", RemittanceRequestManager.accountNumber)
        binding.accountNumber.text = "농협 " + RemittanceRequestManager.accountNumber
        modalKeypadBottomSheet()


    }

    override fun finish() {

        // API 요청 누구한테 보낼지
        val price = binding.price.text.toString().toLong()
         Log.d("다음으로 넘어갑니다","예금주를 조회합니다")
        RemittanceRequestManager.getInstance().remittanceAmount=price
        getRecipientInfo(RemittanceRequestManager.accountNumber, price)


//        val intent = Intent(this, TransferIdentificationActivity::class.java)
//
//        startActivity(intent)
    }


    override fun num(inp: String) {
        Log.d("사용자 움직임", inp + "번호를 눌렀어요")
        var temp = binding.price.text.toString()

        if (temp.toLong() > 5000000) {
            Toast.makeText(this@TransferPaymentActivity, "500만원 이하만 이체 가능합니다", Toast.LENGTH_SHORT)
                .show()
            return
        }

        if (temp == "0") {
            if (inp != "0")
                temp = ""
            else
                return
        }

        temp += inp
        binding.price.text = temp
    }

    override fun easyPay(inp: Int) {
        var temp = binding.price.text.toString()
        var price = temp.toLong()
        if (price + inp > 5000000) {
            Toast.makeText(this@TransferPaymentActivity, "500만원 이하만 이체 가능합니다", Toast.LENGTH_SHORT)
                .show()
            return
        }
        price += inp
        binding.price.text = price.toString()

    }

    override fun del() {
        Log.d("사용자 움직임", "삭제버튼을 눌렀어요")
        var temp = binding.price.text.toString()

        if (temp == "0") return
        else if (temp.isNotEmpty() && temp.length == 1) binding.price.text = "0"
        else {
            temp = temp.substring(0, temp.length - 1)
            binding.price.text = temp

//            // 돈 입력한 대로 넣기
//            RemittanceRequestManager.getInstance().remittanceAmount = 10000
//


        }
    }

    private fun modalKeypadBottomSheet() {
        Log.d("OOOOOOOOOOOOOOOOOOOO", "modalKeypadBottomSheet함수를 호출했어요")
        val modal = BottomSheetWithEasypayFragment()
        modal.setOnPriceUpdateListener(this)  // listener 설정
        modal.show(supportFragmentManager, BottomSheetWithEasypayFragment.TAG)
    }


    fun getRecipientInfo(accountNumber: String, price: Long) {
        var retrofitAPI = RetrofitClient.getClient()
        retrofitAPI.getTransactionRecipientInfo(
            AccessTokenManager.getBearer(),
            accountNumber,
            price
        ).enqueue(object :
            Callback<RecipientInfoResponse> {
            override fun onResponse(
                call: Call<RecipientInfoResponse>,
                response: Response<RecipientInfoResponse>
            ) {
                Log.d("my_tal", "요청 사항 : " + AccessTokenManager.getBearer())
                if (response.isSuccessful) {
                    val recipient: RecipientInfoResponse.RecipientInfo = response.body()!!.data
                    Log.d("my_tab", recipient.toString())
                    if (recipient != null) {
                        Log.d("my_tag", "예금주 로딩 성공")

                         Log.d("예금주 정보", recipient.toString())
                        RemittanceRequestManager.name = recipient.receiverName
                        RemittanceRequestManager.getInstance().receiverAccountId =
                            recipient.accountId

                        val intent = Intent(
                            this@TransferPaymentActivity,
                            TransferIdentificationActivity::class.java
                        )
                        startActivity(intent)
                    } else {
                        Log.d("my_tag", "예금주 NULL 반화됨")
                        // Handle null response body
                    }
                } else {
                    Log.d("my_tag", "예금주 로딩 실패")
                    // 이전 화면으로 돌아감
                    // Handle unsuccessful response
                }
            }

            override fun onFailure(call: Call<RecipientInfoResponse>, t: Throwable) {
                Log.d("my_tag", "거래 내역: 네트워크 오류")
            }
        })
    }
}
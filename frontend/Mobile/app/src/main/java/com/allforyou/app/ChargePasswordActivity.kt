package com.allforyou.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.allforyou.app.databinding.ActivityChargePasswordBinding
import com.allforyou.app.databinding.ActivityChargePaymentBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChargePasswordActivity : AppCompatActivity(),
    BottomsheetOnlyNumberFragment.OnPriceUpdateListener {

    private lateinit var binding: ActivityChargePasswordBinding
    private var password: String = ""
    private var entereCnt: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChargePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        modalKeypadBottomSheet()
        binding.linkedAccountNumber.text="농협 "+RemittanceRequestManager.linkedAccountNumber

        binding.previous.setOnClickListener{
            Log.d("사용자 버튼 클릭", "이전버튼을 클릭했습니다")
            val intent=Intent(this, ChargePaymentActivity::class.java)
            startActivity(intent)
        }

    }

    fun doCharge() {
        var retrofitAPI = RetrofitClient.getClient()
        retrofitAPI.remittance(
            AccessTokenManager.getBearer(),
            RemittanceRequestManager.getInstance()
        ).enqueue(object :
            Callback<Unit> {
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                Log.d("my_tag", "요청 사항 : " + AccessTokenManager.getBearer())
                if (response.isSuccessful) {
                    Log.d("my_tag", "송금 성공")
                    val intent =
                        Intent(this@ChargePasswordActivity, ChargeCompleteActivity::class.java)
                    startActivity(intent)
                } else {
                    Log.d("my_tag", "송금 실패")

                    //일치하지 않으면 비밀번호 초기화
                    Toast.makeText(this@ChargePasswordActivity, "비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT)
                        .show()
                    binding.one.setBackgroundResource(R.drawable.box_password)
                    binding.two.setBackgroundResource(R.drawable.box_password)
                    binding.three.setBackgroundResource(R.drawable.box_password)
                    binding.four.setBackgroundResource(R.drawable.box_password)
                    entereCnt = 0

                    // Handle unsuccessful response
                }
            }

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                Log.d("my_tag", "거래 내역: 네트워크 오류")

                //일치하지 않으면 비밀번호 초기화
                Toast.makeText(this@ChargePasswordActivity, "네트워크 오류입니다..", Toast.LENGTH_SHORT)
                    .show()

            }
        })
    }

    override fun finish() {

        //지금까지 입력된 비밀번호 remmit에 저장
        RemittanceRequestManager.getInstance().password=password

        //비밀번호 확인
        Log.d("계좌비밀번호 확인", "일치하는지 확인해볼게요")


        //RemittanceRequestManager.getInstance().password
        doCharge()






    }


    override fun num(inp: String) {

        Log.d("사용자 움직임", inp + "번호를 눌렀어요")

        password += inp
        entereCnt++
        if (entereCnt == 1) {
            binding.one.setBackgroundResource(R.drawable.box_password_selected)
        } else if (entereCnt == 2) {
            binding.two.setBackgroundResource(R.drawable.box_password_selected)
        } else if (entereCnt == 3) {
            binding.three.setBackgroundResource(R.drawable.box_password_selected)
        } else {
            binding.four.setBackgroundResource(R.drawable.box_password_selected)

            //비밀번호 확인
            Log.d("계좌비밀번호 확인", "일치하는지 확인해볼게요")

            //remmit에 입력된 비밀번호 저장
            RemittanceRequestManager.getInstance().password = password

            //이체요청을 보내면서 비밀번호 확인
            doCharge()

        }


    }


    override fun del() {
        Log.d("사용자 움직임", "삭제버튼을 눌렀어요")


        if (entereCnt == 0) {
            Log.d("계좌비밀번호 확인", "지울게 더이상 없습니다")
            return
        }
        if (entereCnt == 4) {
            binding.four.setBackgroundResource(R.drawable.box_password)
            entereCnt--
        } else if (entereCnt == 3) {
            binding.three.setBackgroundResource(R.drawable.box_password)
            entereCnt--
        } else if (entereCnt == 2) {
            binding.two.setBackgroundResource(R.drawable.box_password)
            entereCnt--
        }
        if (entereCnt == 1) {
            binding.one.setBackgroundResource(R.drawable.box_password)
            entereCnt--
        }
    }

    private fun modalKeypadBottomSheet() {
        Log.d("OOOOOOOOOOOOOOOOOOOO", "modalKeypadBottomSheet함수를 호출했어요")
        val modal = BottomsheetOnlyNumberFragment()
        modal.setOnPriceUpdateListener(this)  // listener 설정
        modal.show(supportFragmentManager, BottomsheetOnlyNumberFragment.TAG)
    }
}
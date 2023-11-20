package com.allforyou.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.allforyou.app.databinding.ActivityChargePaymentBinding
import com.allforyou.app.databinding.ActivityTransactionReceiptBinding

class ChargePaymentActivity : AppCompatActivity(),
    BottomSheetWithEasypayFragment.OnPriceUpdateListener{

    private lateinit var binding: ActivityChargePaymentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChargePaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        모계좌 계좌번호 띄우기
        binding.accountNumber.text="농협 "+RemittanceRequestManager.linkedAccountNumber

        modalKeypadBottomSheet()

        binding.previous.setOnClickListener{
            Log.d("사용자 버튼 클릭", "이전버튼을 클릭했습니다")
            val intent=Intent(this, ChargePaymentActivity::class.java)
            startActivity(intent)
        }
    }

    override fun finish() {

        //입력된 금액 Remmit에 전달
        val price = binding.price.text.toString().toLong()
        RemittanceRequestManager.getInstance().remittanceAmount=price

        //비밀번호 확인페이지로 이동
        val intent = Intent(this, ChargePasswordActivity::class.java)

        startActivity(intent)
    }


    override fun num(inp: String) {
        Log.d("사용자 움직임", inp + "번호를 눌렀어요")
        var temp = binding.price.text.toString()

        if (temp.toLong() > 5000000) {
            Toast.makeText(this@ChargePaymentActivity, "500만원 이하만 이체 가능합니다", Toast.LENGTH_SHORT)
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
            Toast.makeText(this@ChargePaymentActivity, "500만원 이하만 이체 가능합니다", Toast.LENGTH_SHORT)
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
        }
    }

    private fun modalKeypadBottomSheet() {
        Log.d("OOOOOOOOOOOOOOOOOOOO", "modalKeypadBottomSheet함수를 호출했어요")
        val modal = BottomSheetWithEasypayFragment()
        modal.setOnPriceUpdateListener(this)  // listener 설정
        modal.show(supportFragmentManager, BottomSheetWithEasypayFragment.TAG)
    }

}
package com.allforyou.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.allforyou.app.databinding.ActivityPayPaymentBinding

class PayPaymentActivity : AppCompatActivity(), BottomSheetWithEasypayFragment.OnPriceUpdateListener {
    private lateinit var binding : ActivityPayPaymentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPayPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        modalKeypadBottomSheet()

    }

    override fun finish() {
        val intent = Intent(this, PayMethodActivity::class.java)
        startActivity(intent)
    }


    override fun num(inp: String) {
        Log.d("사용자 움직임",inp+"번호를 눌렀어요")
        var temp = binding.price.text.toString()

        if(temp=="0"){
            if(inp!="0")
                temp=""
            else
                return
        }

        temp += inp
        binding.price.text = temp
    }

    override fun del() {
        Log.d("사용자 움직임","삭제버튼을 눌렀어요")
        var temp = binding.price.text.toString()

        if(temp=="0") return
        else if(temp.isNotEmpty() && temp.length==1) binding.price.text="0"
        else  {
            temp = temp.substring(0, temp.length - 1)
            binding.price.text = temp
        }
    }

    private fun modalKeypadBottomSheet(){
        Log.d("OOOOOOOOOOOOOOOOOOOO","modalKeypadBottomSheet함수를 호출했어요")
        val modal = BottomSheetWithEasypayFragment()
        modal.setOnPriceUpdateListener(this)  // listener 설정
        modal.show(supportFragmentManager, BottomSheetWithEasypayFragment.TAG)
    }


}
package com.allforyou.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.allforyou.app.databinding.ActivityChargeCompleteBinding
import com.allforyou.app.databinding.ActivityChargePasswordBinding

class ChargeCompleteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChargeCompleteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChargeCompleteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.goHome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        //충전금액과 연결된 계좌의 계좌번호를 UI에 나타냄
        binding.chargeAmout.text=RemittanceRequestManager.getInstance().remittanceAmount.toString()+"원"
        val temp=RemittanceRequestManager.linkedAccountNumber
        binding.chargeAccountNumber.text="농협 "+temp.substring(0,3)+"-"+temp.substring(3,7)+"-"+temp.substring(7,11)+"-"+temp.substring(11,13)
    }
}
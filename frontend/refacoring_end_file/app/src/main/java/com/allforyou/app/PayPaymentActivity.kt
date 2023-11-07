package com.allforyou.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.allforyou.app.databinding.ActivityPayPaymentBinding

class PayPaymentActivity : AppCompatActivity() {
    private lateinit var binding : ActivityPayPaymentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPayPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.finish.setOnClickListener({
            val intent = Intent(this, PayMethodActivity::class.java)
            startActivity(intent)
        })


        binding.paymentDisabled.setOnClickListener({
            val intent = Intent(this, PaymentCompleteNegativeActivity::class.java)
            startActivity(intent)
        })
    }
}
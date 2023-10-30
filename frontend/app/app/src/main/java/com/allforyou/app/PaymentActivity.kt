package com.allforyou.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.allforyou.app.databinding.ActivityPaymentBinding

class PaymentActivity : AppCompatActivity() {

    private lateinit var binding : ActivityPaymentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.finish.setOnClickListener({
            val intent = Intent(this, SelectMethodActivity::class.java)
            startActivity(intent)
        })


        binding.paymentDisabled.setOnClickListener({
            val intent = Intent(this, PaymentDisabledActivity::class.java)
            startActivity(intent)
        })
    }
}
package com.allforyou.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.allforyou.app.databinding.ActivityChargePaymentBinding
import com.allforyou.app.databinding.ActivityTransactionReceiptBinding

class ChargePaymentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChargePaymentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChargePaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.identify.setOnClickListener {
            val intent = Intent(this, ChargePasswordActivity::class.java)
            startActivity(intent)
        }
    }
}
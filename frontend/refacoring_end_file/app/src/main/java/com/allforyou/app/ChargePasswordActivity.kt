package com.allforyou.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.allforyou.app.databinding.ActivityChargePasswordBinding
import com.allforyou.app.databinding.ActivityChargePaymentBinding

class ChargePasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChargePasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChargePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.finish.setOnClickListener {
            val intent = Intent(this, ChargeCompleteActivity::class.java)
            startActivity(intent)
        }
    }
}
package com.allforyou.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.allforyou.app.databinding.ActivityPaymentCompletePositiveBinding
import com.allforyou.app.databinding.ActivityPaymentCompleteNegativeBinding

class PaymentCompleteNegativeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPaymentCompleteNegativeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentCompleteNegativeBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.goHome.setOnClickListener({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        })
    }
}
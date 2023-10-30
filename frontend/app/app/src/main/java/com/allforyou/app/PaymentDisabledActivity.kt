package com.allforyou.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.allforyou.app.databinding.ActivityPaymentAbledBinding
import com.allforyou.app.databinding.ActivityPaymentDisabledBinding

class PaymentDisabledActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPaymentDisabledBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentDisabledBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.goHome.setOnClickListener({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        })
    }
}
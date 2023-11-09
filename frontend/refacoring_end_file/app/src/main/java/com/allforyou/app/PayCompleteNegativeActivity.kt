package com.allforyou.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.allforyou.app.databinding.ActivityPayCompleteNegativeBinding
import com.allforyou.app.databinding.ActivityPaymentCompleteNegativeBinding

class PayCompleteNegativeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPayCompleteNegativeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPayCompleteNegativeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.goHome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
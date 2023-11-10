package com.allforyou.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.allforyou.app.databinding.ActivityPayCompleteNegativeBinding
import com.allforyou.app.databinding.ActivityPayCompletePositiveBinding

class PayCompletePositiveActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPayCompletePositiveBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPayCompletePositiveBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.goHome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.goRecipt.setOnClickListener {
            val intent = Intent(this, TransactionReceiptActivity::class.java)
            startActivity(intent)
        }

    }
}
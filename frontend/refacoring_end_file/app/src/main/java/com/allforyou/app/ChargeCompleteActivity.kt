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
    }
}
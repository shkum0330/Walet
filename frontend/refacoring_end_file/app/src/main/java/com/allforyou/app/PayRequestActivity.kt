package com.allforyou.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.allforyou.app.databinding.ActivityPayIdentificationBinding
import com.allforyou.app.databinding.ActivityPayRequestBinding

class PayRequestActivity : AppCompatActivity() {
    private lateinit var binding : ActivityPayRequestBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityPayRequestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.writeChipId.setOnClickListener {
            val intent = Intent(this, PayCompletePositiveActivity::class.java)
            startActivity(intent)
        }
        // 실패시 PayCompleteNegativeActivity로 이동
    }
}
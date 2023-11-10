package com.allforyou.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.allforyou.app.databinding.ActivityPayIdentificationBinding
import com.allforyou.app.databinding.ActivityPayRecognitionChipBinding

class PayIdentificationActivity : AppCompatActivity() {
    private lateinit var binding : ActivityPayIdentificationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityPayIdentificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.confirmIdentification.setOnClickListener {
            // 사업장 쪽은 원래 MainActivity로 가야함
            val intent = Intent(this, PayRequestActivity::class.java)
            startActivity(intent)
        }
    }
}
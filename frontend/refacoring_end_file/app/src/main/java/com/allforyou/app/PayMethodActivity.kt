package com.allforyou.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.allforyou.app.databinding.ActivityPayMethodBinding

class PayMethodActivity : AppCompatActivity() {
    private lateinit var binding : ActivityPayMethodBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityPayMethodBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.nosePrint.setOnClickListener {
            val intent = Intent(this, PayRecognitionChipActivity::class.java)
            startActivity(intent)
        }

        binding.chip.setOnClickListener {
            val intent = Intent(this, PayRecognitionChipActivity::class.java)
            startActivity(intent)
        }

        binding.close.setOnClickListener {
            val intent = Intent(this, PayPaymentActivity::class.java)
            startActivity(intent)
        }
    }
}
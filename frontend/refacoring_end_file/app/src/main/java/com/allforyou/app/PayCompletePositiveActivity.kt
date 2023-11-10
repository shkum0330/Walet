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
<<<<<<< HEAD
        setContentView(R.layout.activity_pay_complete_positive)
=======
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
>>>>>>> f34acb82f11168c25395974323bf5272ec0cbc74
    }
}
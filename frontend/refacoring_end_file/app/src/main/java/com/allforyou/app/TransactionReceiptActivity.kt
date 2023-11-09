package com.allforyou.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.allforyou.app.databinding.ActivityPayCompletePositiveBinding
import com.allforyou.app.databinding.ActivityTransactionReceiptBinding

class TransactionReceiptActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTransactionReceiptBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransactionReceiptBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.goHome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        //go_home
    }
}
package com.allforyou.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.allforyou.app.databinding.ActivityTransferIdentificationBinding
import com.allforyou.app.databinding.ActivityTransferPaymentBinding
import com.allforyou.app.databinding.ActivityTransferTargetBinding

class TransferIdentificationActivity : AppCompatActivity() {
    private lateinit var binding : ActivityTransferIdentificationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transfer_identification)

        binding = ActivityTransferIdentificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.send.setOnClickListener {
            val intent = Intent(this, TransferPasswordActivity::class.java)
            startActivity(intent)
        }
    }
}
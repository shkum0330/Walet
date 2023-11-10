package com.allforyou.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.allforyou.app.databinding.ActivityTransferIdentificationBinding
import com.allforyou.app.databinding.ActivityTransferPaymentBinding
import com.allforyou.app.databinding.ActivityTransferTargetBinding

class TransferPaymentActivity : AppCompatActivity() {

    private lateinit var binding : ActivityTransferPaymentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transfer_payment)

        binding = ActivityTransferPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.identify.setOnClickListener {
            val intent = Intent(this, TransferIdentificationActivity::class.java)
            startActivity(intent)
        }
    }
}
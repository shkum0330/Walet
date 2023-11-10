package com.allforyou.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.allforyou.app.databinding.ActivityTransferIdentificationBinding
import com.allforyou.app.databinding.ActivityTransferPasswordBinding

class TransferPasswordActivity : AppCompatActivity() {

    private lateinit var binding : ActivityTransferPasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transfer_password)

        binding = ActivityTransferPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.finish.setOnClickListener {
            val intent = Intent(this, TransferCompleteActivity::class.java)
            startActivity(intent)
        }
    }
}
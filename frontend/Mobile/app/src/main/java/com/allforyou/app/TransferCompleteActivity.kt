package com.allforyou.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.allforyou.app.databinding.ActivityTransferCompleteBinding
import com.allforyou.app.databinding.ActivityTransferPasswordBinding

class TransferCompleteActivity : AppCompatActivity() {

    private lateinit var binding : ActivityTransferCompleteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTransferCompleteBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.receiverName.text=RemittanceRequestManager.name+"님께"
        binding.receiverAmount.text=RemittanceRequestManager.getInstance().remittanceAmount.toString()+"원"
        binding.receiverAccountNumber.text="농협 "+RemittanceRequestManager.accountNumber

//        setContentView(R.layout.activity_transfer_complete)
        binding.goHome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
package com.allforyou.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewbinding.ViewBinding
import com.allforyou.app.databinding.ActivityPaymentAbledBinding
import com.allforyou.app.databinding.ActivityPaymentBinding
import com.allforyou.app.databinding.ActivityReceiptBinding

class PaymentAbledActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPaymentAbledBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentAbledBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.goRecipt.setOnClickListener({
            val intent=Intent(this, ReceiptActivity::class.java)
            startActivity(intent)
    })



        binding.goHome.setOnClickListener({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        })
    }


}
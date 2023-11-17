package com.allforyou.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.allforyou.app.databinding.ActivitySendCheckWhoBinding

class SendCheckWhoActivity : AppCompatActivity() {

    private lateinit var binding:ActivitySendCheckWhoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySendCheckWhoBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.send.setOnClickListener({
            val intent= Intent(this,SendPasswordActivity::class.java)
            startActivity(intent)
        })

        binding.sendNo.setOnClickListener({
            val intent= Intent(this,SendMoneyActivity::class.java)
            startActivity(intent)
        })


    }
}
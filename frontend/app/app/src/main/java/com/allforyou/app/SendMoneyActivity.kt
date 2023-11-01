package com.allforyou.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.allforyou.app.databinding.ActivitySendCheckWhoBinding
import com.allforyou.app.databinding.ActivitySendMoneyBinding

class SendMoneyActivity : AppCompatActivity() {

    private lateinit var binding:ActivitySendMoneyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySendMoneyBinding.inflate(layoutInflater)
        setContentView(binding.root)




        binding.identify.setOnClickListener({
            val intent= Intent(this, SendCheckWhoActivity::class.java)
            startActivity(intent)
        })
    }
}
package com.allforyou.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.allforyou.app.databinding.ActivitySendBinding

class SendActivity : AppCompatActivity() {

    private lateinit var binding:ActivitySendBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySendBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.finish.setOnClickListener({
            var intent= Intent(this,SendMoneyActivity::class.java )
            startActivity(intent)
        })
    }
}
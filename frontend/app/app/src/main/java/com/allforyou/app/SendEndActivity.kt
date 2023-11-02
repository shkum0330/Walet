package com.allforyou.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.allforyou.app.databinding.ActivityPaymentBinding
import com.allforyou.app.databinding.ActivitySendEndBinding

class SendEndActivity : AppCompatActivity() {

    private lateinit var binding:ActivitySendEndBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySendEndBinding.inflate(layoutInflater)
        setContentView(binding.root)




        binding.goHome.setOnClickListener({
            val intent= Intent(this, MainActivity::class.java)
            startActivity(intent)
        })

    }
}
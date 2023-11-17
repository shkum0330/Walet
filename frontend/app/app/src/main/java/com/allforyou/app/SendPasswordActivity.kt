package com.allforyou.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.allforyou.app.databinding.ActivitySendPasswordBinding

class SendPasswordActivity : AppCompatActivity() {
    private lateinit var binding:ActivitySendPasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding=ActivitySendPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.finish.setOnClickListener({
            val intent= Intent(this, SendEndActivity::class.java)
            startActivity(intent)
        })


    }
}
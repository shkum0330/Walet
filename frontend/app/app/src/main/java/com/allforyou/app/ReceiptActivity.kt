package com.allforyou.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.allforyou.app.databinding.ActivityMainBinding
import com.allforyou.app.databinding.ActivityReceiptBinding

class ReceiptActivity : AppCompatActivity() {

    private lateinit var binding:ActivityReceiptBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityReceiptBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.goHome.setOnClickListener({
            val intent=Intent(this, MainActivity::class.java)
            startActivity(intent)
        })

    }
}
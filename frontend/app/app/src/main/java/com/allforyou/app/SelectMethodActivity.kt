package com.allforyou.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.allforyou.app.databinding.ActivityMainBinding
import com.allforyou.app.databinding.ActivitySelectMethodBinding

class SelectMethodActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySelectMethodBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySelectMethodBinding.inflate(layoutInflater)
        setContentView(binding.root)

       binding.nosePrint.setOnClickListener({
           val intent= Intent(this, ChipAlertActivity::class.java)
           startActivity(intent)
       })

        binding.chip.setOnClickListener({
            val intent= Intent(this, ChipAlertActivity::class.java)
            startActivity(intent)
        })

        binding.close.setOnClickListener({
            val intent=Intent(this, PaymentActivity::class.java)
            startActivity(intent)
        })
    }
}
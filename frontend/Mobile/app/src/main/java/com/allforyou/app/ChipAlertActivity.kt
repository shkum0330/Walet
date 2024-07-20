package com.allforyou.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.allforyou.app.databinding.ActivityChipAlertBinding
import com.allforyou.app.databinding.ActivityMainBinding

class ChipAlertActivity : AppCompatActivity() {
    private lateinit var binding : ActivityChipAlertBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChipAlertBinding.inflate(layoutInflater)
        setContentView(binding.root)




        binding.writeChipId.setOnClickListener({
            val intent = Intent(this, PayCompletePositiveActivity::class.java)
            startActivity(intent)
        })



    }
}
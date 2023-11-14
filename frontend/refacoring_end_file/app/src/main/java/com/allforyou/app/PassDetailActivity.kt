package com.allforyou.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.allforyou.app.databinding.ActivityPassDetailBinding

class PassDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPassDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityPassDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.



    }
}
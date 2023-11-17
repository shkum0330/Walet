package com.allforyou.app

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.allforyou.app.databinding.ActivityPassCompleteBinding


class PassCompleteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPassCompleteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityPassCompleteBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.goHome.setOnClickListener({
            val intent= Intent(this, MainActivity::class.java)
            startActivity(intent)
        })
    }
}
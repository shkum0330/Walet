package com.allforyou.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.allforyou.app.databinding.ActivityPassAcceptCompleteBinding
import com.allforyou.app.databinding.ActivityPassCompleteBinding

class PassAcceptCompleteActivity : AppCompatActivity() {
    private lateinit var binding:ActivityPassAcceptCompleteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityPassAcceptCompleteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.goHome.setOnClickListener({
            val intent= Intent(this, MainActivity::class.java)
            startActivity(intent)
        })



    }
}
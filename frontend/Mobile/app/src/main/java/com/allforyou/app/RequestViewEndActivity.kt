package com.allforyou.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.allforyou.app.databinding.ActivityRequestViewEndBinding

class RequestViewEndActivity : AppCompatActivity() {
    private lateinit var binding:ActivityRequestViewEndBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityRequestViewEndBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.goHome.setOnClickListener({
            val intent= Intent(this, MainActivity::class.java)
            Log.d("사용자 클릭", "홈으로 이동")
            startActivity(intent)
        })
    }
}
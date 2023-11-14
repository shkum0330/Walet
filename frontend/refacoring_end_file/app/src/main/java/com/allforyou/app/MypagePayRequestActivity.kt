package com.allforyou.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.allforyou.app.databinding.ActivityMypagePayRequestBinding

class MypagePayRequestActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMypagePayRequestBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMypagePayRequestBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}
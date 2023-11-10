package com.allforyou.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.allforyou.app.databinding.ActivityRequestViewBinding

class RequestViewActivity : AppCompatActivity() {

    private lateinit var binding:ActivityRequestViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityRequestViewBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.doApply.setOnClickListener({
            val intent= Intent(this, RequestViewEndActivity::class.java)
            startActivity(intent)
        })
    }
}
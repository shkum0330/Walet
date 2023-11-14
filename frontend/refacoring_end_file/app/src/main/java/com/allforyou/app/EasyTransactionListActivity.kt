package com.allforyou.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.allforyou.app.databinding.ActivityEasyTransactionListBinding
import com.allforyou.app.databinding.FragmentPrintBinding

class EasyTransactionListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEasyTransactionListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityEasyTransactionListBinding.inflate(layoutInflater)

        setContentView(binding.root)


        binding.btnClose.setOnClickListener({
            val intent= Intent(this, MainActivity::class.java)
            startActivity(intent)
        })
    }
}
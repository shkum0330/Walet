package com.allforyou.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.allforyou.app.databinding.ActivityPassAccessAccountListBinding

class PassAccessAccountListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPassAccessAccountListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityPassAccessAccountListBinding.inflate(layoutInflater)
        setContentView(binding.root)



//        binding.listView
    }
}
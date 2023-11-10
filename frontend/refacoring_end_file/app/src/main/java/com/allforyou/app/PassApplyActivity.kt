package com.allforyou.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.allforyou.app.databinding.ActivityPassApplyBinding

class PassApplyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPassApplyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pass_apply)
    }
}
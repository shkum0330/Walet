package com.allforyou.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.allforyou.app.databinding.ActivityChipAlertBinding
import com.allforyou.app.databinding.ActivityPayMethodBinding
import com.allforyou.app.databinding.ActivityPayRecognitionChipBinding

class PayRecognitionChipActivity : AppCompatActivity() {

    private lateinit var binding : ActivityPayRecognitionChipBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityPayRecognitionChipBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.writeChipId.setOnClickListener {
            
            // 정보 가져오기
            val intent = Intent(this, PayIdentificationActivity::class.java)
            startActivity(intent)
        }
    }
    fun rfidGetPetInfo(){
        
    }

}
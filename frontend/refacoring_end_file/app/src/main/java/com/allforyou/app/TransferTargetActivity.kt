package com.allforyou.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.allforyou.app.databinding.ActivityMainBinding
import com.allforyou.app.databinding.ActivityPayRecognitionChipBinding
import com.allforyou.app.databinding.ActivityTransferCompleteBinding
import com.allforyou.app.databinding.ActivityTransferTargetBinding
import com.allforyou.app.databinding.FragmentHomeBinding

class TransferTargetActivity : AppCompatActivity() {

    private lateinit var binding : ActivityTransferTargetBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransferTargetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d("my_tag",RemittanceRequestManager.getInstance().myAccountId.toString())

        binding.finish.setOnClickListener {
            // 키패드에 입력한 내용 연결
            RemittanceRequestManager.receiverAccountNumber = "301-7890-1357-81"
            val intent = Intent(this, TransferPaymentActivity::class.java)
            startActivity(intent)
        }
    }


}
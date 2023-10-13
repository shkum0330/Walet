package com.example.testapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.testapp.databinding.ActivityMainBinding
import com.example.testapp.databinding.ActivitySendMoneyBinding

class SendMoneyActivity : AppCompatActivity() {

    private lateinit var  binding: ActivitySendMoneyBinding
    private lateinit var accountNum:String
    private lateinit var bank:String



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_send_money)
        binding=ActivitySendMoneyBinding.inflate(layoutInflater)
        setContentView(binding.root)

//22개 은행
        val img=arrayOf(
            R.drawable.bank_shinhan,
            R.drawable.bank_jeju,
            R.drawable.bank_kb,
            R.drawable.bank_ibk,
            R.drawable.bank_nh,
            R.drawable.bank_kdb,
            R.drawable.bank_shinhyup,
            R.drawable.bank_woori,
            R.drawable.bank_hana,
            R.drawable.bank_citi,
            R.drawable.bank_kakao_bank,
            R.drawable.bank_k_bank,
            R.drawable.bank_toss,
            R.drawable.bank_gyung_nam,
            R.drawable.bank_gwangju,
            R.drawable.bank_dgb,
            R.drawable.bank_busan,
            R.drawable.bank_jun_buk,
            R.drawable.bank_new_town,
            R.drawable.bank_post,
            R.drawable.bank_ju_chuk,
            R.drawable.bank_sc,
            R.drawable.bank_sanlim,
        )
        val text= arrayOf(
            "신한",
            "제주",
            "국민",
            "기업",
            "농협",
            "산업",
            "수협",
            "신협",
            "우리",
            "하나",
            "한국씨티",
            "카카오뱅크",
            "케이뱅크",
            "토스뱅크",
            "경남",
            "광주",
            "대구",
            "부산",
            "전북",
            "새마을",
            "우체국",
            "저축은행",
            "SC"
        )

        val bankViewAdapter=BankViewAdapter(this, img, text)
        binding.bankGridview.adapter=bankViewAdapter



        binding.showBanks.setOnClickListener {
            var status=binding.bankGridview.visibility
            if(status==View.GONE)
                binding.bankGridview.visibility=View.VISIBLE
            else
                binding.bankGridview.visibility=View.GONE
        }


    }
}
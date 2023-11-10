package com.allforyou.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import com.allforyou.app.databinding.ActivityChargeCompleteBinding
import com.allforyou.app.databinding.ActivityNoticeBinding
import com.squareup.picasso.Picasso

class NoticeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNoticeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoticeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val notice : NoticeResponse.Notice = NoticeManager.getInstance()

        binding.title.text = notice.title
        binding.subtitle.text = notice.subTitle
        binding.content.text = notice.content
        val imageView : ImageView = binding.noticeImg
        val imageUrl = notice.bannerImg;

        Picasso.get().load(imageUrl).into(imageView)

        binding.btnClose.setOnClickListener{
            finish()
//            val intent = Intent(requireActivity(), PayPaymentActivity::class.java)
//            startActivity(intent)
        }
    }
}
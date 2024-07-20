package com.allforyou.app

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.widget.Toast

import com.allforyou.app.databinding.ActivityTransferTargetBinding
import com.google.android.material.snackbar.Snackbar

class TransferTargetActivity : AppCompatActivity(),
    BottomsheetOnlyNumberFragment.OnPriceUpdateListener {

    private lateinit var binding: ActivityTransferTargetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransferTargetBinding.inflate(layoutInflater)
        setContentView(binding.root)


        modalKeypadBottomSheet()

        binding.previous.setOnClickListener {
            vibrateSingle()
            Snackbar.make(this, binding.root, "완료 메시지", Snackbar.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)

            Log.d("사용자 버튼 클릭", "뒤로가기 버튼을 눌렀어요")
            startActivity(intent)


        }
    }

    override fun finish() {
        val intent = Intent(this, TransferPaymentActivity::class.java)
        RemittanceRequestManager.accountNumber = binding.accountNumber.text.toString()
        Log.d(
            "!!!!!!!!!!!!!!!!!!!!!!!!!!",
            "Remittance에 저장한 accountNumber : ${RemittanceRequestManager.accountNumber}"
        )

        vibrateSingle()
        Snackbar.make(this, binding.root, "완료 메시지", Snackbar.LENGTH_SHORT).show()
        startActivity(intent)
    }


    override fun num(inp: String) {
        vibrateSingle()
        Snackbar.make(this, binding.root, "완료 메시지", Snackbar.LENGTH_SHORT).show()

        Log.d("사용자 움직임", inp + "번호를 눌렀어요")
        var temp = binding.accountNumber.text.toString()

        if(temp.length>13)
        {
            Toast.makeText(this, "계좌번호는 13자리까지만 입력이 가능해요", Toast.LENGTH_SHORT).show()
            return
        }

        if (temp.equals("계좌번호를 입력하세요")) {
            temp = ""
        }

        temp += inp
        binding.accountNumber.text = temp

    }


    override fun del() {
        vibrateSingle()
        Snackbar.make(this, binding.root, "완료 메시지", Snackbar.LENGTH_SHORT).show()


        Log.d("사용자 움직임", "삭제버튼을 눌렀어요")
        var temp = binding.accountNumber.text.toString()

        if (temp.length == 0) {
            Toast.makeText(this@TransferTargetActivity, "최대 20자리까지 입력가능합니다", Toast.LENGTH_SHORT)
                .show()
            return
        } else {
            temp = temp.substring(0, temp.length - 1)
            binding.accountNumber.text = temp
        }
    }
    private fun vibrateSingle() {
        MakeVibrator().run {
            init(this@TransferTargetActivity)
            make(70)
        }
    }

    private fun modalKeypadBottomSheet() {
        Log.d("OOOOOOOOOOOOOOOOOOOO", "modalKeypadBottomSheet함수를 호출했어요")
        val modal = BottomsheetOnlyNumberFragment()
        modal.setOnPriceUpdateListener(this)  // listener 설정
        modal.show(supportFragmentManager, BottomsheetOnlyNumberFragment.TAG)
    }


}
package com.allforyou.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.allforyou.app.databinding.ActivityTransferIdentificationBinding
import com.allforyou.app.databinding.ActivityTransferPasswordBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TransferPasswordActivity : AppCompatActivity(), BottomsheetOnlyNumberFragment.OnPriceUpdateListener {

    private lateinit var binding : ActivityTransferPasswordBinding
    private var password:String=""
    private var entereCnt:Int=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transfer_password)

        modalKeypadBottomSheet()

        binding = ActivityTransferPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }


//    override fun finish() {
//
//        //비밀번호 확인
//        Log.d("계좌비밀번호 확인","일치하는지 확인해볼게요")
//        if(RemittanceRequestManager.getInstance().password.equals(password)){
//
//            //계좌번호 일치 하므로 -> 이체 api연결
//
//            var retrofitAPI = RetrofitClient.getClient()
//            retrofitAPI.remittance(AccessTokenManager.getBearer(),RemittanceRequestManager.getInstance()).enqueue(object :
//                Callback<Unit> {
//                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
//                    Log.d("my_tag","요청 사항 : "+AccessTokenManager.getBearer())
//                    if (response.isSuccessful) {
//                        Log.d("my_tag","송금 성공")
//                        //계좌 이체 완료 페이지로 이동
//                        val intent = Intent(this@TransferPasswordActivity, TransferCompleteActivity::class.java)
//                        startActivity(intent)
//                    } else {
//                        Log.d("my_tag","송금 실패")
//                        // Handle unsuccessful response
//                    }
//                }
//                override fun onFailure(call: Call<Unit>, t: Throwable) {
//                    Log.d("my_tag","거래 내역: 네트워크 오류")
//                }
//            })
//
//
//        }
//        else{
//            //일치하지 않으면 비밀번호 초기화
//            Toast.makeText(this@TransferPasswordActivity, "비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show()
//            binding.one.setBackgroundResource(R.drawable.box_password)
//            binding.two.setBackgroundResource(R.drawable.box_password)
//            binding.three.setBackgroundResource(R.drawable.box_password)
//            binding.four.setBackgroundResource(R.drawable.box_password)
//            entereCnt=0
//
//        }
//
//
//    }


    override fun num(inp: String) {

        Log.d("사용자 움직임",inp+"번호를 눌렀어요")

       password+=inp
        entereCnt++
        if(entereCnt==1){
            binding.one.setBackgroundResource(R.drawable.box_password_selected)
        }
        else if(entereCnt==2){
            binding.two.setBackgroundResource(R.drawable.box_password_selected)
        }
        else if(entereCnt==3){
            binding.three.setBackgroundResource(R.drawable.box_password_selected)
        }
        else {
            binding.four.setBackgroundResource(R.drawable.box_password_selected)

            //비밀번호 확인
            Log.d("계좌비밀번호 확인","일치하는지 확인해볼게요")

            remittance(password)

        }


    }



    override fun del() {
        Log.d("사용자 움직임","삭제버튼을 눌렀어요")


        if(entereCnt==0) {
            Log.d("계좌비밀번호 확인","지울게 더이상 없습니다")
            return
        }
        if(entereCnt==4){
            binding.four.setBackgroundResource(R.drawable.box_password)
            entereCnt--
        }
        else  if(entereCnt==3){
            binding.three.setBackgroundResource(R.drawable.box_password)
            entereCnt--
        }
        else if(entereCnt==2){
            binding.two.setBackgroundResource(R.drawable.box_password)
            entereCnt--
        }
        if(entereCnt==1){
            binding.one.setBackgroundResource(R.drawable.box_password)
            entereCnt--
        }
    }

    private fun modalKeypadBottomSheet(){
        Log.d("OOOOOOOOOOOOOOOOOOOO","modalKeypadBottomSheet함수를 호출했어요")
        val modal = BottomsheetOnlyNumberFragment()
        modal.setOnPriceUpdateListener(this)  // listener 설정
        modal.show(supportFragmentManager, BottomsheetOnlyNumberFragment.TAG)
    }


    fun remittance(password:String){
        // 키패드로 입력받은 것으로 만들기
        RemittanceRequestManager.getInstance().password = password

        var retrofitAPI = RetrofitClient.getClient()
        retrofitAPI.remittance(AccessTokenManager.getBearer(),RemittanceRequestManager.getInstance()).enqueue(object :
            Callback<Unit> {
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                Log.d("my_tag","요청 사항 : "+AccessTokenManager.getBearer())
                if (response.isSuccessful) {
                    Log.d("my_tag","송금 성공")
                    val intent = Intent(this@TransferPasswordActivity, TransferCompleteActivity::class.java)
                    startActivity(intent)
                } else {
                    Log.d("my_tag","송금 실패")
                    //일치하지 않으면 비밀번호 초기화
                    Toast.makeText(this@TransferPasswordActivity, "비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show()
                    binding.one.setBackgroundResource(R.drawable.box_password)
                    binding.two.setBackgroundResource(R.drawable.box_password)
                    binding.three.setBackgroundResource(R.drawable.box_password)
                    binding.four.setBackgroundResource(R.drawable.box_password)
                    entereCnt=0
                    // Handle unsuccessful response
                }
            }
            override fun onFailure(call: Call<Unit>, t: Throwable) {
                Log.d("my_tag","거래 내역: 네트워크 오류")
                //일치하지 않으면 비밀번호 초기화
                Toast.makeText(this@TransferPasswordActivity, "비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show()
                binding.one.setBackgroundResource(R.drawable.box_password)
                binding.two.setBackgroundResource(R.drawable.box_password)
                binding.three.setBackgroundResource(R.drawable.box_password)
                binding.four.setBackgroundResource(R.drawable.box_password)
                entereCnt=0
            }
        })
    }
}
package com.example.testapp
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.testapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var bankAccountAdapter: BankAccountAdapter
    private var bankList = mutableListOf<BankAccount>()
    private val binding: ActivityMainBinding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("onCreate","onCreate함수 실행!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
        setContentView(binding.root)
        Log.d("onCreate","onCreate함수 실행!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
        // 더미 데이터 생성 및 추가
        val user= Member(1, "유지나", "wlskb@naver.com")
        val dummyAccounts = listOf(
            BankAccount( "농협 건강생명 통장", "111-111-111111","511 500", user),
            BankAccount( "농협 봄사랑저축은행 통장", "222-222-222222","111 378 520", user),
            BankAccount( "농협 음메소오돼지 통장", "333-333-333333","1 230", user)
        )
        bankList.addAll(dummyAccounts) // 더미 데이터를 bankList에 추가


        bankAccountAdapter= BankAccountAdapter()
        bankAccountAdapter.setList(bankList) // 어댑터에 데이터 설정
        Log.d("onCreate", "뷰 바인딩, Adapter생성 완료")


        //계좌 정보 적용
        Log.d("계좌 화면에 나타내기", "recycleView 적용 시작")
//        binding.recycleViewAccount.apply {
//            adapter=bankAccountAdapter // RecyclerView에 어댑터 설정
//            layoutManager=LinearLayoutManager(context)
//            setHasFixedSize(true)
//        }
        Log.d("계좌 화면에 나타내기", "recycleView 적용 완료")

        Log.d("onCreate", "onCreate함수 실행 완료")


        binding.goSend.setOnClickListener({
        val intent= Intent(this@MainActivity, SendMoneyActivity::class.java)
            startActivity(intent)

        })
    }
}
package com.allforyou.app

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.allforyou.app.AccessTokenManager
import com.allforyou.app.AccountA
import com.allforyou.app.ApiService
import com.allforyou.app.MypageAccountListAdapter
import com.allforyou.app.RetrofitClient
import com.allforyou.app.databinding.ActivityMypageAccountListBinding
import com.allforyou.app.retrofit.ApiRespond
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class MypageAccountListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMypageAccountListBinding
    private val apiService: ApiService = RetrofitClient.getClient()
    private lateinit var accountList: List<AccountA>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMypageAccountListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnPrevious.setOnClickListener{
            Log.d("사용자 커튼 클릭","뒤로가기 버튼을 눌렀습니다")
            val inttent=Intent(this, MainActivity::class.java)
            intent.putExtra("SELECTED_TAB", "MYPAGE")
            startActivity(intent)
        }



        // MainScope를 사용하여 코루틴을 메인 스레드에서 실행
        MainScope().launch {
            try {
                // accountList를 초기화하는 fetchAccountList를 호출
                accountList = fetchAccountList()

                Log.d("!!!!!!!!!!!!!!!!!!!!!!","fetch완료")
                Log.d("AcocuntList정보1111 !!!!",accountList.toString())
                val adapter = MypageAccountListAdapter(accountList)
                binding.rv.adapter = adapter
                Log.d("AcocuntList정보222 !!!!",accountList.toString())
                Log.d("!!!!!!!!!!!!!!!!!!!!!!","Adapter연결 완료")
                binding.rv.layoutManager = LinearLayoutManager(this@MypageAccountListActivity)

                // 클릭 이벤트 추가
                adapter.itemClick = object : MypageAccountListAdapter.ItemClick {
                    override fun onClick(view: View, position: Int) {
                        Toast.makeText(
                            this@MypageAccountListActivity,
                            "${accountList[position].accountName}이 선택되었습니다",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private suspend fun fetchAccountList(): List<AccountA> = withContext(Dispatchers.IO) {
        try {
            // 네트워크 요청을 통해 계좌 리스트 가져오기
            val response: Response<ApiRespond<List<AccountA>>> =
                apiService.getAllAccount(AccessTokenManager.getBearer())

            if (response.isSuccessful) {
                val apiResponse = response.body()
                Log.d("response! ", apiResponse.toString())

                // Check the code and handle the response accordingly
                Log.d("apiResponse값은 > ", apiResponse?.code.toString())
                when (apiResponse?.code) {
                    200 -> {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                this@MypageAccountListActivity,
                                "계좌 리스트 불러오기 성공!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        Log.d("ㅜㅜㅜㅜㅜ", "Adapter연결 성공 반환할 data : ${apiResponse.data}")

                        // apiResponse.data가 null이 아닐 때만 반환하도록 수정
                        apiResponse?.data?.let {
                            return@withContext it
                        } ?: run {
                            // data가 null이면 처리할 내용 추가
                            Log.e("fetchAccountList", "apiResponse.data is null")
                            // 여기서 리턴값을 무엇으로 할지 결정해야 합니다. 예를 들어, 빈 리스트를 반환하거나 예외를 던지는 등의 처리가 가능합니다.
                            return@withContext emptyList()
                        }
                    }
                    // Handle other response codes if needed
                    else -> {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                this@MypageAccountListActivity,
                                "계좌 리스트 불러오기 실패 ㅜㅜ",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        Log.d("ㅜㅜㅜㅜㅜ", "Adapter연결 실패")

                        // 이 부분에서 emptyList()가 함수의 반환값이 됩니다.
                        return@withContext emptyList()
                    }
                }
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }


}

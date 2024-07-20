package com.allforyou.app

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.allforyou.app.databinding.ActivityAccountListBinding
import com.allforyou.app.retrofit.ApiRespond
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Response

// 올바른 import 구문 추가


class AccountListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAccountListBinding
    private lateinit var apiService: ApiService
    private var linkedAccountId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.previous.setOnClickListener {
            val intent = Intent(this, MakePetAccountActivity::class.java)
            startActivity(intent)
        }

        apiService = RetrofitClient.getClient()
        GlobalScope.launch(Dispatchers.Main) {
            try {
                // ApiService 인터페이스를 통해 호출
                val response: Response<ApiRespond<List<ChargingAccount>>> =
                    apiService.getChargingAccountList(AccessTokenManager.getBearer())

                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    Log.d("연결가능한 계좌 리스트 조회결과", apiResponse.toString())

                    // Check the code and handle the response accordingly
                    when (apiResponse?.code) {

                        200 -> {
                            // Success, handle the list of access applications
                            val accessList = apiResponse.data

                            Toast.makeText(
                                this@AccountListActivity,
                                "리스트 불러오기 성공!",
                                Toast.LENGTH_SHORT
                            ).show()

                            if (accessList != null) {

                                val adapter = AccessAccountAdapter(accessList)
                                binding.rv.adapter = adapter
                                binding.rv.layoutManager =
                                    LinearLayoutManager(this@AccountListActivity)



                                Log.d("작업상황 ","계좌 리스트 적용 완료")


                                adapter.itemClick = object : AccessAccountAdapter.ItemClick {  //클릭이벤트추가부분
                                    override fun onClick(view: View, position: Int) {
                                        Toast.makeText(this@AccountListActivity, accessList[position].accountName+"이 선택되었습니다", Toast.LENGTH_LONG).show()

                                        // 해당 계좌의 accountId를 Intent에 추가
                                        val intent = Intent(this@AccountListActivity, MakePetAccountActivity::class.java)
                                        intent.putExtra("accountId", accessList[position].accountId)
                                        RemittanceRequestManager.linkedAccountId=accessList[position].accountId

                                        // 페이지 이동
                                        startActivity(intent)
                                    }
                                }
                            }
                        }

                        else -> {
                            // 처리할 다른 응답 코드가 있다면 여기에 작성
                        }
                    }
                } else {
                    // Handle unsuccessful response
                    // You might want to check response.errorBody() for more details
                }
            } catch (e: Exception) {
                // Handle exception
                Log.d("예외발생", "계좌 불러오기 실패")
                e.printStackTrace()
            }
        }
    }

    // Extension function to convert dp to pixels
    private fun Int.dpToPx(): Int {
        val scale = resources.displayMetrics.density
        return (this * scale + 0.5f).toInt()
    }
}

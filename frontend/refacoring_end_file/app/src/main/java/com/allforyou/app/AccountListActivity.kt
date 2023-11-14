package com.allforyou.app

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
    private var linkedAccountId:Int=1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.previous.setOnClickListener({
            val intent= Intent(this,MakePetAccountActivity::class.java)
//            intent.putExtra("linkedpetId", linkedpetId)

            startActivity(intent)
        })

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

                            // Clear existing views in the LinearLayout
                            binding.accountList.removeAllViews()

// Check if the accessList is not null
                            if (accessList != null) {
//                                for (account in accessList) {
//                                    // Inflate the layout for each account
//                                    val accountItemBinding: ItemAccountListAccessBinding =
//                                        DataBindingUtil.inflate(layoutInflater, R.layout.item_account_list_access, null, false)
//
//                                    // Set the account data to the layout
//                                    accountItemBinding.account = account
//
//                                    // Add the inflated layout to the parent LinearLayout
//                                    binding.accountList.addView(accountItemBinding.root)
//
//                                    // Add a Space between account items
//                                    val space = Space(this@AccountListActivity)
//                                    space.layoutParams = ViewGroup.LayoutParams(
//                                        ViewGroup.LayoutParams.MATCH_PARENT,
//                                        resources.getDimensionPixelSize(R.dimen.space_between_accounts)
//                                    )
//                                    binding.accountList.addView(space)
//                                }
                            }
                        }
                        // Handle other response codes if needed
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

package com.allforyou.app

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.allforyou.app.databinding.ActivityMakePetAccountBinding

import com.allforyou.app.retrofit.ApiRespond
import com.allforyou.app.retrofit.PetAccountRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Response

class MakePetAccountActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMakePetAccountBinding
    private var petGender:String="male"
    private var petNeutered:Boolean=false
    private lateinit var apiService: ApiService
    private var limitTypedIdList: MutableList<Int> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMakePetAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        apiService = RetrofitClient.getClient()

        binding.petGenderMale.setOnClickListener {
            petGender = "male"
            Toast.makeText(this, "남자로 선택되었습니다", Toast.LENGTH_SHORT).show()
        }

        binding.petGenderFemale.setOnClickListener {
            petGender = "female"
            Toast.makeText(this, "여자로 선택되었습니다", Toast.LENGTH_SHORT).show()
        }

// 중성화 여부 선택
        binding.isNeuturedNo.setOnClickListener {
            petNeutered = false
            Toast.makeText(this, "중성화 안 했어요로 선택되었습니다", Toast.LENGTH_SHORT).show()
        }

        binding.isNeuturedOk.setOnClickListener {
            petNeutered = true
            Toast.makeText(this, "중성화 했어요로 선택되었습니다", Toast.LENGTH_SHORT).show()
        }

// 각 버튼에 클릭 리스너 할당
        binding.type1.setOnClickListener { addToLimitTypedIdList(1) }
        binding.type2.setOnClickListener { addToLimitTypedIdList(2) }
        binding.type3.setOnClickListener { addToLimitTypedIdList(3) }
        binding.type4.setOnClickListener { addToLimitTypedIdList(4) }
        binding.type5.setOnClickListener { addToLimitTypedIdList(5) }

        binding.seeAccessableAccounts.setOnClickListener({
            val intent=Intent(this,AccountListActivity::class.java)
            startActivity(intent)
        })

        binding.makeAccountBtn.setOnClickListener {
            val accountPwd = "0000"
            val petBirth = binding.petBirth.text.toString()
            val petBreed = binding.petBreed.text.toString()
            val linkedAccountId = "1"
            val petName = binding.petName.text.toString()
            val accountName = "${petName}의 NH예금통장"
            val petPhoto = "1"
            val petWeightt = binding.petWeight.text.toString().toDouble()
            val rfidCode = "12134"
            val petGenderr=petGender
            val petNeuteredd=petNeutered

            val petAccountRequest = PetAccountRequest(
                accountName,
                accountPwd,
                linkedAccountId,
                petBirth,
                petBreed,
                petGenderr,
                petName,
                petNeuteredd,
                petPhoto,
                petWeightt,
                rfidCode,
                limitTypedIdList
            )



            GlobalScope.launch(Dispatchers.Main) {
                try {
                    // ApiService 인터페이스를 통해 호출
                    val response: Response<ApiRespond<Int>> =
                        apiService.makePetAccount(AccessTokenManager.getBearer(), petAccountRequest)

                    if (response.isSuccessful) {
                        val apiResponse = response.body()
                        Log.d("계좌 발급 결과", apiResponse.toString())


                        // Check the code and handle the response accordingly
                        when (apiResponse?.code) {
                            200 -> {
                                // Success, handle the list of access applications
                                val accessList = apiResponse.data
                                Toast.makeText(
                                    this@MakePetAccountActivity,
                                    "펫계좌가 잘 발급되었어요",
                                    Toast.LENGTH_SHORT
                                ).show()
                                val intent =
                                    Intent(this@MakePetAccountActivity, MainActivity::class.java)
                                startActivity(intent)
                            }
                            // Handle other response codes if needed
                            else -> {
                                Toast.makeText(
                                    this@MakePetAccountActivity,
                                    "펫계좌가 잘 발급 실패...",
                                    Toast.LENGTH_SHORT
                                ).show()
                                // Handle other cases
                            }
                        }
                    } else {
                        // Handle unsuccessful response
                        // You might want to check response.errorBody() for more details
                    }
                } catch (e: Exception) {
                    // Handle exception
                    Log.d("계좌 발급 결과", "실패 삐빅ㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱ")
                    e.printStackTrace()
                }
            }


        }


    }
    // 클릭 리스너에서 리스트에 값을 추가하는 함수 정의
    private fun addToLimitTypedIdList(value: Int) {
        limitTypedIdList.add(value)
    }
}
package com.allforyou.app

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.allforyou.app.databinding.ActivityMakePetAccountBinding

import com.allforyou.app.retrofit.ApiRespond
import com.allforyou.app.retrofit.PetAccountRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Response

class MakePetAccountActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMakePetAccountBinding
    private var petGender: String = "male"
    private var petNeutered: Boolean = false
    private lateinit var apiService: ApiService
    private var limitType = "11111"
    private var linkedAccountId: Long = -1L
    private var rfid: String = ""
    private  var limitTypes : Array<Long> = Array(5) {0L}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMakePetAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)



        apiService = RetrofitClient.getClient()

        binding.petGenderMale.setOnClickListener {
            petGender = "male"
            binding.petGenderMale.background=ContextCompat.getDrawable(this@MakePetAccountActivity, R.drawable.button_outline_no_green_round)
            binding.petGenderMale.setTextColor(ContextCompat.getColor(this@MakePetAccountActivity, R.color.no_green))

            binding.petGenderFemale.background=ContextCompat.getDrawable(this@MakePetAccountActivity, R.drawable.button_outline_grey)
            binding.petGenderFemale.setTextColor(ContextCompat.getColor(this@MakePetAccountActivity, R.color.gray60))

            Toast.makeText(this, "남아로 선택되었습니다", Toast.LENGTH_SHORT).show()
        }

        binding.petGenderFemale.setOnClickListener {
            petGender = "female"

            binding.petGenderFemale.background=ContextCompat.getDrawable(this@MakePetAccountActivity, R.drawable.button_outline_no_green_round)
            binding.petGenderFemale.setTextColor(ContextCompat.getColor(this@MakePetAccountActivity, R.color.no_green))

            binding.petGenderMale.background=ContextCompat.getDrawable(this@MakePetAccountActivity, R.drawable.button_outline_grey)
            binding.petGenderMale.setTextColor(ContextCompat.getColor(this@MakePetAccountActivity, R.color.gray60))

            Toast.makeText(this, "여아로 선택되었습니다", Toast.LENGTH_SHORT).show()
        }

// 중성화 여부 선택
        binding.isNeuturedNo.setOnClickListener {
            petNeutered = false

            binding.isNeuturedNo.background=ContextCompat.getDrawable(this@MakePetAccountActivity, R.drawable.button_outline_no_green_round)
            binding.isNeuturedNo.setTextColor(ContextCompat.getColor(this@MakePetAccountActivity, R.color.no_green))

            binding.isNeuturedOk.background=ContextCompat.getDrawable(this@MakePetAccountActivity, R.drawable.button_outline_grey)
            binding.isNeuturedOk.setTextColor(ContextCompat.getColor(this@MakePetAccountActivity, R.color.gray60))
            Toast.makeText(this, "중성화 안 했어요로 선택되었습니다", Toast.LENGTH_SHORT).show()
        }

        binding.isNeuturedOk.setOnClickListener {
            petNeutered = true

            binding.isNeuturedOk.background=ContextCompat.getDrawable(this@MakePetAccountActivity, R.drawable.button_outline_no_green_round)
            binding.isNeuturedOk.setTextColor(ContextCompat.getColor(this@MakePetAccountActivity, R.color.no_green))

            binding.isNeuturedNo.background=ContextCompat.getDrawable(this@MakePetAccountActivity, R.drawable.button_outline_grey)
            binding.isNeuturedNo.setTextColor(ContextCompat.getColor(this@MakePetAccountActivity, R.color.gray60))

            Toast.makeText(this, "중성화 했어요로 선택되었습니다", Toast.LENGTH_SHORT).show()
        }

        //연결된 계좌가 선택되었다면
        if (intent.getStringExtra("accountId") != null) {
            val temp = intent.getStringExtra("accountId")
            Log.d("지금 연결된 계좌의 아이디", temp ?: "없음")
            linkedAccountId = temp?.toLong() ?: -1L


            val drawableResId = if (linkedAccountId!=-1L) {
                R.drawable.button_outline_no_green_round
            } else {
                R.drawable.button_outline_grey
            }

            val drawable = ContextCompat.getDrawable(this@MakePetAccountActivity, drawableResId)
            binding.seeAccessableAccounts.background = drawable
            Log.d("limit Type","연결계좌 선택 완료")


        }

// 각 버튼에 클릭 리스너 할당
        binding.type1.setOnClickListener {
            val temp = limitType.substring(0, 1)
            limitType = if (temp.equals( "1")) {
                limitTypes.set(0,1)
                limitType.replaceRange(0, 1, "0")
            } else {
                limitTypes.set(0,0)
                limitType.replaceRange(0, 1, "1")
            }


            // Drawable 리소스 가져오기
            val drawableResId = if (temp.equals( "1")) {
                R.drawable.button_outline_no_green_round
            } else {
                R.drawable.button_outline_grey
            }

            // Drawable 설정
            val drawable = ContextCompat.getDrawable(this@MakePetAccountActivity, drawableResId)
            binding.type1.background = drawable
            Log.d("limit Type",limitType)
        }



        binding.type2.setOnClickListener {
            val temp = limitType.substring(1, 2)
            limitType = if (temp.equals( "1")) {
                limitTypes.set(1,1)
                limitType.replaceRange(1, 2, "0")
            } else {
                limitTypes.set(1,0)
                limitType.replaceRange(1, 2, "1")
            }

            // Drawable 리소스 가져오기
            val drawableResId = if (temp.equals( "1")) {
                R.drawable.button_outline_no_green_round

            } else {
                R.drawable.button_outline_grey
            }

            // Drawable 설정
            val drawable = ContextCompat.getDrawable(this@MakePetAccountActivity, drawableResId)
            binding.type1.background = drawable
            Log.d("limit Type",limitType)
        }
        binding.type3.setOnClickListener {
            val temp = limitType.substring(2, 3)
            limitType = if (temp.equals( "1")) {
                limitTypes.set(2,1)
                limitType.replaceRange(2, 3, "0")
            } else {
                limitTypes.set(2,0)
                limitType.replaceRange(2, 3, "1")
            }

            // Drawable 리소스 가져오기
            val drawableResId = if (temp.equals( "1")) {
                R.drawable.button_outline_no_green_round

            } else {
                R.drawable.button_outline_grey
            }

            // Drawable 설정
            val drawable = ContextCompat.getDrawable(this@MakePetAccountActivity, drawableResId)
            binding.type1.background = drawable
            Log.d("limit Type",limitType)
        }
        binding.type4.setOnClickListener {
            val temp = limitType.substring(3, 4)
            limitType = if (temp == "1") {
                limitTypes.set(3,1)
                limitType.replaceRange(3, 4, "0")
            } else {
                limitTypes.set(3,0)
                limitType.replaceRange(3, 4, "1")
            }

            // Drawable 리소스 가져오기
            val drawableResId = if (temp == "1") {
                R.drawable.button_outline_no_green_round

            } else {
                R.drawable.button_outline_grey
            }

            // Drawable 설정
            val drawable = ContextCompat.getDrawable(this@MakePetAccountActivity, drawableResId)
            binding.type1.background = drawable
            Log.d("limit Type",limitType)
        }
        binding.type5.setOnClickListener {
            val temp = limitType.substring(4, 5)
            limitType = if (temp == "1") {
                limitTypes.set(4,1)
                limitType.replaceRange(4, 5, "0")
            } else {
                limitTypes.set(4,0)
                limitType.replaceRange(4, 5, "1")
            }

            // Drawable 리소스 가져오기
            val drawableResId = if (temp == "1") {
                R.drawable.button_outline_no_green_round

            } else {
                R.drawable.button_outline_grey
            }

            // Drawable 설정
            val drawable = ContextCompat.getDrawable(this@MakePetAccountActivity, drawableResId)
            binding.type1.background = drawable

            Log.d("limit Type",limitType)
        }

        binding.seeAccessableAccounts.setOnClickListener {
            val intent = Intent(this, AccountListActivity::class.java)
            startActivity(intent)
        }

        binding.makeAccountBtn.setOnClickListener {
            val accountPwd = "0000"
            val petBirth = binding.petBirth.text.toString()
            val petBreed = binding.petBreed.text.toString()

            val linkedAccountId = linkedAccountId
            val petName = binding.petName.text.toString()
            val accountName = "${petName}의 NH반려동물통장"

            val petPhoto = "1"
            val petWeightt = binding.petWeight.text.toString().toDouble()
            val rfidCode = rfid
            val petGenderr = petGender
            val petNeuteredd = petNeutered

            val limitTypedIdList:List<Long>?

            limitTypedIdList = limitTypes.asList()

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

            //연결된 계좌가 없는 경우
            if (linkedAccountId == -1L) {
                Toast.makeText(this, "연결할 계좌를 선택해주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener

            }

            //rfid가 등록되지 않은 경우
            if (rfid.equals("")) {
                Toast.makeText(this, "동물 등록 칩을 등록해주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener

            }


//계좌 발급 버튼 클릭시
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
//    private fun addToLimitTypedIdList(value: Int) {
//        limitTypedIdList.add(value)
//    }
}
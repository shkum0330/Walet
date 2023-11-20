package com.allforyou.app

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.allforyou.app.databinding.FragmentMypageBinding
import com.allforyou.app.databinding.FragmentPetBinding
import com.allforyou.app.retrofit.ApiRespond
import com.allforyou.app.retrofit.TransferInfoResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private val apiService = RetrofitClient.getClient()
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MypageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MypageFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentMypageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMypageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //들어온 결제요청이 있는지 확인
        //FCM기반으로 결제요청 내용 확인
        GlobalScope.launch(Dispatchers.Main) {
            try {
                // ApiService 인터페이스를 통해 호출
                val response: Response<ApiRespond<PaymentInfo>> =
                    apiService.getPaymentInfo(AccessTokenManager.getBearer())
                Log.d("apipaip", "마이페이지에서 결제 요청 내역 확인")
                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    Log.d("결제요청내용 확인", apiResponse.toString())


                    // Check the code and handle the response accordingly
                    when (apiResponse?.code) {
                        200 -> {
                            // Success, handle the list of access applications
                            val info: PaymentInfo = apiResponse.data
                            binding.seePay.text = "1개의 요청"


//                            binding.storeCall.text=info.phoneNumber
                        }
                        // Handle other response codes if needed
                        else -> {
                            binding.seePay.text = "0개의 요청"
//                            Toast.makeText(
//                                this@PayRequestActivity,
//                                "결제정보를 불러오는데 실패했습니다",
//                                Toast.LENGTH_SHORT
//                            ).show()
                            // Handle other cases
                        }
                    }
                } else {
                }
            } catch (e: Exception) {
                binding.seePay.text = "0개의 요청"
                // Handle exception
                Log.d("계좌 발급 결과", "실패 삐빅")
                e.printStackTrace()
            }
        }

        binding.btnPayment.setOnClickListener {
            if (binding.seePay.text.equals("1개의 요청")) {
//결제 요청이 있는지 다시 확인
                GlobalScope.launch(Dispatchers.Main) {
                    try {
                        // ApiService 인터페이스를 통해 호출
                        val response: Response<ApiRespond<PaymentInfo>> =
                            apiService.getPaymentInfo(AccessTokenManager.getBearer())
                        Log.d("apipaipㅔㅔㅔㅔㅔㅔ", "마이페이지에서 결제 요청 내역 확인")
                        if (response.isSuccessful) {
                            val apiResponse = response.body()
                            Log.d("결제요청내용 확인", apiResponse.toString())


                            // Check the code and handle the response accordingly
                            when (apiResponse?.code) {
                                200 -> {
                                    // Success, handle the list of access applications
                                    val info: PaymentInfo = apiResponse.data

                                    val intent = Intent(requireActivity(), PayRequestActivity::class.java)
                                    Log.d("사용자 클릭", "계좌 양도 버튼을 눌렀습니다")
                                    startActivity(intent)



//                            binding.storeCall.text=info.phoneNumber
                                }
                                // Handle other response codes if needed
                                else -> {

                                    Toast.makeText(
                                        requireContext(),
                                        "결제정보를 불러오는데 실패했습니다",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        } else {
                            Toast.makeText(
                                requireContext(),
                                "결제정보를 불러오는데 실패했습니다",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } catch (e: Exception) {
                        binding.seePay.text = "0개의 요청"
                        // Handle exception
                        Log.d("계좌 발급 결과", "실패 삐빅")
                        e.printStackTrace()

                        Toast.makeText(
                            requireContext(),
                            "결제정보를 불러오는데 실패했습니다",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }



            } else {
                Toast.makeText(requireContext(), "결제요청이 없습니다", Toast.LENGTH_SHORT).show()
            }
        }

        binding.name.text = AccessTokenManager.getInstance().userName + "님"
        binding.btnPassConfirm.setOnClickListener {

//            들어온 양도 요청이 있는지 확인
            lifecycleScope.launch(Dispatchers.Main) {
                try {
                    // Perform the asynchronous API call
                    val response: Response<ApiRespond<TransferInfoResponse>> =
                        apiService.getTransferInfo(AccessTokenManager.getBearer())

                    if (response.isSuccessful) {
                        val apiResponse = response.body()
                        Log.d("통신 데이터", apiResponse.toString())

                        // Check the code and handle the response accordingly
                        when (apiResponse?.code) {
                            200 -> {
                                // Success, handle the transfer info
                                val intent =
                                    Intent(requireActivity(), PassDetailActivity::class.java)
                                Log.d("사용자 클릭", "양도확인 버튼을 눌렀습니다+ 양도 신청내역 존재")
                                startActivity(intent)
                            }
                            // Handle other response codes if needed
                            else -> {
                                Toast.makeText(context, "받은 양도 신청이 없습니다", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else {
                        Toast.makeText(context, "받은 양도 신청이 없습니다", Toast.LENGTH_SHORT).show()
                        // Handle unsuccessful response
                        // You might want to check response.errorBody() for more details
                    }
                } catch (e: Exception) {
                    // Handle exception
                    e.printStackTrace()
                    Toast.makeText(context, "받은 양도 신청이 없습니다", Toast.LENGTH_SHORT).show()
                }
            }


//
//            val intent=Intent(requireActivity(), PassDetailActivity::class.java )
//            Log.d("사용자 클릭", "양도확인 버튼을 눌렀습니다")
//            startActivity(intent)
        }

        binding.btnPass.setOnClickListener {
            val intent = Intent(requireActivity(), PassApplyActivity::class.java)
            Log.d("사용자 클릭", "계좌 양도 버튼을 눌렀습니다")
            startActivity(intent)
        }

        binding.btnAllAccount.setOnClickListener {
            val intent = Intent(requireActivity(), MypageAccountListActivity::class.java)
            Log.d("사용자 클릭", "모든계좌 리스트 보기 버튼을 눌렀습니다")
            startActivity(intent)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MypageFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MypageFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
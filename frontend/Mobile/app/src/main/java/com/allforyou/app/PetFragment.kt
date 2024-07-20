package com.allforyou.app

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.allforyou.app.databinding.FragmentHomeBinding
import com.allforyou.app.databinding.FragmentPetBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PetFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PetFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentPetBinding
    private lateinit var petAccounts : List<AnimalAccountDetailResponse.AnimalAccountDetail>

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
        binding = FragmentPetBinding.inflate(inflater, container, false)

//        binding.userName.text=RemittanceRequestManager.name

        loadPetAccount()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        binding.userName.text = AccessTokenManager.getInstance().userName
//        binding.userName.text=RemittanceRequestManager.name

        binding.btnViewApply.setOnClickListener{
            val intent=Intent(requireActivity(), RequestViewActivity::class.java)
            Log.d("사용자 클릭", "계좌 열람 신청을 눌렀습니다")
            startActivity(intent)
        }

        binding.btnMakeAccount.setOnClickListener{
            val intent=Intent(requireActivity(), MakePetAccountActivity::class.java)
            Log.d("사용자 클릭", "펫계좌 발급 버튼을 눌렀습니다")
            startActivity(intent)
        }

        binding.btnAccountPass.setOnClickListener{
            val intent=Intent(requireActivity(), PassApplyActivity::class.java)
            Log.d("사용자 클릭", "계좌 양도 버튼을 눌렀습니다")
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
         * @return A new instance of fragment PetFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PetFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    fun loadPetAccount(){
        var retrofitAPI = RetrofitClient.getClient()
        retrofitAPI.loadPetAccounts(AccessTokenManager.getBearer()).enqueue(object :
            Callback<AnimalAccountDetailResponse> {
            override fun onResponse(call: Call<AnimalAccountDetailResponse>, response: Response<AnimalAccountDetailResponse>) {
                Log.d("my_tag","요청 사항 : "+AccessTokenManager.getBearer())
                Log.d("my_tag",response.toString())
                if (response.isSuccessful) {
                    val petAccountList = response.body()!!.data
                    if (petAccountList != null) {
                        petAccounts = petAccountList;

                        //연결계좌 등록
                        Log.d("petpetpept account",petAccounts.toString())
                        RemittanceRequestManager.linkedAccountNumber=petAccounts[0].linkedAccountNumber

                        Log.d("my_tag","펫 계좌 리스트 로딩 성공")
                        Log.d("my_tag",petAccountList.toString())
                        val listView = binding.petAccountsList
                        val adapter = PetAccountAdapter(ArrayList(petAccounts),requireContext())
                        listView.adapter = adapter
                    } else {
                        Log.d("my_tag","펫 계좌 리스트 NULL 반화됨")
                        // Handle null response body
                    }
                } else {
                    Log.d("my_tag","펫 계좌 리스트 실패")
                    // Handle unsuccessful response
                }
            }
            override fun onFailure(call: Call<AnimalAccountDetailResponse>, t: Throwable) {
                Log.d("my_tag","펫 계좌 리스트: 네트워크 오류")
            }
        })
    }
}
package com.allforyou.app

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.allforyou.app.databinding.ActivityMainBinding
import com.allforyou.app.databinding.FragmentHomeBinding
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val retrofitAPI = RetrofitClient.getClient()

    private lateinit var binding:FragmentHomeBinding

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
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
//        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(!NoticeManager.init){
            Log.d("my_tag","공지사항을 불러옵니다")
            loadNotice()
        }else{
            Log.d("my_tag","이미 저장된 공지사항을 불러옵니다")
            setNotice()
        }
        loadAccounts()

        binding.notice.setOnClickListener {
            val intent = Intent(requireActivity(), NoticeActivity::class.java)
            startActivity(intent)
        }

        binding.petpay.setOnClickListener {
            val intent = Intent(requireActivity(), PayPaymentActivity::class.java)
            Log.d("버튼 클릭!!!!!!!!!!!","   ____            __\n" +
                    "  / __/__ ___  ___/ /\n" +
                    " _\\ \\/ -_) _ \\/ _  / \n" +
                    "/___/\\__/_//_/\\_,_/  ")
            startActivity(intent)
        }


        binding.send.setOnClickListener {
            val intent = Intent(requireActivity(), TransferTargetActivity::class.java)
            Log.d("버튼 클릭!!!!!!!!!!!","사업자계좌의 이체버튼을 클릭했습니다")
            startActivity(intent)
        }
        binding.accountLogStore.setOnClickListener {
            // 여기서 API 로드

            getTransactionLog(5);
        }
        binding.send2.setOnClickListener {
            val intent = Intent(requireActivity(), TransferTargetActivity::class.java)
            Log.d("버튼 클릭!!!!!!!!!!!","일반 계좌의 이체버튼을 클릭했습니다")
            startActivity(intent)
        }
    }

    fun loadNotice() {
//        var retrofitAPI = RetrofitClient.getClient()
//
//        val header : MutableMap<String, String> = mutableMapOf()
//        header.put("AccessToken", AccessTokenManager.getBearer())
//        header.put("owner-id", 1)
//        retrofitAPI.뭐시기(header).enqueue(object: Callback<....>)

        retrofitAPI.loadNotice(AccessTokenManager.getBearer()).enqueue(object : Callback<NoticeResponse> {
            override fun onResponse(call: Call<NoticeResponse>, response: Response<NoticeResponse>) {
                Log.d("my_tag","요청 사항 : "+AccessTokenManager.getBearer())
                if (response.isSuccessful) {
                    val noticeResponse = response.body()!!.data
                    NoticeManager.initData(noticeResponse!!)
                    Log.d("my_tag",noticeResponse.toString())
                    if (noticeResponse != null) {
                        Log.d("my_tag","공지사항을 로딩 성공")
                        setNotice()
                    } else {
                        Log.d("my_tag","공지사항: NULL 반화됨")
                        // Handle null response body
                    }
                } else {
                    Log.d("my_tag","공지사항: 실패")
                    // Handle unsuccessful response
                }
            }
            override fun onFailure(call: Call<NoticeResponse>, t: Throwable) {
                Log.d("my_tag","공지사항을: 네트워크 오류")
            }
        })
    }
    fun setNotice(){
        binding.noticeTitle.text = NoticeManager.getInstance().title
        binding.noticeSubtitle.text = NoticeManager.getInstance().subTitle
        val imageView : ImageView = binding.noticeImg
        val imageUrl = NoticeManager.getInstance().bannerImg;

        Picasso.get().load(imageUrl).into(imageView)
    }

    fun loadAccounts(){
        var retrofitAPI = RetrofitClient.getClient()

        retrofitAPI.loadGeneralAccounts(AccessTokenManager.getBearer()).enqueue(object : Callback<HomeAccountResponse> {
            override fun onResponse(call: Call<HomeAccountResponse>, response: Response<HomeAccountResponse>) {
                Log.d("my_tag","요청 사항 : "+AccessTokenManager.getBearer())
                Log.d("my_tag",response.toString())
                if (response.isSuccessful) {
                    val homeAccountList = response.body()!!.data
//                    NoticeManager.initData(noticeResponse!!)
                    if (homeAccountList != null) {
                        Log.d("my_tag","일반 계좌 리스트 로딩 성공")
                        Log.d("my_tag",homeAccountList.toString())
                    } else {
                        Log.d("my_tag","일반 계좌 리스트 NULL 반화됨")
                        // Handle null response body
                    }
                } else {
                    Log.d("my_tag","일반 계좌 리스트 실패")
                    // Handle unsuccessful response
                }
            }
            override fun onFailure(call: Call<HomeAccountResponse>, t: Throwable) {
                Log.d("my_tag","일반 계좌 리스트: 네트워크 오류")
            }
        })
        retrofitAPI.loadBusinessAccounts(AccessTokenManager.getBearer()).enqueue(object : Callback<BusinessAccountResponse> {
            override fun onResponse(call: Call<BusinessAccountResponse>, response: Response<BusinessAccountResponse>) {
                Log.d("my_tag","요청 사항 : "+AccessTokenManager.getBearer())
                if (response.isSuccessful) {
                    val businessAccountList = response.body()!!.data
//                    NoticeManager.initData(noticeResponse!!)
                    if (businessAccountList != null) {
                        Log.d("my_tag","사업자 계좌 리스트 로딩 성공")
                        Log.d("my_tag",businessAccountList.toString())
                    } else {
                        Log.d("my_tag","사업자 계좌 리스트 NULL 반화됨")
                        // Handle null response body
                    }
                } else {
                    Log.d("my_tag","사업자 계좌 리스트 실패")
                    // Handle unsuccessful response
                }
            }
            override fun onFailure(call: Call<BusinessAccountResponse>, t: Throwable) {
                Log.d("my_tag","사업자 계좌 리스트: 네트워크 오류")
            }
        })
    }

    fun getTransactionLog(accountId : Long){
        retrofitAPI.loadTransactionLog(AccessTokenManager.getBearer(), accountId).enqueue(object : Callback<TransactionLogResponse> {
            override fun onResponse(call: Call<TransactionLogResponse>, response: Response<TransactionLogResponse>) {
                Log.d("my_tag","요청 사항 : "+AccessTokenManager.getBearer())
                if (response.isSuccessful) {
                    val transactionLog : List<TransactionLogResponse.TransactionLog>? = response.body()!!.data
//                    NoticeManager.initData(noticeResponse!!)
                    Log.d("my_tag",transactionLog.toString())
                    if (transactionLog != null) {
                        Log.d("my_tag","거래 내역 로딩 성공")

                        val intent = Intent(requireActivity(), TransactionActivity::class.java)
                        intent.putExtra("transactionLog", ArrayList(transactionLog));
                        Log.d("버튼 클릭!!!!!!!!!!!","사업자계좌의 거래내역을 클릭했습니다")
                        startActivity(intent)
                    } else {
                        Log.d("my_tag","거래 내역 NULL 반화됨")
                        // Handle null response body
                    }
                } else {
                    Log.d("my_tag","거래 내역 로딩 실패")
                    // Handle unsuccessful response
                }
            }
            override fun onFailure(call: Call<TransactionLogResponse>, t: Throwable) {
                Log.d("my_tag","거래 내역: 네트워크 오류")
            }
        })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
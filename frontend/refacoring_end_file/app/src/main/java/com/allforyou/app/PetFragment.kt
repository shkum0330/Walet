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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recharge.setOnClickListener {
            val intent = Intent(requireActivity(), ChargePaymentActivity::class.java)
            Log.d("버튼 클릭!!!!!!!!!!!","  _______                    \n" +
                    " / ___/ /  ___ ________ ____ \n" +
                    "/ /__/ _ \\/ _ `/ __/ _ `/ -_)\n" +
                    "\\___/_//_/\\_,_/_/  \\_, /\\__/ \n" +
                    "                  /___/      ")
            startActivity(intent)
        }

        binding.btnViewApply.setOnClickListener{
            val intent=Intent(requireActivity(), RequestViewActivity::class.java)
            Log.d("사용자 클릭", "계좌 열람 신청을 눌렀습니다")
            startActivity(intent)
        }

        binding.btnMakeAccount.setOnClickListener({
            val intent=Intent(requireActivity(), MakePetAccountActivity::class.java)
            Log.d("사용자 클릭", "펫계좌 발급 버튼을 눌렀습니다")
            startActivity(intent)
        })

        binding.btnAccountPass.setOnClickListener({
            val intent=Intent(requireActivity(), PassApplyActivity::class.java)
            Log.d("사용자 클릭", "계좌 양도 버튼을 눌렀습니다")
            startActivity(intent)
        })
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

    }
}
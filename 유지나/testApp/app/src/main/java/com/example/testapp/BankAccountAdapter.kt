package com.example.testapp

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.example.testapp.databinding.ItemRecycleAccountInfoBinding

class BankAccountAdapter:RecyclerView.Adapter<BankAccountAdapter.Holder>() {

    private var bankAccountList=listOf<BankAccount>()

    inner class Holder(private val binding: ItemRecycleAccountInfoBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(pos: Int) {

            binding.bankAccountTitle.text = bankAccountList[pos].title?: "농협 기본 튼튼 통장"
            binding.accountNumber.text = bankAccountList[pos].accountNumber?: "111-111-111111"
            binding.balance.text = bankAccountList[pos].balance?: "0"

            Log.d("계좌정보 받아옴 정도", "바인딩 완료")
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view =ItemRecycleAccountInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return bankAccountList.size
    }

    fun setList(list: List<BankAccount>) {
        bankAccountList = list
//            Log.d("어댑터 진행상황", "일단 리스트 옮기기 까지 성공"+diaryList.toString().substring(0,30))
    }

}
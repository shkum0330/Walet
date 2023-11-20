package com.allforyou.app

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.allforyou.app.databinding.ItemAccountListAccessBinding
import com.allforyou.app.databinding.MypageAccountItemBinding
import com.allforyou.app.retrofit.ListAllAccountResponse
import kotlinx.coroutines.NonDisposableHandle
import kotlinx.coroutines.NonDisposableHandle.parent

class MypageAccountListAdapter(val accountList: List<AccountA>) :
    RecyclerView.Adapter<MypageAccountListAdapter.Holder>() {
    inner class Holder(val binding: ItemAccountListAccessBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val accountId = binding.accountId
        val accountName = binding.accountName
        val accountNumber = binding.accountNumber
        val balance = binding.balance
    }

    //클릭이벤트를 위한 인터페이스
    interface ItemClick {
        fun onClick(view: View, position: Int)
    }
    //클릭이벤트를 위한 변수
    var itemClick: ItemClick? = null
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MypageAccountListAdapter.Holder {
        Log.d("Adapter에서 다둘 데이터!!!!!!!!!!!!!!!!!", accountList.toString())
        val binding =
            ItemAccountListAccessBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: MypageAccountListAdapter.Holder, position: Int) {
//       클릭이벤트 추가부분
        holder.itemView.setOnClickListener {
            itemClick?.onClick(it, position)
        }

        holder.accountId.text = accountList[position].accountId.toString()
        holder.accountName.text = accountList[position].accountName
        holder.accountNumber.text = accountList[position].accountNumber
        holder.balance.text = accountList[position].balance.toString()+" 원"
    }

    override fun getItemCount(): Int {
        return accountList.size
    }


}


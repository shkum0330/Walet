package com.allforyou.app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.allforyou.app.databinding.ItemAccountListAccessBinding

class AccessAccountAdapter(val accountList : List<ChargingAccount>) : RecyclerView.Adapter<AccessAccountAdapter.Holder>() {

    interface ItemClick {  //클릭이벤트추가부분
        fun onClick(view : View, position : Int)
    }

    var itemClick : ItemClick? = null  //클릭이벤트추가부분

    inner class Holder(val binding: ItemAccountListAccessBinding) : RecyclerView.ViewHolder(binding.root) {


        val accountId=binding.accountId
        val accountName=binding.accountName
        val accountNumber=binding.accountNumber
        val balance=binding.balance

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccessAccountAdapter.Holder {
        val binding = ItemAccountListAccessBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: AccessAccountAdapter.Holder, position: Int) {

        holder.itemView.setOnClickListener {  //클릭이벤트추가부분
            itemClick?.onClick(it, position)
        }
        holder.accountId.text = accountList[position].accountId.toString()
        holder.accountName.text= accountList[position].accountName
        holder.accountNumber.text= accountList[position].accountNumber
        holder.balance.text= accountList[position].balance.toString()


    }

    override fun getItemCount(): Int {
        return accountList.size
    }
}



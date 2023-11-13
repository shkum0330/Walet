package com.allforyou.app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.allforyou.app.databinding.MypageAccountItemBinding
import com.allforyou.app.retrofit.ListAllAccountResponse

class MypageAccountListAdapter :RecyclerView.Adapter<MypageAccountListAdapter.Holder>(){

    inner class Holder(val binding: MypageAccountItemBinding):RecyclerView.ViewHolder(binding.root){
        val accountName=binding.accountName
        val accountNumber=binding.accountNumber
        val accountBalance=binding.accountBalance
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MypageAccountListAdapter.Holder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: MypageAccountListAdapter.Holder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return
    }

}
//
//(private val mData:  MutableList<ListAllAccountResponse>) :
//
//    private List<ListAllAccountResponse>
//    RecyclerView.Adapter<MypageAccountListAdapter.ViewHolder>() {
//
//    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val accountNameTextView: TextView = itemView.findViewById(R.id.account_name)
//        val accountNumberTextView: TextView = itemView.findViewById(R.id.account_number)
//        val accountBalanceTextView: TextView = itemView.findViewById(R.id.account_balance)
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val context = parent.context
//        val inflater = LayoutInflater.from(context)
//        val view: View = inflater.inflate(R.layout.mypage_account_item, parent, false)
//        return ViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val accountItem = mData[position] // ListAllAccountResponse 객체
//
//        // ListAllAccountResponse 객체에서 data 속성에 접근하여 Account1 데이터를 가져옴
//        val accountData = accountItem.data[position]
//
//        holder.accountNameTextView.text = accountData.accountName
//        holder.accountNumberTextView.text = accountData.accountNumber
//        holder.accountBalanceTextView.text = "${accountData.balance} 원"
//    }
//
//
//
//    override fun getItemCount(): Int {
//        return mData.size
//    }
//
//    fun updateData(newData: List<ListAllAccountResponse.Account1>) {
//        mData.clear()
//        mData.addAll(newData)
//        notifyDataSetChanged()
//    }
//
//
//}

package com.allforyou.app

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import com.google.android.material.internal.ContextUtils.getActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BusinessAccountAdapter(private val items : ArrayList<BankAccount>, private val context : Context) :
    ArrayAdapter<BankAccount>(context, R.layout.item_business_bank_account, items) {
    private class ViewHolder {
        lateinit var accountNumber: TextView
        lateinit var accountName: TextView
        lateinit var accountBalance: TextView
        lateinit var sendButton : Button
        lateinit var transactionLogButton : Button
        lateinit var petPayButton : Button
//        lateinit var transaction1 : Button
//        lateinit var transaction2 : Button
//        lateinit var transaction3 : Button
//        lateinit var transaction4 : Button
//        lateinit var transaction5 : Button
    }

    override fun getCount(): Int {
        return items.size
    }
    override fun getItem(position: Int): BankAccount {
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val item = getItem(position)
        var viewHolder: ViewHolder? // view lookup cache stored in tag
        val result: View

        if (convertView == null) {
            viewHolder = ViewHolder()
            val inflater = LayoutInflater.from(context)
            val itemView = inflater.inflate(R.layout.item_business_bank_account, parent, false)
            viewHolder.accountNumber = itemView.findViewById(R.id.account_number)
            viewHolder.accountName = itemView.findViewById(R.id.account_name)
            viewHolder.accountBalance = itemView.findViewById(R.id.account_balance)
            viewHolder.sendButton = itemView.findViewById(R.id.send)
            viewHolder.transactionLogButton = itemView.findViewById(R.id.account_log_store)
            viewHolder.petPayButton = itemView.findViewById(R.id.petpay)
            result = itemView
            itemView.tag = viewHolder

        }else{
            viewHolder = convertView.tag as ViewHolder
            result = convertView
        }
        Log.d("my_tag",item.accountNumber)
        viewHolder.accountNumber.text = item.accountNumber
        viewHolder.accountName.text = item.accountName
        viewHolder.accountBalance.text = item.balance.toString()
        viewHolder.sendButton!!.setOnClickListener{
            RemittanceRequestManager.getInstance().myAccountId = item.accountId
            val intent = Intent(context, TransferTargetActivity::class.java)
            context.startActivity(intent)
        }
        viewHolder.petPayButton!!.setOnClickListener{
            val intent = Intent(context, PayPaymentActivity::class.java)
            context.startActivity(intent)
        }
        viewHolder.transactionLogButton!!.setOnClickListener {
            getTransactionLog(item.accountId)
        }
        return result
    }
    fun getTransactionLog(accountId : Long){
        val retrofitAPI = RetrofitClient.getClient()
        retrofitAPI.loadTransactionLog(AccessTokenManager.getBearer(), accountId).enqueue(object :
            Callback<TransactionLogResponse> {
            override fun onResponse(call: Call<TransactionLogResponse>, response: Response<TransactionLogResponse>) {
                Log.d("my_tag","요청 사항 : "+AccessTokenManager.getBearer())
                if (response.isSuccessful) {
                    val transactionLog : List<TransactionLogResponse.TransactionLog>? = response.body()!!.data
                    Log.d("my_tag",transactionLog.toString())
                    if (transactionLog != null) {
                        Log.d("my_tag","거래 내역 로딩 성공")

                        val intent = Intent(context, TransactionActivity::class.java)
                        intent.putExtra("transactionLog", ArrayList(transactionLog));
                        Log.d("버튼 클릭!!!!!!!!!!!","사업자계좌의 거래내역을 클릭했습니다")
                        context.startActivity(intent)
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
}

class GeneralAccountAdapter(private val items : ArrayList<BankAccount>, private val context : Context) :
    ArrayAdapter<BankAccount>(context, R.layout.item_general_bank_account, items) {
    private class ViewHolder {
        lateinit var accountNumber: TextView
        lateinit var accountName: TextView
        lateinit var accountBalance: TextView
        lateinit var sendButton : Button
        lateinit var transactionLogButton : Button
    }

    override fun getCount(): Int {
        return items.size
    }
    override fun getItem(position: Int): BankAccount {
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val item = getItem(position)
        var viewHolder: ViewHolder? // view lookup cache stored in tag
        val result: View

        if (convertView == null) {
            viewHolder = ViewHolder()
            val inflater = LayoutInflater.from(context)
            val itemView = inflater.inflate(R.layout.item_general_bank_account, parent, false)
            viewHolder.accountNumber = itemView.findViewById(R.id.account_number)
            viewHolder.accountName = itemView.findViewById(R.id.account_name)
            viewHolder.accountBalance = itemView.findViewById(R.id.account_balance)
            viewHolder.sendButton = itemView.findViewById(R.id.send)
            viewHolder.transactionLogButton = itemView.findViewById(R.id.account_log_store)
            result = itemView
            itemView.tag = viewHolder

        }else{
            viewHolder = convertView.tag as ViewHolder
            result = convertView
        }
        Log.d("my_tag",item.accountNumber)
        viewHolder.accountNumber.text = item.accountNumber
        viewHolder.accountName.text = item.accountName
        viewHolder.accountBalance.text = item.balance.toString()
        viewHolder.sendButton!!.setOnClickListener{
            RemittanceRequestManager.getInstance().myAccountId = item.accountId
            val intent = Intent(context, TransferTargetActivity::class.java)
            context.startActivity(intent)
        }
        viewHolder.transactionLogButton!!.setOnClickListener {
            getTransactionLog(item.accountId)
        }
        return result
    }
    fun getTransactionLog(accountId : Long){
        val retrofitAPI = RetrofitClient.getClient()
        retrofitAPI.loadTransactionLog(AccessTokenManager.getBearer(), accountId).enqueue(object : Callback<TransactionLogResponse> {
            override fun onResponse(call: Call<TransactionLogResponse>, response: Response<TransactionLogResponse>) {
                Log.d("my_tag","요청 사항 : "+AccessTokenManager.getBearer())
                if (response.isSuccessful) {
                    val transactionLog : List<TransactionLogResponse.TransactionLog>? = response.body()!!.data
//                    NoticeManager.initData(noticeResponse!!)
                    Log.d("my_tag",transactionLog.toString())
                    if (transactionLog != null) {
                        Log.d("my_tag","거래 내역 로딩 성공")

                        val intent = Intent(context, TransactionActivity::class.java)
                        intent.putExtra("transactionLog", ArrayList(transactionLog));
                        context.startActivity(intent)
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
}

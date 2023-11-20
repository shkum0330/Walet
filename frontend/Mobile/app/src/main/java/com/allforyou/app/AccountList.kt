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
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import com.google.android.material.internal.ContextUtils.getActivity
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
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
            getTransactionLog(item.accountId, item)
        }
        return result
    }
    fun getTransactionLog(accountId : Long, item : BankAccount){
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
                        Log.d("transactionLog",transactionLog.toString())

                        val intent = Intent(context, TransactionActivity::class.java)
                        intent.putExtra("balance", item.balance);
                        intent.putExtra("accountName", item.accountName);
                        intent.putExtra("accountNumber", item.accountNumber);
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
            getTransactionLog(item.accountId, item)
        }
        return result
    }
    fun getTransactionLog(accountId : Long, item : BankAccount){
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
                        intent.putExtra("balance", item.balance);
                        intent.putExtra("accountName", item.accountName);
                        intent.putExtra("accountNumber", item.accountNumber);
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

class PetAccountAdapter(private val items : ArrayList<AnimalAccountDetailResponse.AnimalAccountDetail>, private val context : Context) :
    ArrayAdapter<AnimalAccountDetailResponse.AnimalAccountDetail>(context, R.layout.item_pet_bank_account, items) {
    private class ViewHolder {
        lateinit var accountNumber: TextView
        lateinit var accountName: TextView
        lateinit var accountBalance: TextView
        lateinit var rechargeButton : Button
        lateinit var transactionLogButton : Button
        lateinit var petName: TextView
        lateinit var petAge: TextView
        lateinit var petBreed: TextView
        lateinit var petImage: CircleImageView
        lateinit var petImage2: CircleImageView
    }

    override fun getCount(): Int {
        return items.size
    }
    override fun getItem(position: Int): AnimalAccountDetailResponse.AnimalAccountDetail {
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val item = getItem(position)
        val viewHolder: ViewHolder? // view lookup cache stored in tag
        val result: View


        if (convertView == null) {
            viewHolder = ViewHolder()
            val inflater = LayoutInflater.from(context)
            val itemView = inflater.inflate(R.layout.item_pet_bank_account, parent, false)
            viewHolder.accountNumber = itemView.findViewById(R.id.account_number)
            viewHolder.accountName = itemView.findViewById(R.id.account_name)
            viewHolder.accountBalance = itemView.findViewById(R.id.balance)
            viewHolder.rechargeButton = itemView.findViewById(R.id.recharge)
            viewHolder.transactionLogButton = itemView.findViewById(R.id.account_log_store)

            viewHolder.petAge = itemView.findViewById<TextView?>(R.id.pet_age)
            viewHolder.petName = itemView.findViewById(R.id.pet_name)
            viewHolder.petBreed = itemView.findViewById(R.id.pet_breed)
            viewHolder.petImage = itemView.findViewById(R.id.pet_img)
            viewHolder.petImage2 = itemView.findViewById(R.id.pet_profile2)
            result = itemView
            itemView.tag = viewHolder
        }else{
            viewHolder = convertView.tag as ViewHolder
            result = convertView
        }
//        Log.d("my_tag",item.toString())

        var temp:String=item.accountNumber
        Log.d("accccccccccount Number ",temp)
        temp=temp.substring(0,3)+"-"+temp.substring(3,7)+"-"+temp.substring(7,11)+"-"+temp.substring(11,13)
        viewHolder.accountNumber.text = temp

        viewHolder.accountName.text = item.accountName
        viewHolder.accountBalance.text = item.balance.toString()
        viewHolder.petAge.text = item.petAge+"살"
        viewHolder.petName.text = item.petName
        viewHolder.petBreed.text = item.petBreed
        viewHolder.rechargeButton!!.setOnClickListener{
            RemittanceRequestManager.getInstance().myAccountId = item.linkedAccountId
            RemittanceRequestManager.getInstance().receiverAccountId = item.id
            val intent = Intent(context, ChargePaymentActivity::class.java)
            context.startActivity(intent)
        }
        viewHolder.transactionLogButton!!.setOnClickListener {
            getTransactionLog(item.id, item)
        }
        val imageUrl = item.petPhoto

        Picasso.get().load(imageUrl).into(viewHolder.petImage)
        Picasso.get().load(imageUrl).into(viewHolder.petImage2)
        return result
    }
    fun getTransactionLog(accountId : Long, item : AnimalAccountDetailResponse.AnimalAccountDetail){
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
                        intent.putExtra("balance", item.balance);
                        intent.putExtra("accountName", item.accountName);
                        intent.putExtra("accountNumber", item.accountNumber);
                        intent.putExtra("transactionLog", ArrayList(transactionLog));
                        context.startActivity(intent)
                    } else {
                        Log.d("my_tag","거래 내역 NULL 반환됨")
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

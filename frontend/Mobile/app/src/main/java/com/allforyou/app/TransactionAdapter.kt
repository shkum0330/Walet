package com.allforyou.app

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class TransactionAdapter(private val items : ArrayList<TransactionLogResponse.TransactionLog>, private val context : Context) :
    ArrayAdapter<TransactionLogResponse.TransactionLog>(context, R.layout.item_transaction, items) {
    private class ViewHolder {
        lateinit var transactionTime: TextView
        lateinit var transactionTarget: TextView
        lateinit var transactionAmount: TextView
        lateinit var transactionLeftOver: TextView
        lateinit var transactionType: TextView
    }

    override fun getCount(): Int {
        return items.size
    }
    override fun getItem(position: Int): TransactionLogResponse.TransactionLog {
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
            val itemView = inflater.inflate(R.layout.item_transaction, parent, false)
            viewHolder.transactionTime = itemView.findViewById(R.id.transaction_time)
            viewHolder.transactionTarget = itemView.findViewById(R.id.transaction_target)
            viewHolder.transactionType = itemView.findViewById(R.id.transaction_type)
            viewHolder.transactionAmount = itemView.findViewById(R.id.transaction_amount)
            viewHolder.transactionLeftOver = itemView.findViewById(R.id.transaction_leftover)


            result = itemView
            itemView.tag = viewHolder
        }else{
            viewHolder = convertView.tag as ViewHolder
            result = convertView
        }
//        Log.d("my_tag",item.toString())
        viewHolder.transactionTime.text = item.transactionDate
        viewHolder.transactionTarget.text = item.recipient
        viewHolder.transactionType.text = item.transactionType
        if(item.transactionType == "출금"){
            viewHolder.transactionType.text = "출금"
            viewHolder.transactionType.setTextColor(context.resources.getColor(R.color.red))
            viewHolder.transactionAmount.setTextColor(context.resources.getColor(R.color.red))
            //@color/red
        }else{
            viewHolder.transactionType.text = "입금"
            viewHolder.transactionType.setTextColor(context.resources.getColor(R.color.blue))
            viewHolder.transactionAmount.setTextColor(context.resources.getColor(R.color.blue))
        }
        viewHolder.transactionAmount.text = item.paymentAccount.toString()+" 원"
        viewHolder.transactionLeftOver.text = item.balance.toString()+" 원"

        return result
    }
}
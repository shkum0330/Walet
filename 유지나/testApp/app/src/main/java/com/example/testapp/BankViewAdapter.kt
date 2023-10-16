package com.example.testapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class BankViewAdapter(val context:Context, val img_list:Array<Int>, val text_list:Array<String>):BaseAdapter(){
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val view:View =LayoutInflater.from(context).inflate(R.layout.item_bank, null)


        view.findViewById<ImageView>(R.id.bank_logo).setImageResource(img_list[p0])
        view.findViewById<TextView>(R.id.bank_name).text=text_list[p0]

        return view
    }
    override fun getCount(): Int {
       return img_list.size
    }

    override fun getItem(position: Int): Any {
       return 0
    }

    override fun getItemId(position: Int): Long {
     return 0
    }



}
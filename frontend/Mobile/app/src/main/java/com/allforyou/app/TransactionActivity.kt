package com.allforyou.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ListView
import android.widget.TextView

class TransactionActivity : AppCompatActivity() {
    private lateinit var chargePaymentButton : Button
    private lateinit var transactionButton : Button

    private lateinit var transactionLog : ArrayList<TransactionLogResponse.TransactionLog>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction)

        val extras = intent.extras
//        bankAccount = (intent.getSerializableExtra("bankAccount") as? BankAccount)!!
        val accountName = intent.getStringExtra("accountName") as String
        val accountNumber = intent.getStringExtra("accountNumber") as String
        val balance = intent.getLongExtra("balance",0)
        transactionLog = (intent.getSerializableExtra("transactionLog") as? ArrayList<TransactionLogResponse.TransactionLog>)!!

        findViewById<TextView>(R.id.account_number).text = accountNumber
        findViewById<TextView>(R.id.account_name).text = accountName
        findViewById<TextView>(R.id.balance).text = balance.toString()+" 원"

        Log.d("my_tag",transactionLog.toString())
        val listView = findViewById<ListView>(R.id.transaction_list)
        val adapter = TransactionAdapter(ArrayList(transactionLog),this)
        listView.adapter = adapter

        chargePaymentButton = findViewById(R.id.charge_payment)
        chargePaymentButton.setOnClickListener {
            val intent = Intent(this, ChargePaymentActivity::class.java)
            startActivity(intent)
        }

        transactionButton = findViewById(R.id.transaction)
        transactionButton.setOnClickListener {
            val intent = Intent(this, TransactionActivity::class.java)
            startActivity(intent)
        }
    }
}
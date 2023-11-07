package com.allforyou.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class TransactionActivity : AppCompatActivity() {
    private lateinit var chargePaymentButton : Button
    private lateinit var transactionButton : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction)

    }
}
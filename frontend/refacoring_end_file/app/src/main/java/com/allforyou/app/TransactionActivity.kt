package com.allforyou.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class TransactionActivity : AppCompatActivity() {
    private lateinit var chargePaymentButton : Button
    private lateinit var transactionButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction)
        chargePaymentButton = findViewById(R.id.charge_payment)
        chargePaymentButton.setOnClickListener({
            val intent = Intent(this, ChargePaymentActivity::class.java)
            startActivity(intent)
        })

        transactionButton = findViewById(R.id.transaction)
        transactionButton.setOnClickListener({
            val intent = Intent(this, TransactionActivity::class.java)
            startActivity(intent)
        })
    }
}
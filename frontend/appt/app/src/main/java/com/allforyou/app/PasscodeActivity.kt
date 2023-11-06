package com.allforyou.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class PasscodeActivity : AppCompatActivity() {
    private lateinit var message : TextView
    private var enroll : Boolean = true
    private var confirm : Boolean = false
    private var passCode: String = ""
    private lateinit var passcodeDigits : Array<EditText>
    private lateinit var goBackButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_passcode)

        goBackButton = findViewById(R.id.goBack)
        goBackButton.setOnClickListener {
            finish() // Finish the current activity and go back to the previous one or exit the app.
        }

        message = findViewById(R.id.message)

        val passcodeDigit1 = findViewById<EditText>(R.id.passcode_digit1)
        val passcodeDigit2 = findViewById<EditText>(R.id.passcode_digit2)
        val passcodeDigit3 = findViewById<EditText>(R.id.passcode_digit3)
        val passcodeDigit4 = findViewById<EditText>(R.id.passcode_digit4)
        val passcodeDigit5 = findViewById<EditText>(R.id.passcode_digit5)
        val passcodeDigit6 = findViewById<EditText>(R.id.passcode_digit6)

        passcodeDigits = arrayOf(passcodeDigit1, passcodeDigit2, passcodeDigit3, passcodeDigit4, passcodeDigit5, passcodeDigit6)

        passcodeDigit6.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE || (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN)) {
                // Check if all EditText fields are filled
                if (passcodeDigits.all { it.text.isNotEmpty() }) {
                    setToConfirmMode()
                }
                return@setOnEditorActionListener true
            }
            false
        }
        passcodeDigits[0].requestFocus()

        for (i in passcodeDigits.indices) {
            val digit = passcodeDigits[i]
            digit.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable?) {
                    if (s?.length == 1) {
                        if (i < passcodeDigits.size - 1) {
                            passcodeDigits[i + 1].requestFocus()
                        }else{
                            if (passcodeDigits.all { it.text.isNotEmpty() }) {
                                setToConfirmMode()
                            }
                        }
                    }
                }
            })
            digit.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
                if (keyCode == KeyEvent.KEYCODE_DEL && event.action == KeyEvent.ACTION_DOWN) {
                    if (i > 0 && digit.text.isEmpty()) {
                        // Backspace pressed on a blank EditText; focus on the previous one
                        passcodeDigits[i - 1].requestFocus()
                        passcodeDigits[i - 1].setText("")
                        return@OnKeyListener true
                    }
                }
                false
            })
        }
    }
    fun setToConfirmMode(){
        var passCodeConfirm = ""

        for (digit in passcodeDigits) {
            passCodeConfirm += digit.text.toString()
            digit.setText("")
        }
        if(!confirm){
            if(enroll){
                passCode = passCodeConfirm
                confirm = true
                message.setText("PIN 입력 확인")
            }else{
                goNext()
            }
        }else{
            // 등록 완료
            // 일치하지 않느냐를 본다
            if(passCodeConfirm == passCode){
                goNext()
            }else{
                // 일치하지 않습니다
                
            }
        }
//        message
        passcodeDigits[0].requestFocus()
        Log.d("my_tag",passCode)
    }
    fun goNext(){
        RegisterRequestManager.getInstance().pinNumber = passCode
        RegisterRequestManager.performRegister(this@PasscodeActivity);

//        val intent = Intent(this, PhoneAuthenticationActivity::class.java)
//        startActivity(intent)
    }
}
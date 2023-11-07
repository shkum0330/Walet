package com.allforyou.app

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.telephony.PhoneNumberFormattingTextWatcher
import android.telephony.PhoneNumberUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Locale


class PhoneAuthenticationActivity : AppCompatActivity() {
    private lateinit var goBackButton: Button
    private lateinit var phoneNumberForm: EditText
    private lateinit var authenticationNumber: EditText
    private lateinit var sendAuthenticationNumberButton: Button
    private lateinit var authenticationTimer: TextView
    private lateinit var goNextButton: Button
    private var rawTimer : Long = 300000; // 5분
    private var timerRunning : Boolean = false
    private var timerLeft : Long = 0


    private lateinit var registerRequest: RegisterRequest
    private lateinit var countDownTimer :CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone_authentication)

//        val jsonString = intent.getStringExtra("registerRequest")
//        if (jsonString != null) {
//            val gson = Gson()
//            registerRequest = gson.fromJson(jsonString, RegisterRequest::class.java)
//
//            Log.d("myTag", "I am passed on: " + registerRequest.toString());
//        }

        goBackButton = findViewById(R.id.goBack)

        phoneNumberForm = findViewById(R.id.phoneNumber)
        phoneNumberForm.addTextChangedListener(PhoneNumberFormattingTextWatcher("KR"))

//        PhoneNumberUtils.formatNumber(cellphone, "KR")
        sendAuthenticationNumberButton = findViewById(R.id.sendAuthenticationNumberButton)
        sendAuthenticationNumberButton.setOnClickListener{
            if(!timerRunning){
                startTimer()
            }else{
                resetTimer()
                // 다시 전송하기
            }
        }

        authenticationNumber = findViewById(R.id.authenticationNumber)

        authenticationTimer = findViewById(R.id.authenticationTimer)

        goNextButton = findViewById(R.id.goNext)

        goBackButton.setOnClickListener {
            val intent = Intent(this, LoginFaceActivity::class.java)
            startActivity(intent)
        }

        goNextButton.isEnabled = false
        goNextButton.setOnClickListener {
            checkAuthenticationNumber()
        }
        updateCountDownText()
    }
    fun startTimer() {
        countDownTimer = object: CountDownTimer(rawTimer, 1000){
            override fun onTick(millisUntilFinished: Long) {
                timerLeft = millisUntilFinished
                updateCountDownText();
            }

            override fun onFinish() {
                timerRunning = false;
                timerLeft = 0;
            }
        }.start()
        sendAuthenticationNumberButton.isEnabled = false
        timerRunning = true
        // SEND
        Log.d("myTag", "Phone Number: " + phoneNumberForm.text.toString());
        sendAuthenticationNumber()
    }
    fun resetTimer() {
        timerLeft = rawTimer;
        updateCountDownText();
        // SEND
        sendAuthenticationNumber()
    }
    fun updateCountDownText(){
        var time = ((timerLeft.toInt() + 999) / 1000 )
        var minutes : Int = time / 60
        var seconds : Int = time % 60

        val timeLeftFormatted : String =  String.format(Locale.getDefault(),"%02d:%02d", minutes, seconds)
        authenticationTimer.setText(timeLeftFormatted)
        if(minutes >= 4 && !sendAuthenticationNumberButton.isEnabled)
            sendAuthenticationNumberButton.isEnabled = true
        if(time == 0){
            goNextButton.isEnabled = false
        }
    }
    fun sendAuthenticationNumber(){
        var retrofitAPI = RetrofitClient.getClient().create(ApiService::class.java)

        retrofitAPI.sendCode(PhoneAuthenticationRequest(phoneNumberForm.text.toString(),0)).enqueue(object : Callback<Unit> {
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if (response.isSuccessful) {
                    // 인정번호가 전송되었습니다.
                    RegisterRequestManager.getInstance().phoneNumber = phoneNumberForm.text.toString()
                    sendCodeAlertDialog()
                    goNextButton.isEnabled = true
                } else {
                    // Handle unsuccessful response
                    sendCodeFailedAlertDialog()
                }
            }
            override fun onFailure(call: Call<Unit>, t: Throwable) {
                sendCodeFailedAlertDialog()
            }

        })
    }

    fun checkAuthenticationNumber(){
        var retrofitAPI = RetrofitClient.getClient().create(ApiService::class.java)

        retrofitAPI.checkCode(PhoneAuthenticationRequest(phoneNumberForm.text.toString(),authenticationNumber.text.toString().toLong())).enqueue(object : Callback<Unit> {
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if (response.isSuccessful) {
                    // 다음으로 넘어가기
                    val intent = Intent(this@PhoneAuthenticationActivity, PasscodeActivity::class.java)
                    startActivity(intent)
                } else {
                    // Handle unsuccessful response
                    checkCodeMismatchDialog ()
                }
            }
            override fun onFailure(call: Call<Unit>, t: Throwable) {
//                sendCodeFailedAlertDialog()
                showAlertDialog("네트워크 오류", "네트워크 오류가 있어 인증번호 확인에 실패했습니다.")
            }

        })
    }
    fun checkCodeMismatchDialog(){
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("인증번호 불일치")
        alertDialogBuilder.setMessage("인증번호가 맞지 않습니다.")
        alertDialogBuilder.setPositiveButton("확인") { dialog, which ->
            // Implement your login logic here
            // For example, you can start a new activity or perform the login action.
        }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
    fun sendCodeAlertDialog (){
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("인증번호 전송")
        alertDialogBuilder.setMessage("인증번호가 전송되었습니다.")
        alertDialogBuilder.setPositiveButton("확인") { dialog, which ->
            // Implement your login logic here
            // For example, you can start a new activity or perform the login action.
        }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
    fun sendCodeFailedAlertDialog (){
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("인증번호 전송 실패")
        alertDialogBuilder.setMessage("인증번호가 전송이 실패했습니다.")
        alertDialogBuilder.setPositiveButton("확인") { dialog, which ->
            // Implement your login logic here
            // For example, you can start a new activity or perform the login action.
        }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
    private fun showAlertDialog(title : String, message : String) {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle(title)
        alertDialogBuilder.setMessage(message)
        alertDialogBuilder.setPositiveButton("확인") { dialog, which ->
        }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
}
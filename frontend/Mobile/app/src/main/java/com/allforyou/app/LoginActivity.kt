package com.allforyou.app

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity() {

    private lateinit var goBackButton: Button
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var loginButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        email = findViewById(R.id.email)
        password = findViewById(R.id.password)

//        goBackButton = findViewById(R.id.goBack)
        loginButton = findViewById(R.id.loginButton)

//        goBackButton.setOnClickListener {
//            val intent = Intent(this, LoginFaceActivity::class.java)
//            startActivity(intent)
//        }
        loginButton.setOnClickListener {
            performLogin(email.text.toString(), password.text.toString(), "000000");
        }

    }
    // Your login logic
    fun performLogin(email : String, password : String, pinNumber : String) {

        var retrofitAPI = RetrofitClient.getClient()
        retrofitAPI.login(LoginRequest(email,password)).enqueue(object : Callback<AccessTokenResponse> {
            override fun onResponse(call: Call<AccessTokenResponse>, response: Response<AccessTokenResponse>) {
                if (response.isSuccessful) {
                    val accessToken : AccessTokenResponse.AccessToken = response.body()?.data!!
                    Log.d("my_tag", "ResponseBody $response");
                    if (accessToken != null) {
                        AccessTokenManager.init(accessToken);
                        retrofitAPI.login(LoginRequest(email,password))
                        Log.d("my_tag",accessToken.toString())
                        val intent = Intent(this@LoginActivity, PasscodeActivity::class.java)
                        intent.putExtra("enroll",false);
                        intent.putExtra("destination", "com.allforyou.app.MainActivity");
                        startActivity(intent)
                        finish()
                        // Handle a successful login here
                    } else {
                        // Handle null response body
                        showNullLoginAlertDialog()
                    }
                } else {
                    // Handle unsuccessful response
//                    onFailure(call, retrofitAPI.)
                    showLoginAlertDialog(response.toString())
                }
            }

            override fun onFailure(call: Call<AccessTokenResponse>, t: Throwable) {
                showNetworkLoginAlertDialog()
            }

        })


    }

    private fun showLoginAlertDialog(message : String) {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("로그인 오류")
        alertDialogBuilder.setMessage(message)
//        alertDialogBuilder.setMessage("이메일과 비밀번호를 확인하세요.")
        alertDialogBuilder.setPositiveButton("확인") { dialog, which ->
            // Implement your login logic here
            // For example, you can start a new activity or perform the login action.
        }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
    private fun showNullLoginAlertDialog() {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("로그인 오류")
        alertDialogBuilder.setMessage("NULL RESPONSE BODY")
        alertDialogBuilder.setPositiveButton("확인") { dialog, which ->
            // Implement your login logic here
            // For example, you can start a new activity or perform the login action.
        }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
    private fun showNetworkLoginAlertDialog() {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("로그인 오류")
        alertDialogBuilder.setMessage("네트워크 오류 발생")
        alertDialogBuilder.setPositiveButton("확인") { dialog, which ->
            // Implement your login logic here
            // For example, you can start a new activity or perform the login action.
        }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
}
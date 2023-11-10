package com.allforyou.app

import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.TypeAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException


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

        goBackButton = findViewById(R.id.goBack)
        loginButton = findViewById(R.id.loginButton)

        goBackButton.setOnClickListener {
            val intent = Intent(this, LoginFaceActivity::class.java)
            startActivity(intent)
        }
        loginButton.setOnClickListener {
            performLogin(email.text.toString(), password.text.toString());
        }

    }
    // Your login logic
    fun performLogin(email : String, password : String) {

        var retrofitAPI = RetrofitClient.getClient().create(ApiService::class.java)

        retrofitAPI.login(LoginRequest(email,password)).enqueue(object : Callback<AccessToken> {
            override fun onResponse(call: Call<AccessToken>, response: Response<AccessToken>) {
                if (response.isSuccessful) {
                    val accessToken : AccessToken? = response.body()
                    if (accessToken != null) {
//                        val accessToken = loginResponse.accessToken
//                        val refreshToken = loginResponse.refreshToken
//                        Log.d("my_tag",loginResponse.toString())
//                        Log.d("my_tag",loginResponse.accessToken)
//                        Log.d("my_tag",loginResponse.refreshToken)
//
                        AccessTokenManager.init(accessToken);

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
                    showLoginAlertDialog()
                }
            }

            override fun onFailure(call: Call<AccessToken>, t: Throwable) {
                showNetworkLoginAlertDialog()
            }

        })


    }

    private fun showLoginAlertDialog() {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("로그인 오류")
        alertDialogBuilder.setMessage("이메일과 비밀번호를 확인하세요.")
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
package com.allforyou.app

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String,
    var phoneNumber: String,
    val birth: String,
    var pinNumber : String
)
object RegisterRequestManager {
    private var data : RegisterRequest? = null
    fun initData(request : RegisterRequest){
        data = request
    }
    fun getInstance(): RegisterRequest {
        if (data == null) {
            data = RegisterRequest("Initial Value","","","","","")
        }
        return data!!
    }
    fun performRegister(context: Context){

        var retrofitAPI = RetrofitClient.getClient()
        Log.d("my_tag","request : "+data.toString())
        retrofitAPI.register(data!!).enqueue(object : Callback<RegisterRequest> {
            override fun onResponse(call: Call<RegisterRequest>, response: Response<RegisterRequest>) {
                if (response.isSuccessful) {
                    val registerResponse = response.body()
                    if (registerResponse != null) {
                        val intent = Intent(context, SignUpFinishActivity::class.java)
                        context.startActivity(intent)
                    } else {
                        // Handle null response body
                        showAlertDialog(context, "회원가입 오류", "NULL이 반환되었습니다.")
                    }
                } else {
                    showAlertDialog(context, "회원가입 실패", "입력사항을 다시 확인해주세요.")
                }
            }
            override fun onFailure(call: Call<RegisterRequest>, t: Throwable) {
                showAlertDialog(context, "네트워크 오류", "서버에 연결을 실패했습니다.")
            }
        })
    }
    private fun showAlertDialog(context : Context, title : String, message : String) {
        val alertDialogBuilder = AlertDialog.Builder(context)
        alertDialogBuilder.setTitle(title)
        alertDialogBuilder.setMessage(message)
        alertDialogBuilder.setPositiveButton("확인") { dialog, which ->
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
}
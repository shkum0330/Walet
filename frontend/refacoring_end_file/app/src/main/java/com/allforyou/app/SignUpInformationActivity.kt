package com.allforyou.app

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.icu.util.GregorianCalendar
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.DatePicker
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Calendar
import java.util.Date
import java.util.regex.Pattern


class SignUpInformationActivity : AppCompatActivity() {

    private lateinit var goBackButton: Button
    private lateinit var emailForm: EditText
    private lateinit var passwordForm: EditText
    private lateinit var passwordConfirmForm: EditText
    private lateinit var nameForm: EditText
//    private lateinit var phoneNumberForm: EditText
    private lateinit var dateofBirthForm: EditText
    private lateinit var datePickButton: Button
    private lateinit var checkbox1: CheckBox
    private lateinit var checkbox2: CheckBox
    private lateinit var goNextButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_information)

        goBackButton = findViewById(R.id.goBack)
        emailForm = findViewById(R.id.email)
        passwordForm = findViewById(R.id.password)
        passwordConfirmForm = findViewById(R.id.passwordConfirm)
        nameForm = findViewById(R.id.name)
//        phoneNumberForm = findViewById(R.id.phoneNumber)
        dateofBirthForm = findViewById(R.id.dateofBirth)
        datePickButton = findViewById(R.id.datePickButton)
        checkbox1 = findViewById(R.id.signupInformationCheckbox1)
        checkbox2 = findViewById(R.id.signupInformationCheckbox2)
        goNextButton = findViewById(R.id.nextButton)


        datePickButton.setOnClickListener {
            openDatePickDialog()
        }

        goNextButton.isEnabled = true

        goBackButton.setOnClickListener {
            // 중단하시겠습니까? 라고 뜨면 좋을 것 같음
            val intent = Intent(this, LoginFaceActivity::class.java)
            startActivity(intent)
        }
        goNextButton.setOnClickListener {
            if(hasValidInputs()){
                val intent = Intent(this@SignUpInformationActivity, PhoneAuthenticationActivity::class.java)
                // 다음 activity로 넘어감
                RegisterRequestManager.initData(RegisterRequest(
                    nameForm.text.toString(),
                    emailForm.text.toString(),
                    passwordForm.text.toString(),
                    "",
                    dateofBirthForm.text.toString(),"",
                ))
//                val registerRequest =
//                Log.d("myTag", registerRequest.toString());
//                val gson = Gson()
//                val registerRequestAsAString = gson.toJson(registerRequest)
//                intent.putExtra("registerRequest", registerRequestAsAString);
                startActivity(intent)
                finish()
            }
            
            // 여기에 새 회원 post 요청

//            val intent = Intent(this, SignUpInformationActivity::class.java)
//            startActivity(intent)
        }
    }
    fun isValidName(name: String?): Boolean {
        val trimmedName = name?.trim().toString()
        val exp = Regex("^[가-힣ㄱ-ㅎa-zA-Z0-9._ -]{2,}\$")
        return !trimmedName.isNullOrEmpty() && exp.matches(trimmedName)
    }
    fun isEmailValid(email: String): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z]+\\.[a-zA-Z]+"
        val pattern = Pattern.compile(emailPattern)
        val matcher = pattern.matcher(email)

        return matcher.matches()
    }

    fun openDatePickDialog(){
        val date : Date = Date()
        val calendar : GregorianCalendar = GregorianCalendar()
        calendar.time = date
        val datePicker : DatePickerDialog = DatePickerDialog(this,
            DatePickerDialog.OnDateSetListener(){ datePicker: DatePicker, year: Int, month: Int, day: Int ->
                dateofBirthForm.setText("${year}.${month+1}.${day}")
            },calendar[Calendar.YEAR],calendar[Calendar.MONTH],calendar[Calendar.DAY_OF_MONTH])
        datePicker.show()
    }

    private fun hasValidInputs() : Boolean{
        // 이메일 체크
        var reject = false
        if(!isEmailValid(emailForm.text.toString())){
            // 이메일을 확인하세요
            showSignUpAlertDialog("회원가입 오류", "올바른 이메일이 아닙니다.")
            return false
        }
        // 비밀번호는 최소 8자 이상

        val password : String = passwordForm.text.toString()
        if(password.length < 7){
            showSignUpAlertDialog("회원가입 오류", "비밀번호는 최소 8자 이상이어야 합니다.")
            return false
        }
        // 비밀번호 / 확인 체크
        if(password != passwordConfirmForm.text.toString()){
            showSignUpAlertDialog("회원가입 오류", "비밀번호 확인은 비밀번호와 같아야 합니다.")
            return false
        }

        val name : String = nameForm.text.toString()

//        if(!isValidName(name)){
//            showSignUpAlertDialog("회원가입 오류", "유효한 이름이 아닙니다.")
//            return false
//        }
        return true
    }



    private fun showSignUpAlertDialog(title : String, message : String) {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle(title)
        alertDialogBuilder.setMessage(message)
        alertDialogBuilder.setPositiveButton("확인") { dialog, which ->
        }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }


}
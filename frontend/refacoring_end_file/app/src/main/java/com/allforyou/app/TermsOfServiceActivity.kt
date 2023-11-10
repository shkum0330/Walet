package com.allforyou.app

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Phone
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TermsOfServiceActivity : AppCompatActivity(){
    private lateinit var goBackButton: Button
    private lateinit var termsOfServiceMandatory: LinearLayout
    private lateinit var termsOfServiceMandatoryContent: LinearLayout
    private lateinit var termsOfServiceOptional: LinearLayout
    private lateinit var termsOfServiceOptionalContent: LinearLayout
    private lateinit var goNextButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_terms_of_service)

        goBackButton = findViewById(R.id.goBack)
        goNextButton = findViewById(R.id.nextButton)

//        termsOfServiceMandatory = findViewById(R.id.termsOfServiceMandatory)
//        termsOfServiceOptional = findViewById(R.id.termsOfServiceOptional)
        goBackButton = findViewById(R.id.goBack)

//        termsOfServiceMandatory.setOnClickListener {
//            toggleSectionVisibility(termsOfServiceMandatoryContent)
//        }
//
//        termsOfServiceOptional.setOnClickListener {
//            toggleSectionVisibility(termsOfServiceOptionalContent)
//        }
        goBackButton.setOnClickListener {
            val intent = Intent(this, LoginFaceActivity::class.java)
            startActivity(intent)
        }
        goNextButton.setOnClickListener {
            val intent = Intent(this, PinLockActivity::class.java)
            startActivity(intent)
        }

    }
    private fun toggleSectionVisibility(sectionContent: LinearLayout) {
        if (sectionContent.visibility == LinearLayout.VISIBLE) {
            sectionContent.visibility = LinearLayout.GONE
        } else {
            sectionContent.visibility = LinearLayout.VISIBLE
        }
    }
}
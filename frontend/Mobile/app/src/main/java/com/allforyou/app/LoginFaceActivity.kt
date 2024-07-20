package com.allforyou.app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.messaging.FirebaseMessaging

class LoginFaceActivity : AppCompatActivity() {
    private lateinit var animationView: LottieAnimationView
    private lateinit var loginButton: Button
    private lateinit var signupButton: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_face)

        animationView = findViewById(R.id.lottieAnimationView)
        animationView.setAnimation("loginface.json")

        animationView.playAnimation()

//        val token = FirebaseMessaging.getInstance()

        loginButton = findViewById(R.id.loginButton)
        loginButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        signupButton = findViewById(R.id.signupButton) // Corrected the button reference
        signupButton.setOnClickListener { // Set a click listener for signupButton
            val intent = Intent(this, TermsOfServiceActivity::class.java)
            startActivity(intent)
        }
    }
}
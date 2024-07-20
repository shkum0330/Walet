package com.allforyou.app

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging


class SplashActivity : AppCompatActivity() {
    private val SPLASH_TIMEOUT = 1000 // 1 seconds
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            // This code will run after the splashTimeout
            val intent = Intent(this, LoginFaceActivity::class.java)
            startActivity(intent)
            finish() // Close the splash activity to prevent going back to it
        }, SPLASH_TIMEOUT.toLong())
//        FirebaseApp.initializeApp(this);

    }

}
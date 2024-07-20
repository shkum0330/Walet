package com.allforyou.app


import android.content.Intent
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import android.os.Bundle
import android.provider.Settings
import android.view.textservice.TextInfo
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import java.util.concurrent.Executor


class PinLockActivity : AppCompatActivity() {
    lateinit var btn_fp : Button
    lateinit var btn_pin : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pin_lock)

        btn_fp = findViewById(R.id.btn_fp)
        btn_pin = findViewById(R.id.btn_pin)

        checkBioMetricSupported()

        val executor : Executor = ContextCompat.getMainExecutor(this);
        val biometricPrompt : BiometricPrompt = BiometricPrompt(this,
            executor, object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    Toast.makeText(this@PinLockActivity, "Auth Error: "+errString, Toast.LENGTH_SHORT).show()
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    Toast.makeText(this@PinLockActivity, "Auth Succeeded: ", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@PinLockActivity, SignUpInformationActivity::class.java)
                    startActivity(intent)
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(this@PinLockActivity, "Auth Failed: ", Toast.LENGTH_SHORT).show()
                }
        })

        btn_fp.setOnClickListener{
            val promptInfo :BiometricPrompt.PromptInfo.Builder = dialogMetric()
            promptInfo.setNegativeButtonText("Cancel")
            biometricPrompt.authenticate(promptInfo.build())
        }
        btn_pin.setOnClickListener{
            val intent = Intent(this@PinLockActivity, SignUpInformationActivity::class.java)
            startActivity(intent)
            val promptInfo :BiometricPrompt.PromptInfo.Builder = dialogMetric()
            promptInfo.setDeviceCredentialAllowed(true);
            biometricPrompt.authenticate(promptInfo.build())
        }

    }
    fun dialogMetric() : BiometricPrompt.PromptInfo.Builder
    {
        return BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric login")
            .setSubtitle("Login using your biometric credential")
    }

    private fun checkBioMetricSupported(){
        var info = ""
        val manager = BiometricManager.from(this)
        when (manager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_WEAK
                or BiometricManager.Authenticators.BIOMETRIC_STRONG)) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                info = "App can authenticate using biometrics"
            }
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                info = "No biometric features available on this device"
            }
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                info = "No biometric features are currently unavailable"
            }
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                info = "Need to register at least one fingerprint"
            }
            else -> {
                info = "Unknown error"
            }

        }
        val txinfo : TextView = findViewById(R.id.tx_info)
        txinfo.setText(info);
//        val manager = BiometricManager.from(this)
//        switch(manager.canAuthenticate())
//        BiometricManager
//        BiometricManager.from(context).canAuthenticate(int) == BiometricManager.BIOMETRIC_SUCCESS
    }
    fun enableButton(enable: Boolean) {
        btn_fp.isEnabled = enable
        btn_pin.isEnabled = enable
        
    }
    fun enableButton(enable : Boolean, enroll : Boolean){
        enableButton(enable)
        if(!enable) return
        val enrollIntent : Intent = Intent(Settings.ACTION_BIOMETRIC_ENROLL)
        enrollIntent.putExtra(Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
            BiometricManager.Authenticators.BIOMETRIC_STRONG
            or BiometricManager.Authenticators.BIOMETRIC_WEAK)
        startActivity(enrollIntent)
    }
}
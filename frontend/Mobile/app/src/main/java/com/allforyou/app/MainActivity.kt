package com.allforyou.app

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import com.allforyou.app.databinding.ActivityMainBinding
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging

private const val TAG_HOME = "home_fragment"
private const val TAG_PRINT = "print_fragment"
private const val TAG_PRESENT = "present_fragment"
private const val TAG_PET = "pet_fragment"
private const val TAG_MYPAGE="mypage_fragment"

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setFragment(TAG_HOME, HomeFragment())



        binding.navigationView.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.homeFragment -> setFragment(TAG_HOME, HomeFragment())
                R.id.printFragment -> setFragment(TAG_PRINT, PrintFragment())
                R.id.presentFragment-> setFragment(TAG_PRESENT, FinancialProductFragment())
                R.id.petFragment-> setFragment(TAG_PET, PetFragment())
                R.id.mypageFragment-> setFragment(TAG_MYPAGE, MypageFragment())
            }
            true
        }
        askNotificationPermission()

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("my_tag", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }


            // Get new FCM registration token
            val token = task.result
            Log.d("my_tag","FCM Token : "+token);

            MyFirebaseMessagingService().sendRegistrationToServer(token)

            // Log and toast
//            val msg = getString(R.string.msg_token_fmt, token)
//            Log.d("my_tag", msg)
//            Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
        })
    }

    private fun setFragment(tag: String, fragment: Fragment) {
        val manager: FragmentManager = supportFragmentManager
        val fragTransaction = manager.beginTransaction()

        if (manager.findFragmentByTag(tag) == null){
            fragTransaction.add(R.id.mainFrameLayout, fragment, tag)
        }


        val home = manager.findFragmentByTag(TAG_HOME)
        val print = manager.findFragmentByTag(TAG_PRINT)
        val present = manager.findFragmentByTag(TAG_PRESENT)
        val pet = manager.findFragmentByTag(TAG_PET)
        val mypage=manager.findFragmentByTag(TAG_MYPAGE)

        if (home != null){
            fragTransaction.hide(home)
        }
        if (print != null){
            fragTransaction.hide(print)
        }


        if (present != null) {
            fragTransaction.hide(present)
        }
        if (pet != null) {
            fragTransaction.hide(pet)
        }
        if (mypage != null) {
            fragTransaction.hide(mypage)
        }


      if (tag == TAG_HOME) {
            if (home != null) {
                fragTransaction.show(home)
            }
        }

        else if (tag == TAG_PRINT) {
            if (print!=null){
                fragTransaction.show(print)
            }
        }

        else if (tag == TAG_PRESENT) {
            if (present!=null){
                fragTransaction.show(present)
            }
        }

        else if (tag == TAG_PET){
            if (pet != null){
                fragTransaction.show(pet)
            }
        }
      else if (tag == TAG_MYPAGE){
          if (mypage != null){
              fragTransaction.show(mypage)
          }
      }

        fragTransaction.commitAllowingStateLoss()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when(event?.action){
            MotionEvent.ACTION_DOWN->{
                Log.d("터치 이벤트","Touch down event")
                Log.d("터치 좌표","터치 이벤트 좌표 X: ${event.rawX}, Y: ${event.rawY}")
            }
            MotionEvent.ACTION_UP->{
                Log.d("터치 이벤트","Touch up event")
            }
        }

        return super.onTouchEvent(event)
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isGranted: Boolean ->
        if (isGranted) {
            // FCM SDK (and your app) can post notifications.
        } else {
            // TODO: Inform user that that your app will not show notifications.
        }
    }

    private fun askNotificationPermission() {
        // This is only necessary for API level >= 33 (TIRAMISU)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED
            ) {
                // FCM SDK (and your app) can post notifications.
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                // TODO: display an educational UI explaining to the user the features that will be enabled
                //       by them granting the POST_NOTIFICATION permission. This UI should provide the user
                //       "OK" and "No thanks" buttons. If the user selects "OK," directly request the permission.
                //       If the user selects "No thanks," allow the user to continue without notifications.
            } else {
                // Directly ask for the permission
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }
}
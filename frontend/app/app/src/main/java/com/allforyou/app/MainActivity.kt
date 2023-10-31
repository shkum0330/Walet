package com.allforyou.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.allforyou.app.PresentFragment
import com.allforyou.app.HomeFragment
import com.allforyou.app.PetFragment
import com.allforyou.app.R
import com.allforyou.app.PrintFragment
import com.allforyou.app.databinding.ActivityMainBinding


private const val TAG_HOME = "home_fragment"
private const val TAG_PRINT = "print_fragment"
private const val TAG_PRESENT = "present_fragment"
private const val TAG_PET = "pet_fragment"



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
                R.id.presentFragment-> setFragment(TAG_PRESENT, PresentFragment())
                R.id.petFragment-> setFragment(TAG_PET, PetFragment())
            }
            true
        }
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

        fragTransaction.commitAllowingStateLoss()
    }
}
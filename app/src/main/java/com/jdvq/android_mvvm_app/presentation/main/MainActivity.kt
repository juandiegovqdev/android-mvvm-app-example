package com.jdvq.android_mvvm_app.presentation.main

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.jdvq.android_mvvm_app.R
import com.jdvq.android_mvvm_app.databinding.ActivityMainBinding
import com.jdvq.android_mvvm_app.presentation.base.BaseActivity
import com.jdvq.android_mvvm_app.presentation.fragments.MainFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupDataBinding()
        if (savedInstanceState == null) {
            loadFragment(MainFragment())
        }
    }

    private fun setupDataBinding() {
        binding = DataBindingUtil.setContentView(
            this, R.layout.activity_main
        )
    }

    fun loadFragment(fragment: Fragment) {
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    fun loadFragment(fragment: Fragment, bundle: Bundle) {
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragment.arguments = bundle
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
}
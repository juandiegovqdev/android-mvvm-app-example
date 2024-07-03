package com.jdvq.android_mvvm_app.presentation.main

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.ui.AppBarConfiguration
import com.jdvq.android_mvvm_app.R
import com.jdvq.android_mvvm_app.databinding.ActivityMainBinding
import com.jdvq.android_mvvm_app.presentation.base.BaseActivity
import com.jdvq.android_mvvm_app.presentation.fragments.MainFragment
import dagger.hilt.android.AndroidEntryPoint

/*
Objective:
Develop an Android application for managing various objects (e.g., desk, computer, keyboard, server, or human being) with attributes and relationships. The goal is to assess your ability to design and implement a functional, maintainable, and user-friendly system.

Requirements:
Attributes of Objects:

Name
Description
Type

Functionalities:

Create, Edit, and Delete Objects:
Implement functionality to create, edit, and delete objects.
Create, Edit, and Delete Relations between Objects:
Implement functionality to create, edit, and delete relationships between objects (e.g., a desk can
contain a calculator, and Max uses the desk as a workplace).
Search for Objects:
Implement a search feature to find objects by their attributes.
Persistent Storage:
Ensure all data is serializable and stored persistently.
User interface:

Design a simple and intuitive GUI using native components and styles. The example GUI provided can
be a reference, but creativity and usability improvements are encouraged.
Additional Information:
Technical Specifications:

Language:Kotlin
Frameworks:Freely chosen based on your expertise and justification for the selection.
Persistent Storage:Choose a suitable solution (e.g., SQLite, Room, SharedPreferences, etc.)
Assessment Criteria:

Code quality and structure
Choice of frameworks and technologies
User interface design and usability
Completeness and correctness of functionalities
Justification for chosen programming concepts and frameworks
Submission:

Deadline:Within 48 hours after receiving this task.
Deliverables:
Complete source code
A brief document explaining the choice of frameworks, programming concepts, and any other relevant decisions.
Instructions for building and running the application.

Evaluation:
Your submission will be evaluated on the following:

Adherence to the requirements
Code readability and maintainability
Effective use of chosen frameworks and technologies
User interface quality and functionality
Documentation clarity
*/

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    // private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViewModels()
        setupDataBinding()
        //    binding.viewModel = viewModel

        if (savedInstanceState == null) {
            loadFragment(MainFragment())
        }
    }

    private fun setupDataBinding() {
        binding = DataBindingUtil.setContentView(
            this, R.layout.activity_main
        )
    }

    private fun setupViewModels() {
        //    viewModel = ViewModelProvider(this)[MainViewModel::class.java]
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
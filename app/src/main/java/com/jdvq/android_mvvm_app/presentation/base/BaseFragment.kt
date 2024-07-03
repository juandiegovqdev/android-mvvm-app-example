package com.jdvq.android_mvvm_app.presentation.base

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        val menuResourceId = getMenuResourceId()
        if (menuResourceId != null) {
            inflater.inflate(menuResourceId, menu)
        }
        super.onCreateOptionsMenu(menu, inflater)
    }


    abstract fun getMenuResourceId(): Int?
}

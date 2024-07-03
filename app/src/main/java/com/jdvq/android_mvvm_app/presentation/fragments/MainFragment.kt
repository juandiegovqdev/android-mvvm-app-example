package com.jdvq.android_mvvm_app.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.jdvq.android_mvvm_app.ObjectAdapter
import com.jdvq.android_mvvm_app.R
import com.jdvq.android_mvvm_app.config.GlobalVariables
import com.jdvq.android_mvvm_app.databinding.FragmentMainBinding
import com.jdvq.android_mvvm_app.presentation.base.BaseFragment
import com.jdvq.android_mvvm_app.presentation.main.MainActivity
import com.jdvq.android_mvvm_app.presentation.main.MainViewModel
import com.jdvq.android_mvvm_app.utils.showExitDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment() {

    private lateinit var viewModel: MainViewModel
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var objectAdapter: ObjectAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        setupDataBinding(inflater, container)

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        binding.toolbar.setTitleTextColor(resources.getColor(R.color.white))
        binding.toolbar.setNavigationOnClickListener {
            (activity as AppCompatActivity).onBackPressed()
        }

        return binding.root
    }

    private fun setupViewModels() {
        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViewModels()

        viewModel.getAllObject()

        objectAdapter = ObjectAdapter(requireContext(), viewModel, activity)

        objectAdapter.setObjects(GlobalVariables.objects)

        binding.recyclerView.adapter = objectAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        binding.fab.setOnClickListener {
            (activity as MainActivity).loadFragment(EditObjectFragment())
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { objectAdapter.filter.filter(it) }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { objectAdapter.filter.filter(it) }
                return false
            }
        })
    }

    override fun getMenuResourceId(): Int {
        return R.menu.menu_main
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_exit_app -> {
                showExitDialog(requireActivity(), getString(R.string.title_exit_app))
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupDataBinding(inflater: LayoutInflater, container: ViewGroup?) {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
    }
}
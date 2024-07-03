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
import com.jdvq.android_mvvm_app.R
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        setupDataBinding(inflater, container)
        setupToolbar()
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViewModels()
        viewModel.setupObjectAdapter(requireContext(), requireActivity())
        setupRecyclerView()
        setupListeners()
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

    private fun setupToolbar() {
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        binding.toolbar.setTitleTextColor(resources.getColor(R.color.white))
        binding.toolbar.setNavigationOnClickListener {
            (activity as AppCompatActivity).onBackPressed()
        }
    }

    private fun setupViewModels() {
        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
    }

    private fun setupListeners() {
        binding.fab.setOnClickListener {
            (activity as MainActivity).loadFragment(EditObjectFragment())
        }
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { viewModel.objectAdapter.filter.filter(it) }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { viewModel.objectAdapter.filter.filter(it) }
                return false
            }
        })
    }

    private fun setupRecyclerView() {
        binding.recyclerView.adapter = viewModel.objectAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun setupDataBinding(inflater: LayoutInflater, container: ViewGroup?) {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
    }
}
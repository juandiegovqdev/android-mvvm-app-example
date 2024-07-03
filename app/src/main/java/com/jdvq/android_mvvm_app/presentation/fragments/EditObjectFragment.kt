package com.jdvq.android_mvvm_app.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.jdvq.android_mvvm_app.R
import com.jdvq.android_mvvm_app.RelationAdapter
import com.jdvq.android_mvvm_app.config.GlobalVariables
import com.jdvq.android_mvvm_app.databinding.FragmentEditObjectBinding
import com.jdvq.android_mvvm_app.domain.entities.RelationEntity
import com.jdvq.android_mvvm_app.domain.models.ObjectModel
import com.jdvq.android_mvvm_app.presentation.base.BaseFragment
import com.jdvq.android_mvvm_app.presentation.dialog.SelectChildDialogFragment
import com.jdvq.android_mvvm_app.presentation.main.MainActivity
import com.jdvq.android_mvvm_app.presentation.main.MainViewModel
import com.jdvq.android_mvvm_app.utils.showDialog
import com.jdvq.android_mvvm_app.utils.showExitDialog
import com.jdvq.android_mvvm_app.utils.validateEditText
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class EditObjectFragment : BaseFragment() {

    private lateinit var viewModel: MainViewModel
    private var _binding: FragmentEditObjectBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        setupDataBinding(inflater, container)

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        binding.toolbar.setTitleTextColor(resources.getColor(R.color.white))
        binding.toolbar.setNavigationOnClickListener {
            (activity as AppCompatActivity).onBackPressed()
        }
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        return binding.root
    }

    private fun setupViewModels() {
        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViewModels()
        setupListeners()

        val objectModel = arguments?.getParcelable<ObjectModel>("objectModel")
        if (objectModel != null) {
            viewModel.setObjectModel(objectModel)
            viewModel.getRelation(objectModel)
            GlobalVariables.currentObject = objectModel
            binding.nameEditText.setText(viewModel.objectModel.value!!.name)
            binding.descriptionEditText.setText(viewModel.objectModel.value!!.description)
            binding.typeEditText.setText(viewModel.objectModel.value!!.type)
            viewModel.getRelation(objectModel)
            viewModel.relationAdapter =
                RelationAdapter(viewModel, objectModel, GlobalVariables.relations) {

                }
            viewModel.relationAdapter.updateRelations(GlobalVariables.relations)
            binding.relationsRecyclerView.apply {
                adapter = viewModel.relationAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
        }
    }

    override fun getMenuResourceId(): Int {
        return R.menu.menu_edit_object_fragment
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_save -> {
                saveObject()
                true
            }

            R.id.action_exit_app -> {
                showExitDialog(requireActivity(), getString(R.string.title_exit_app))
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupEditTextValidation(): Boolean {
        var isValid = true
        if (validateEditText(binding.typeEditText)) {
            showDialog(
                requireContext(),
                getString(R.string.data_missing),
                getString(R.string.please_enter_type)
            )
            isValid = false
        }
        if (validateEditText(binding.nameEditText)) {
            showDialog(
                requireContext(),
                getString(R.string.data_missing),
                getString(R.string.please_enter_a_name)
            )
            isValid = false
        }
        if (validateEditText(binding.descriptionEditText)) {
            showDialog(
                requireContext(),
                getString(R.string.data_missing),
                getString(R.string.please_enter_a_description)
            )
            isValid = false
        }
        return isValid
    }

    private fun saveObject() {
        if (setupEditTextValidation()) {
            GlobalVariables.currentObject = ObjectModel(
                id = 0L,
                binding.nameEditText.text.toString(),
                binding.descriptionEditText.text.toString(),
                binding.typeEditText.text.toString()
            )
            GlobalVariables.objects.add(GlobalVariables.currentObject)
            val objectModel = arguments?.getParcelable<ObjectModel>("objectModel")
            if (objectModel != null) {
                viewModel.deleteObjectById(objectModel)
            }
            viewModel.insertObject()
            (activity as MainActivity).loadFragment(MainFragment())
        }
    }

    private fun setupListeners() {
        binding.saveButton.setOnClickListener {
            saveObject()
        }
        binding.addRelations.setOnClickListener {
            val dialog = SelectChildDialogFragment()
            dialog.onChildSelected = { selectedChild ->
                val parentId = viewModel.objectModel.value?.id ?: 0
                val newRelation = RelationEntity(parentId = parentId, childId = selectedChild.id)
                viewModel.insertRelation(newRelation)
                viewModel.relationAdapter.updateRelations(GlobalVariables.relations)
            }
            dialog.show(parentFragmentManager, "SelectChildDialogFragment")
        }
    }

    private fun setupDataBinding(inflater: LayoutInflater, container: ViewGroup?) {
        _binding = FragmentEditObjectBinding.inflate(inflater, container, false)
    }
}
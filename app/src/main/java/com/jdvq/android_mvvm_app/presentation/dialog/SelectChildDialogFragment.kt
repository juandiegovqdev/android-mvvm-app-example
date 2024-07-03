package com.jdvq.android_mvvm_app.presentation.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.jdvq.android_mvvm_app.domain.adapters.ObjectAdapter
import com.jdvq.android_mvvm_app.config.GlobalVariables
import com.jdvq.android_mvvm_app.databinding.DialogSelectChildBinding
import com.jdvq.android_mvvm_app.domain.models.ObjectModel
import com.jdvq.android_mvvm_app.presentation.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelectChildDialogFragment : DialogFragment() {

    private var _binding: DialogSelectChildBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MainViewModel
    var onChildSelected: ((ObjectModel) -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = DialogSelectChildBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupRecyclerView()
        setupObservers()
        setupObjects()
    }

    private fun setupObjects() {
        viewModel.loadObjects(GlobalVariables.currentObject.id)
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
    }

    private fun setupRecyclerView() {
        val objectAdapter = ObjectAdapter(requireContext(), viewModel, activity) { selectedObject ->
            onChildSelected?.invoke(selectedObject)
            dismiss()
        }
        binding.childObjectsRecyclerView.apply {
            adapter = objectAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun setupObservers() {
        (binding.childObjectsRecyclerView.adapter as ObjectAdapter).setObjects(GlobalVariables.objects)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

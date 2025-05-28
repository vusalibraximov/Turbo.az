package com.example.turboaz.presentation.car_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.turboaz.R
import com.example.turboaz.databinding.FragmentCarListBinding
import com.example.turboaz.presentation.car_list.adapter.CarListAdapter
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CarListFragment : Fragment() {

    private var _binding: FragmentCarListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CarListViewModel by viewModels()
    private val carAdapter = CarListAdapter { car ->
        findNavController().navigate(
            CarListFragmentDirections.actionCarListFragmentToCarDetailFragment(car.id)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCarListBinding.inflate(inflater, container, false)
        return binding.root
    }

    @OptIn(FlowPreview::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupSearchView()
        setupSwipeRefresh()
        setupFilterButton()
        observeState()
    }

    private fun setupRecyclerView() {
        binding.carsRecyclerView.adapter = carAdapter
    }

    @OptIn(FlowPreview::class)
    private fun setupSearchView() {
        binding.searchEditText.addTextChangedListener { editable ->
            viewLifecycleOwner.lifecycleScope.launch {
                editable?.toString()?.let { query ->
                    viewModel.onEvent(CarListEvent.Search(query))
                }
            }
        }
    }

    private fun setupSwipeRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.onEvent(CarListEvent.Refresh)
        }
    }

    private fun setupFilterButton() {
        binding.filterButton.setOnClickListener {
            // Show filter dialog
            FilterDialogFragment().show(childFragmentManager, FilterDialogFragment.TAG)
        }
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collectLatest { state ->
                binding.apply {
                    progressBar.isVisible = state.isLoading
                    errorTextView.isVisible = state.error != null
                    errorTextView.text = state.error
                    swipeRefreshLayout.isRefreshing = state.isLoading
                    
                    if (state.error != null) {
                        Snackbar.make(
                            root,
                            state.error,
                            Snackbar.LENGTH_LONG
                        ).setAction("Retry") {
                            viewModel.onEvent(CarListEvent.Refresh)
                        }.show()
                    }

                    carAdapter.submitList(state.cars)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

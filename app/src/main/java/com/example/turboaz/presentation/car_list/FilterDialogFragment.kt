package com.example.turboaz.presentation.car_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.turboaz.R
import com.example.turboaz.databinding.FragmentFilterDialogBinding
import com.google.android.material.slider.RangeSlider
import dagger.hilt.android.AndroidEntryPoint
import java.text.NumberFormat
import java.util.Locale

@AndroidEntryPoint
class FilterDialogFragment : DialogFragment() {

    private var _binding: FragmentFilterDialogBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CarListViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilterDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupPriceRangeSlider()
        setupYearRangeSlider()
        setupTransmissionFilter()
        setupFuelTypeFilter()
        setupButtons()
    }

    private fun setupPriceRangeSlider() {
        binding.priceRangeSlider.apply {
            valueFrom = 0f
            valueTo = 100000f
            values = listOf(0f, 100000f)
            
            setLabelFormatter { value ->
                NumberFormat.getCurrencyInstance(Locale.US)
                    .format(value.toDouble())
            }

            addOnChangeListener { slider, _, _ ->
                updatePriceLabels(slider.values[0], slider.values[1])
            }
        }
    }

    private fun setupYearRangeSlider() {
        binding.yearRangeSlider.apply {
            valueFrom = 1990f
            valueTo = 2024f
            values = listOf(1990f, 2024f)
            stepSize = 1f

            setLabelFormatter { value ->
                value.toInt().toString()
            }

            addOnChangeListener { slider, _, _ ->
                updateYearLabels(slider.values[0].toInt(), slider.values[1].toInt())
            }
        }
    }

    private fun setupTransmissionFilter() {
        // Setup transmission chip group
        binding.transmissionChipGroup.setOnCheckedStateChangeListener { group, _ ->
            // Handle transmission selection
        }
    }

    private fun setupFuelTypeFilter() {
        // Setup fuel type chip group
        binding.fuelTypeChipGroup.setOnCheckedStateChangeListener { group, _ ->
            // Handle fuel type selection
        }
    }

    private fun setupButtons() {
        binding.applyButton.setOnClickListener {
            applyFilters()
            dismiss()
        }

        binding.resetButton.setOnClickListener {
            resetFilters()
        }
    }

    private fun updatePriceLabels(min: Float, max: Float) {
        val format = NumberFormat.getCurrencyInstance(Locale.US)
        binding.minPriceTextView.text = format.format(min.toDouble())
        binding.maxPriceTextView.text = format.format(max.toDouble())
    }

    private fun updateYearLabels(min: Int, max: Int) {
        binding.minYearTextView.text = min.toString()
        binding.maxYearTextView.text = max.toString()
    }

    private fun applyFilters() {
        val priceRange = binding.priceRangeSlider.values
        val yearRange = binding.yearRangeSlider.values
        
        val selectedTransmission = when (binding.transmissionChipGroup.checkedChipId) {
            R.id.chipAutomatic -> "AUTOMATIC"
            R.id.chipManual -> "MANUAL"
            R.id.chipSemiAutomatic -> "SEMI_AUTOMATIC"
            else -> null
        }

        val selectedFuelType = when (binding.fuelTypeChipGroup.checkedChipId) {
            R.id.chipGasoline -> "GASOLINE"
            R.id.chipDiesel -> "DIESEL"
            R.id.chipElectric -> "ELECTRIC"
            R.id.chipHybrid -> "HYBRID"
            else -> null
        }

        viewModel.onEvent(CarListEvent.Filter(
            CarFilters(
                minPrice = priceRange[0].toDouble(),
                maxPrice = priceRange[1].toDouble(),
                minYear = yearRange[0].toInt(),
                maxYear = yearRange[1].toInt(),
                transmission = selectedTransmission,
                fuelType = selectedFuelType
            )
        ))
    }

    private fun resetFilters() {
        binding.apply {
            priceRangeSlider.values = listOf(0f, 100000f)
            yearRangeSlider.values = listOf(1990f, 2024f)
            transmissionChipGroup.clearCheck()
            fuelTypeChipGroup.clearCheck()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "FilterDialogFragment"
    }
}

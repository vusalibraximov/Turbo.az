package com.example.turboaz.presentation.car_detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.turboaz.R
import com.example.turboaz.databinding.FragmentCarDetailBinding
import com.example.turboaz.domain.model.Car
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale

@AndroidEntryPoint
class CarDetailFragment : Fragment() {

    private var _binding: FragmentCarDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CarDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCarDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        observeState()
        setupContactButton()
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collectLatest { state ->
                binding.progressBar.isVisible = state.isLoading

                state.car?.let { car ->
                    updateUI(car)
                }

                state.error?.let { error ->
                    Snackbar.make(
                        binding.root,
                        error,
                        Snackbar.LENGTH_LONG
                    ).setAction("Retry") {
                        // Retry loading car details
                    }.show()
                }
            }
        }
    }

    private fun updateUI(car: Car) {
        binding.apply {
            // Load car image
            Glide.with(requireContext())
                .load(car.imageUrl)
                .placeholder(R.drawable.placeholder_car)
                .error(R.drawable.error_car)
                .into(carImageView)

            // Set car details
            carTitleTextView.text = "${car.year} ${car.brand} ${car.model}"
            
            // Format price
            val formattedPrice = NumberFormat.getCurrencyInstance(Locale.US)
                .format(car.price)
            priceTextView.text = formattedPrice

            // Set specifications
            mileageTextView.text = "${car.mileage} km"
            fuelTypeTextView.text = car.fuelType
            transmissionTextView.text = car.transmission
            colorTextView.text = car.color
            descriptionTextView.text = car.description

            // Set seller information
            sellerNameTextView.text = car.sellerName
            locationTextView.text = car.location
            phoneTextView.text = car.sellerPhone
        }
    }

    private fun setupContactButton() {
        binding.contactSellerButton.setOnClickListener {
            viewModel.state.value.car?.let { car ->
                val intent = Intent(Intent.ACTION_DIAL).apply {
                    data = Uri.parse("tel:${car.sellerPhone}")
                }
                startActivity(intent)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

package com.example.turboaz.presentation.car_list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.turboaz.R
import com.example.turboaz.databinding.ItemCarBinding
import com.example.turboaz.domain.model.Car
import java.text.NumberFormat
import java.util.Locale

class CarListAdapter(
    private val onCarClick: (Car) -> Unit
) : ListAdapter<Car, CarListAdapter.CarViewHolder>(CarDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        val binding = ItemCarBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CarViewHolder(binding, onCarClick)
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class CarViewHolder(
        private val binding: ItemCarBinding,
        private val onCarClick: (Car) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(car: Car) {
            binding.apply {
                // Load car image
                Glide.with(carImageView)
                    .load(car.imageUrl)
                    .placeholder(R.drawable.placeholder_car)
                    .error(R.drawable.error_car)
                    .centerCrop()
                    .into(carImageView)

                // Format price
                val formattedPrice = NumberFormat.getCurrencyInstance(Locale.US).format(car.price)
                priceTextView.text = formattedPrice

                // Set car details
                carTitleTextView.text = "${car.year} ${car.brand} ${car.model}"
                mileageTextView.text = "${car.mileage} km"
                fuelTypeTextView.text = car.fuelType
                transmissionTextView.text = car.transmission
                locationTextView.text = car.location

                // Set click listener
                root.setOnClickListener { onCarClick(car) }
            }
        }
    }

    private class CarDiffCallback : DiffUtil.ItemCallback<Car>() {
        override fun areItemsTheSame(oldItem: Car, newItem: Car): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Car, newItem: Car): Boolean {
            return oldItem == newItem
        }
    }
}

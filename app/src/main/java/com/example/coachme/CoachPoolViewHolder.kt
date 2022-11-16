package com.example.coachme
import androidx.recyclerview.widget.RecyclerView
import com.example.coachme.databinding.ItemCoachpoolBinding


class CoachPoolViewHolder(
    private val binding: ItemCoachpoolBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(coach: Coach) {
        binding.coach = coach
    }
}
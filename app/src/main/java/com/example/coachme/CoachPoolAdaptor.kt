package com.example.coachme

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.coachme.databinding.ItemCoachpoolBinding


class CoachPoolAdaptor(private var coachesList: List<Coach>) :
    RecyclerView.Adapter<CoachPoolViewHolder>() {
    private lateinit var binding: ItemCoachpoolBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoachPoolViewHolder {
        binding = ItemCoachpoolBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CoachPoolViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CoachPoolViewHolder, position: Int) {
        val coach = coachesList[position]
        Log.i("onBindViewHolder", coach.toString())
        holder.bind(coach)
    }

    override fun getItemCount(): Int {
        Log.i("getItemCount", coachesList.size.toString())
        return coachesList.size
    }


}
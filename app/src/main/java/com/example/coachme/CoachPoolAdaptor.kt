package com.example.coachme

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.coachme.databinding.ItemCoachpoolBinding


class CoachPoolAdaptor(private var coachesList: ArrayList<Coach>, private val onSelect: (Coach?) -> Unit) :
    RecyclerView.Adapter<CoachPoolViewHolder>() {
    private lateinit var binding: ItemCoachpoolBinding


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoachPoolViewHolder {
        binding = ItemCoachpoolBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CoachPoolViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CoachPoolViewHolder, position: Int) {
        val coach = coachesList[position]
        Log.i("onBindViewHolder", coach.toString())
        holder.bind(coach, onSelect)
    }

    override fun getItemCount(): Int {
        Log.i("getItemCount", coachesList.size.toString())
        return coachesList.size
    }

    class OnClickListener(val clickListener: (coach: Coach) -> Unit) {
        fun onClick(coach: Coach) = clickListener(coach)
    }
    fun updateData(coaches: ArrayList<Coach>) {
        coachesList = ArrayList()
        (coachesList as ArrayList<Coach>).addAll(coaches)
    }

/*    fun updateData(coaches: ArrayList<Coach>) {
        coachesList = ArrayList()
        coachesList.addAll(coaches)
    } */

}

//class CoachPoolAdaptor(private var coachesList: List<Coach>) :
//    RecyclerView.Adapter<CoachPoolViewHolder>() {
//    private lateinit var binding: ItemCoachpoolBinding
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoachPoolViewHolder {
//        binding = ItemCoachpoolBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return CoachPoolViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: CoachPoolViewHolder, position: Int) {
//        val coach = coachesList[position]
//        Log.i("onBindViewHolder", coach.toString())
//        holder.bind(coach)
//    }
//
//    override fun getItemCount(): Int {
//        Log.i("getItemCount", coachesList.size.toString())
//        return coachesList.size
//    }
//
//
//}
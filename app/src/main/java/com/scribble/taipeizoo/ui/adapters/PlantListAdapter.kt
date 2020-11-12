package com.scribble.taipeizoo.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.scribble.taipeizoo.R
import com.scribble.taipeizoo.data.models.Plant
import com.scribble.taipeizoo.databinding.PlantItemBinding
import com.scribble.taipeizoo.ui.PlantClickListener

class PlantListAdapter(private val plants: ArrayList<Plant>, private val listener: PlantClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun getItemCount() = plants.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil
            .inflate<PlantItemBinding>(inflater, R.layout.plant_item, parent, false)
        return PlantViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val plant = plants[position]
        val viewholder = holder as PlantViewHolder
        viewholder.binding.plant = plant
        viewholder.binding.root.setOnClickListener {
            listener.onPlantClicked(plant)
        }
    }

    inner class PlantViewHolder(val binding: PlantItemBinding) : RecyclerView.ViewHolder(binding.root)
}


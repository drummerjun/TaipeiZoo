package com.scribble.taipeizoo.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.scribble.taipeizoo.R
import com.scribble.taipeizoo.data.models.Zone
import com.scribble.taipeizoo.databinding.ZoneItemBinding
import com.scribble.taipeizoo.ui.ZoneClickListener

class ZonesListAdapter(private val zones: ArrayList<Zone>, private val listener: ZoneClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun getItemCount() = zones.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil
            .inflate<ZoneItemBinding>(inflater, R.layout.zone_item, parent, false)
        return ZoneViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val zone = zones[position]
        val viewholder = holder as ZoneViewHolder
        viewholder.binding.zone = zone
        viewholder.binding.root.setOnClickListener {
            listener.onZoneClicked(position)
        }
    }

    inner class ZoneViewHolder(val binding: ZoneItemBinding) : RecyclerView.ViewHolder(binding.root)
}


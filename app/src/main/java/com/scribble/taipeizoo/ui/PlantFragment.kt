package com.scribble.taipeizoo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.scribble.taipeizoo.R
import com.scribble.taipeizoo.data.models.Plant
import com.scribble.taipeizoo.databinding.FragmentPlantBinding

/**
 * PlantFragment:
 * fragment that shows info on selected plant
 *
 * 1. display plant info in multiple ImageViews & TextViews within a ScrollView
 *
 * issues: Unable to retrieve Chinese name, Latin name is displayed instead
 *         Unable to load Function&Applications, hence field is hidden
 */

class PlantFragment(private val plant: Plant) : Fragment() {
    private lateinit var binding: FragmentPlantBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_plant, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.plant = plant
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as? AppCompatActivity)?.supportActionBar?.run {
            title = plant.F_Name_Latin
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
        setHasOptionsMenu(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            activity?.onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
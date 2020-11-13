package com.scribble.taipeizoo.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.scribble.taipeizoo.R
import com.scribble.taipeizoo.data.models.Plant
import com.scribble.taipeizoo.data.models.Zone
import com.scribble.taipeizoo.databinding.FragmentZoneBinding
import com.scribble.taipeizoo.ui.adapters.PlantListAdapter
import com.scribble.taipeizoo.ui.viewmodels.ZooViewModel

/**
 * ZoneFragment:
 * fragment that shows info on selected zone
 *
 * associated viewModel: ZooViewModel
 *
 * 1. take data from csv and display in recyclerview
 * 2. calls api to retrieve botanicals affiliated with current zone
 *    (max count is set at 20)
 * 3. hyperlink is provided to view zone info in external browser
 *
 * issue: unable to retrieve Chinese name, Latin name is displayed instead
 */

class ZoneFragment(private val zone: Zone) : Fragment(), PlantClickListener {
    private lateinit var viewModel: ZooViewModel
    private lateinit var binding: FragmentZoneBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(ZooViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel.plantLiveData.observe(viewLifecycleOwner, Observer { plants ->
            if (plants.isNotEmpty()) {
                val botanicals = ArrayList<Plant>()
                botanicals.addAll(plants)
                val layoutManager = LinearLayoutManager(requireActivity())
                layoutManager.orientation = LinearLayoutManager.VERTICAL
                binding.plantsList.layoutManager = layoutManager
                binding.plantsList.adapter = PlantListAdapter(botanicals, this)
                binding.plantsList.setHasFixedSize(true)
                val dividerItemDecoration =
                    DividerItemDecoration(binding.plantsList.context, layoutManager.orientation)
                binding.plantsList.addItemDecoration(dividerItemDecoration)
                binding.titleLabel.visibility = View.VISIBLE
            }
            binding.refresher.isRefreshing = false
        })

        viewModel.clickLivedata.observe(viewLifecycleOwner, Observer { clicked ->
            if (clicked) {
                binding.scroller.scrollTo(0, 0)
            }
        })

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_zone, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        binding.zone = zone
        binding.refresher.isRefreshing = true
        viewModel.getPlants(zone.name)
        binding.refresher.setOnRefreshListener {
            viewModel.getPlants(zone.name)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.scroller.setOnScrollChangeListener { _, _, scrollY: Int, _, _ ->
            if (scrollY > 2000) {
                viewModel.scrollLivedata.postValue(true)
            } else {
                viewModel.scrollLivedata.postValue(false)
            }
        }

        (activity as? AppCompatActivity)?.supportActionBar?.run {
            title = zone.name
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

    override fun onPlantClicked(plant: Plant) {
        val plantFragment = PlantFragment(plant)
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.root_container, plantFragment)
            ?.addToBackStack(plant.name)
            ?.commitAllowingStateLoss()
    }
}

interface PlantClickListener {
    fun onPlantClicked(plant: Plant)
}

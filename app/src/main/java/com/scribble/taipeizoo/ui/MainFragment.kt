package com.scribble.taipeizoo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.scribble.taipeizoo.R
import com.scribble.taipeizoo.data.models.Zone
import com.scribble.taipeizoo.databinding.FragmentMainBinding
import com.scribble.taipeizoo.ui.adapters.ZonesListAdapter
import com.scribble.taipeizoo.ui.viewmodels.ZooViewModel

/**
 * MainFragment:
 * first fragment to be displayed
 *
 * associated viewModel: ZooViewModel
 *
 * 1. take data from csv and display in recyclerview
 */

class MainFragment(private val zones: ArrayList<Zone>) : Fragment(), ZoneClickListener {
    private lateinit var viewModel: ZooViewModel
    private lateinit var binding: FragmentMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(ZooViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        (activity as? AppCompatActivity)?.supportActionBar?.run {
            title = getString(R.string.app_name)
            setDisplayHomeAsUpEnabled(false)
            setDisplayShowHomeEnabled(false)
        }

        if (binding.zoneList.adapter == null) {
            val adapter = ZonesListAdapter(zones, this)
            val layoutManager = LinearLayoutManager(requireActivity())
            layoutManager.orientation = LinearLayoutManager.VERTICAL
            binding.zoneList.layoutManager = layoutManager
            binding.zoneList.adapter = adapter
            val dividerItemDecoration = DividerItemDecoration(binding.zoneList.context, layoutManager.orientation)
            binding.zoneList.addItemDecoration(dividerItemDecoration)
            binding.zoneList.addOnScrollListener(object: RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val firstVisible = (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                    if (firstVisible > 2) {
                        viewModel.showFab()
                    } else {
                        viewModel.hideFab()
                    }
                }
            })
            viewModel.setAnchor(binding.zoneList)
        }
    }

    override fun onZoneClicked(position: Int) {
        viewModel.hideFab()
        val zoneFragment = ZoneFragment(zones[position])
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.root_container, zoneFragment)
            ?.addToBackStack(zones[position].name)
            ?.commitAllowingStateLoss()
    }
}

interface ZoneClickListener {
    fun onZoneClicked(position: Int)
}
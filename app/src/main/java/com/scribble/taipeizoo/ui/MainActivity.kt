package com.scribble.taipeizoo.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.scribble.taipeizoo.R
import com.scribble.taipeizoo.databinding.ActivityMainBinding
import com.scribble.taipeizoo.ui.viewmodels.ZooViewModel

/**
 * MainActivity:
 * one and only base activity object
 *
 * 1. init toolbar
 * 2. loads repository
 */
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // init repository and load preloaded csv
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(binding.toolbar)
        val viewModel = ViewModelProvider(this).get(ZooViewModel::class.java)
        viewModel.scrollLivedata.observe(this, Observer { scrolled ->
            if (scrolled) {
                binding.fab.show()
            } else {
                binding.fab.hide()
            }
        })
        binding.viewModel = viewModel

        val zones = viewModel.repo.openCSV(this)
        if (zones.isNotEmpty()) {
            if(savedInstanceState == null) {
                val mainFragment = MainFragment(zones)
                supportFragmentManager.beginTransaction()
                    .replace(R.id.root_container, mainFragment, "main")
                    .commitAllowingStateLoss()
            }
        }
    }
}
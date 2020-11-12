package com.scribble.taipeizoo.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.scribble.taipeizoo.data.models.Plant
import com.scribble.taipeizoo.data.repositories.ZooRepository

class ZooViewModel : ViewModel() {
    val repo by lazy { ZooRepository() }
    var plantLiveData = MutableLiveData<List<Plant>>()
    private lateinit var scrollListener: ScrollListener
    private lateinit var clickListener: FabClickListener

    fun setScrollListener(listener: ScrollListener) {
        scrollListener = listener
    }

    fun setFabClickListener(listener: FabClickListener) {
        clickListener = listener
    }

    fun hideFab() {
        scrollListener.hideFab()
    }

    fun showFab() {
        scrollListener.showFab()
    }

    fun getPlants(query: String, limit: Int = 20, offset: Int = 0) {
        repo.getPlants(plantLiveData, query, limit, offset)
    }

    fun scrollToTop() {
        clickListener.scrollToTop()
    }

    interface ScrollListener {
        fun hideFab()
        fun showFab()
    }

    interface FabClickListener {
        fun scrollToTop()
    }
}

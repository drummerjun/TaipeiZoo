package com.scribble.taipeizoo.ui.viewmodels

import android.view.View
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.scribble.taipeizoo.data.models.Plant
import com.scribble.taipeizoo.data.repositories.ZooRepository

class ZooViewModel : ViewModel() {
    val repo by lazy { ZooRepository() }
    var plantLiveData = MutableLiveData<List<Plant>>()
    private var anchorView: View? = null
    private lateinit var viewModelListener: VMOnClickListener

    fun setListener(listener: VMOnClickListener) {
        viewModelListener = listener
    }

    fun hideFab() {
        viewModelListener.hideFab()
    }

    fun showFab() {
        viewModelListener.showFab()
    }

    fun getPlants(query: String, limit: Int = 20, offset: Int = 0) {
        repo.getPlants(plantLiveData, query, limit, offset)
    }

    fun setAnchor(view: View) {
        anchorView = view
    }

    fun scrollToTop() {
        when(anchorView) {
            is NestedScrollView -> {
                (anchorView as NestedScrollView).scrollTo(0, 0)
            }
            is RecyclerView -> {
                (anchorView as RecyclerView).scrollToPosition(0)
            }
        }
    }

    interface VMOnClickListener {
        fun hideFab()
        fun showFab()
    }
}

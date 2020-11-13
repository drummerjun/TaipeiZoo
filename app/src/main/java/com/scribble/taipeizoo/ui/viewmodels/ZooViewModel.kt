package com.scribble.taipeizoo.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.scribble.taipeizoo.data.models.Plant
import com.scribble.taipeizoo.data.repositories.ZooRepository

class ZooViewModel : ViewModel() {
    val repo by lazy { ZooRepository() }
    var plantLiveData = MutableLiveData<List<Plant>>() // plants data from api call
    var scrollLivedata = MutableLiveData<Boolean>() // observable to determine whether or not show fab
    var clickLivedata = MutableLiveData<Boolean>() // observable to detect click events on fab

    fun getPlants(query: String, limit: Int = 20, offset: Int = 0) {
        repo.getPlants(plantLiveData, query, limit, offset)
    }

    fun scrollToTop() {
        clickLivedata.postValue(true)
    }
}

package com.scribble.taipeizoo.data.repositories

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.scribble.taipeizoo.data.models.Plant
import com.scribble.taipeizoo.data.models.Zone
import com.scribble.taipeizoo.data.network.ApiProvider
import com.scribble.taipeizoo.data.network.ApiService
import kotlinx.coroutines.*
import java.io.BufferedReader
import java.io.InputStreamReader

class ZooRepository {
    private var job: Job? = null

    // calls API to retrieve botanicals
    fun getPlants(liveData: MutableLiveData<List<Plant>>, query: String, limit: Int, offset: Int) {
        val apiService = ApiProvider.createService(ApiService::class.java)
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = apiService.getPlants(query, limit, offset)
            withContext(Dispatchers.Main) {
                if(response.isSuccessful && response.body() != null) {
                    liveData.postValue(response.body()!!.result.results)
                }
            }
        }
    }

    // loads csv file from Assets folder
    @Suppress("LocalVariableName")
    fun openCSV(context: Context): ArrayList<Zone> {
        val COL_E_no = 0
        val COL_E_Categpry = 1
        val COL_E_Name = 2
        val COL_E_Pic_URL = 3
        val COL_E_Info = 4
        val COL_E_Memo = 5
        val COL_E_Geo = 6
        val COL_E_URL = 7
        val zones = ArrayList<Zone>()
        val inputStream = InputStreamReader(context.assets.open("zoo_20200206.csv"), "big5")
        val reader = BufferedReader(inputStream)
        reader.readLine()
        var line: String?
        while (reader.readLine().also { line = it } != null) {
            val tokens = line!!.split(",")
            if (tokens.isNotEmpty()) {
                val dept = Zone(
                    Integer.parseInt(tokens[COL_E_no]),
                    tokens[COL_E_Categpry],
                    tokens[COL_E_Name],
                    tokens[COL_E_Pic_URL],
                    tokens[COL_E_Info],
                    tokens[COL_E_Memo],
                    tokens[COL_E_Geo],
                    tokens[COL_E_URL]
                )
                zones.add(dept)
            }
        }
        return zones
    }
}
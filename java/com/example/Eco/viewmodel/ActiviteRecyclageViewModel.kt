package com.example.Eco.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.Eco.data.ActiviteRecyclage
import com.example.Eco.data.MaterialStatistics
import com.example.Eco.retrofit.RetrofitInstance
import com.example.Eco.retrofit.RetrofitServiceInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ActiviteRecyclageViewModel : ViewModel() {

    lateinit var liveData: MutableLiveData<List<ActiviteRecyclage>>
    lateinit var liveDataStats: MutableLiveData<List<MaterialStatistics>>
    lateinit var createActiviteRecyclageLiveData: MutableLiveData<ActiviteRecyclage?>
    lateinit var deleteActiviteRecyclageLiveData: MutableLiveData<ActiviteRecyclage?>
    init {
        liveData = MutableLiveData()
        liveDataStats = MutableLiveData()
        createActiviteRecyclageLiveData = MutableLiveData()
        deleteActiviteRecyclageLiveData = MutableLiveData()
    }

    private val onItemClickLiveData = MutableLiveData<String>()

    fun getOnItemClickObserver(): MutableLiveData<String> {
        return onItemClickLiveData
    }
    fun getLiveDataObserver(): MutableLiveData<List<ActiviteRecyclage>> {
        return liveData
    }
    fun getLiveDataStatsObserver(): MutableLiveData<List<MaterialStatistics>> {
        return liveDataStats
    }

    fun getCreateActiviteObserver(): MutableLiveData<ActiviteRecyclage?> {
        return createActiviteRecyclageLiveData
    }
    fun getDeleteActiviteObserver(): MutableLiveData<ActiviteRecyclage?> {
        return deleteActiviteRecyclageLiveData
    }



    fun onItemClick(activiteId: String) {
        onItemClickLiveData.postValue(activiteId)
    }

    fun getActivites() {
        val retroInstance = RetrofitInstance.getRetrofitInstantce()
        val retroService = retroInstance.create(RetrofitServiceInterface::class.java)
        val call = retroService.getActiviteRecyclage()
        call.enqueue(object : Callback<List<ActiviteRecyclage>> {
            override fun onFailure(call: Call<List<ActiviteRecyclage>>, t: Throwable) {
                liveData.postValue(null)
            }

            override fun onResponse(
                call: Call<List<ActiviteRecyclage>>,
                response: Response<List<ActiviteRecyclage>>
            ) {
                liveData.postValue(response.body())
            }
        })
    }
    fun getStatis() {
        val retroInstance = RetrofitInstance.getRetrofitInstantce()
        val retroService = retroInstance.create(RetrofitServiceInterface::class.java)
        val call = retroService.getMaterialStatistics()
        call.enqueue(object : Callback<List<MaterialStatistics>> {
            override fun onFailure(call: Call<List<MaterialStatistics>>, t: Throwable) {
                liveDataStats.postValue(null)
            }

            override fun onResponse(
                call: Call<List<MaterialStatistics>>,
                response: Response<List<MaterialStatistics>>
            ) {
                liveDataStats.postValue(response.body())
            }
        })
    }


    fun deleteActivite(activiteid: String?) {
        val retroInstance = RetrofitInstance.getRetrofitInstantce()
        val retroService = retroInstance.create(RetrofitServiceInterface::class.java)
        val call = retroService.deleteActivite(activiteid!!)
        call.enqueue(object : Callback<ActiviteRecyclage?> {
            override fun onFailure(call: Call<ActiviteRecyclage?>, t: Throwable) {
                deleteActiviteRecyclageLiveData.postValue(null)
            }

            override fun onResponse(call: Call<ActiviteRecyclage?>, response: Response<ActiviteRecyclage?>) {
                if(response.isSuccessful) {
                    deleteActiviteRecyclageLiveData.postValue(response.body())
                } else {
                    deleteActiviteRecyclageLiveData.postValue(null)
                }
            }
        })
    }
    fun getActivitesByUser(userId:String) {
        val retroInstance = RetrofitInstance.getRetrofitInstantce()
        val retroService = retroInstance.create(RetrofitServiceInterface::class.java)
        val call = retroService.getActiviteRecyclageByUser(userId)
        call.enqueue(object : Callback<List<ActiviteRecyclage>> {
            override fun onFailure(call: Call<List<ActiviteRecyclage>>, t: Throwable) {
                liveData.postValue(null)
            }

            override fun onResponse(
                call: Call<List<ActiviteRecyclage>>,
                response: Response<List<ActiviteRecyclage>>
            ) {
                liveData.postValue(response.body())
            }
        })

    }
    fun createNewActivite(activite: ActiviteRecyclage) {
        val retroService =
            RetrofitInstance.getRetrofitInstantce().create(RetrofitServiceInterface::class.java)
        val call = retroService.addActiviteRecyclage(activite)
        call.enqueue(object : Callback<ActiviteRecyclage> {
            override fun onFailure(call: Call<ActiviteRecyclage>, t: Throwable) {
                createActiviteRecyclageLiveData.postValue(null)
            }

            override fun onResponse(
                call: Call<ActiviteRecyclage>,
                response: Response<ActiviteRecyclage>
            ) {
                if (response.isSuccessful) {
                    createActiviteRecyclageLiveData.postValue(response.body())
                } else {
                    createActiviteRecyclageLiveData.postValue(null)
                }
            }

        })
    }
    fun updateActivite(activiteId:String,activite: ActiviteRecyclage) {
        val retroService =
            RetrofitInstance.getRetrofitInstantce().create(RetrofitServiceInterface::class.java)
        val call = retroService.updateActivite(activiteId,activite)
        call.enqueue(object : Callback<ActiviteRecyclage> {
            override fun onFailure(call: Call<ActiviteRecyclage>, t: Throwable) {
                createActiviteRecyclageLiveData.postValue(null)
            }
            override fun onResponse(
                call: Call<ActiviteRecyclage>,
                response: Response<ActiviteRecyclage>
            ) {
                if (response.isSuccessful) {
                    createActiviteRecyclageLiveData.postValue(response.body())
                } else {
                    createActiviteRecyclageLiveData.postValue(null)
                }
            }
        })
    }



}
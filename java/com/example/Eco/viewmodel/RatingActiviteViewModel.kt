package com.example.Eco.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.Eco.data.ActiviteRating
import com.example.Eco.data.ActiviteRecyclage
import com.example.Eco.retrofit.RetrofitInstance
import com.example.Eco.retrofit.RetrofitServiceInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RatingActiviteViewModel: ViewModel() {
    lateinit var createRatingLiveData: MutableLiveData<ActiviteRating?>
    private val activiteDetailsLiveData = MutableLiveData<ActiviteRecyclage?>()
    fun getCreateRatingObserver(): MutableLiveData<ActiviteRating?> {
        return createRatingLiveData
    }
    fun getActiviteDetailsObserver(): MutableLiveData<ActiviteRecyclage?> {
        return activiteDetailsLiveData
    }
    init {
       // liveData = MutableLiveData()
        createRatingLiveData = MutableLiveData()
    }
    fun getActiviteDetails(activiteId: String) {
        val retroService = RetrofitInstance.getRetrofitInstantce().create(RetrofitServiceInterface::class.java)
        val call = retroService.getActiviteById(activiteId) // Utilisation de getActiviteById
        call.enqueue(object : Callback<ActiviteRecyclage> {
            override fun onFailure(call: Call<ActiviteRecyclage>, t: Throwable) {
                activiteDetailsLiveData.postValue(null)
            }

            override fun onResponse(call: Call<ActiviteRecyclage>, response: Response<ActiviteRecyclage>) {
                if (response.isSuccessful) {
                    activiteDetailsLiveData.postValue(response.body())
                } else {
                    activiteDetailsLiveData.postValue(null)
                }
            }
        })
    }

    fun createNewRating(rating: ActiviteRating) {
        val retroService =
            RetrofitInstance.getRetrofitInstantce().create(RetrofitServiceInterface::class.java)
        val call = retroService.addRatingActivte(rating)
        call.enqueue(object : Callback<ActiviteRating> {
            override fun onFailure(call: Call<ActiviteRating>, t: Throwable) {
                createRatingLiveData.postValue(null)
            }

            override fun onResponse(
                call: Call<ActiviteRating>,
                response: Response<ActiviteRating>
            ) {
                if (response.isSuccessful) {
                    createRatingLiveData.postValue(response.body())
                } else {
                    createRatingLiveData.postValue(null)
                }
            }

        })
    }
}
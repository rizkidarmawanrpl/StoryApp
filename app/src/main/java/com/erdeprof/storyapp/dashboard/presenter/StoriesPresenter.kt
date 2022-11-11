package com.erdeprof.storyapp.dashboard.presenter

import com.erdeprof.storyapp.dashboard.data.ResultStories
import com.erdeprof.storyapp.network.NetworkConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StoriesPresenter (val storiesView: StoriesView) {
    fun stories(token : String? = null, location : Int? = 0) {
        NetworkConfig.getService(token)
            .stories(location)
            .enqueue(object : Callback<ResultStories> {
                override fun onFailure(call: Call<ResultStories>, t: Throwable) {
                    storiesView.onFailedStories(t.localizedMessage)
                }
                override fun onResponse(call: Call<ResultStories>, response: Response<ResultStories>) {
                    if (response.isSuccessful && response.body()?.error == false){
                        storiesView.onSuccessStories(response.body()?.message, response.body()?.listStory)
                        println(response.body()?.listStory)
                    } else {
                        storiesView.onFailedStories(response.body()?.message)
                    }
                }
            })
    }
}
package com.erdeprof.storyapp.dashboard.presenter

import com.erdeprof.storyapp.dashboard.data.ResultAddStory
import com.erdeprof.storyapp.dashboard.data.ResultStories
import com.erdeprof.storyapp.network.NetworkConfig
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddstoryPresenter (val addStoryView: AddStoryView) {
    fun addStory(token: String? = null, description: RequestBody?, photo: RequestBody?) {
        NetworkConfig.getService(token)
            .addStory(description, photo)
            .enqueue(object : Callback<ResultAddStory> {
                override fun onFailure(call: Call<ResultAddStory>, t: Throwable) {
                    addStoryView.onFailedAddStory(t.localizedMessage)
                }
                override fun onResponse(call: Call<ResultAddStory>, response: Response<ResultAddStory>) {
                    if (response.isSuccessful && response.body()?.error == false){
                        addStoryView.onSuccessAddStory(response.body()?.message)
                    } else {
                        addStoryView.onFailedAddStory(response.body()?.message)
                    }
                }
            })
    }
}
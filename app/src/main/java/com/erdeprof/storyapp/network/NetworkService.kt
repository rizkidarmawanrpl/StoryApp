package com.erdeprof.storyapp.network

import com.erdeprof.storyapp.dashboard.data.ResultAddStory
import com.erdeprof.storyapp.dashboard.data.ResultStories
import com.erdeprof.storyapp.login.data.ResultLogin
import com.erdeprof.storyapp.register.data.ResultRegister
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface NetworkService {
    @FormUrlEncoded
    @POST("login")
    fun login(@Field("email") email : String?,
              @Field("password") password: String?
    ) : Call<ResultLogin>

    @FormUrlEncoded
    @POST("register")
    fun register(@Field("name") name : String?,
                 @Field("email") email : String?,
                 // @Field("hp") hp : String?,
                 @Field("password") password : String?,
                 // @Field("alamat") alamat : String?
    ) : Call<ResultRegister>

    @GET("stories")
    fun stories() : Call<ResultStories>

    @Multipart
    @POST("stories")
    fun addStory(@Part("description") description : RequestBody?,
                 @Part("photo") photo : RequestBody? //MultipartBody.Part?
    ) : Call<ResultAddStory>
}
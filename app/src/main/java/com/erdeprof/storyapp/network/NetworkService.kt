package com.erdeprof.storyapp.network

import com.erdeprof.storyapp.login.data.ResultLogin
import com.erdeprof.storyapp.register.data.ResultRegister
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface NetworkService {
    @FormUrlEncoded
    @POST("login")
    fun login(@Field("email") email : String?,
              @Field("password") password: String?) : Call<ResultLogin>

    @FormUrlEncoded
    @POST("register")
    fun register(@Field("name") name : String?,
                 @Field("email") email : String?,
                 // @Field("hp") hp : String?,
                 @Field("password") password : String?,
                 // @Field("alamat") alamat : String?
    ) : Call<ResultRegister>
}
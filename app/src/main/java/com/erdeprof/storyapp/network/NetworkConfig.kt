package com.erdeprof.storyapp.network

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import com.erdeprof.storyapp.register.RegisterActivity
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkConfig {
    fun getInterceptor(token : String?) : OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor { chain ->
                chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer $token")
                    .build()
                    .let(chain::proceed)
            }
            .build()

        return okHttpClient
    }

    //Retrofit

    fun getRetrofit(token : String?): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://story-api.dicoding.dev/v1/")
            .client(getInterceptor(token))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getService(token : String? = null) = getRetrofit(token).create(NetworkService::class.java)
}
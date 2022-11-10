package com.erdeprof.storyapp.login.presenter

import com.erdeprof.storyapp.login.data.ResultLogin
import com.erdeprof.storyapp.network.NetworkConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginPresenter (val loginView: LoginView) {
    fun login(email : String, password : String){
        NetworkConfig.getService()
            .login(email,password)
            .enqueue(object : Callback<ResultLogin>{
                override fun onFailure(call: Call<ResultLogin>, t: Throwable) {
                    loginView.onFailedLogin(t.localizedMessage)
                }
                override fun onResponse(call: Call<ResultLogin>, response: Response<ResultLogin>) {
                    if (response.isSuccessful && response.body()?.error == false){
                        loginView.onSuccessLogin(response.body()?.message, response.body()?.loginResult)
                    } else {
                        loginView.onFailedLogin(response.body()?.message)
                    }
                }
            })
    }
}
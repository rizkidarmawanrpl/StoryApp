package com.erdeprof.storyapp.register.presenter

import com.erdeprof.storyapp.network.NetworkConfig
import com.erdeprof.storyapp.register.data.ResultRegister
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterPresenter (val registerView: RegisterView){

    fun register( name : String?, email : String?, password : String?){
        NetworkConfig.getService()
            .register(name,email,password)
            .enqueue(object : Callback<ResultRegister>{
                override fun onFailure(call: Call<ResultRegister>, t: Throwable) {
                    registerView.onFailedRegister(t.localizedMessage)
                }

                override fun onResponse(
                    call: Call<ResultRegister>,
                    response: Response<ResultRegister>
                ) {
                    if (response.body()?.status == 200){
                        registerView.onSuccessRegister(response.body()?.message)
                    } else{
                        registerView.onFailedRegister(response.body()?.message)
                    }
                }
            })
    }
}
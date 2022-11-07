package com.erdeprof.storyapp.register.presenter

interface RegisterView {
    fun onSuccessRegister(msg : String?)
    fun onFailedRegister(msg: String?)
}
package com.erdeprof.storyapp.login.data

import com.erdeprof.storyapp.login.presenter.LoginPresenter
import com.erdeprof.storyapp.login.presenter.LoginView

class LoginRepository: LoginView {
    val loginPresenter = LoginPresenter(this)
    var loginResult : User = User()

    fun login(email: String, password: String) {
        loginPresenter.login(email, password)
    }

    fun getLogin(): User {
        return loginResult
    }

    override fun onSuccessLogin(msg: String?, data: User?) {
        if (data != null) {
            loginResult = data
        }
    }

    override fun onFailedLogin(msg: String?) {
    }
}
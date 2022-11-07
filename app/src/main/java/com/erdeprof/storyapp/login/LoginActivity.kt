package com.erdeprof.storyapp.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.erdeprof.storyapp.R

import com.erdeprof.storyapp.dashboard.DashboardActivity
// import com.erdeprof.storyapp.login.data.Staff
import com.erdeprof.storyapp.login.data.User
import com.erdeprof.storyapp.login.presenter.LoginPresenter
import com.erdeprof.storyapp.login.presenter.LoginView
import com.erdeprof.storyapp.register.RegisterActivity
// import org.jetbrains.anko.startActivity

class LoginActivity : AppCompatActivity(), LoginView {
    private lateinit var presenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        presenter = LoginPresenter(this)

        var btnLogin = findViewById<Button>(R.id.btnLogin)
        var btnRegister = findViewById<Button>(R.id.btnRegister)

        btnLogin.setOnClickListener {
            val email = R.id.loginEmail.toString()
            val password = R.id.loginPassword.toString()
            presenter.login(email, password)
        }

        btnRegister.setOnClickListener {
            // startActivity<RegisterActivity>()

            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onSuccessLogin(msg: String?, data: User?) {
        /*alert {
            title = "Information"
            message = "Login Success"
        }.show()*/

        Toast.makeText(this@LoginActivity, "Login Success.", Toast.LENGTH_SHORT).show()

        // startActivity<DashboardActivity>()

        val intent = Intent(this, DashboardActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onFailedLogin(msg: String?) {
        /*alert {
            title = "Information"
            message = "Login Failed Silahkan Cek Email Password"
        }.show()*/

        Toast.makeText(this@LoginActivity, msg, Toast.LENGTH_SHORT).show()
    }
}
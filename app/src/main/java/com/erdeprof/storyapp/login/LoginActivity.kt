package com.erdeprof.storyapp.login

// import com.erdeprof.storyapp.login.data.Staff
// import org.jetbrains.anko.startActivity

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.system.Os.uname
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.erdeprof.storyapp.R
import com.erdeprof.storyapp.dashboard.DashboardActivity
import com.erdeprof.storyapp.login.data.User
import com.erdeprof.storyapp.login.presenter.LoginPresenter
import com.erdeprof.storyapp.login.presenter.LoginView
import com.erdeprof.storyapp.register.RegisterActivity


class LoginActivity : AppCompatActivity(), LoginView {
    private lateinit var presenter: LoginPresenter
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        presenter = LoginPresenter(this)

        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val btnRegister = findViewById<Button>(R.id.btnRegister)

        btnLogin.setOnClickListener {
            val email = findViewById<EditText>(R.id.loginEmail).text.toString()
            val password = findViewById<EditText>(R.id.loginPassword).text.toString()
            presenter.login(email, password)
        }

        btnRegister.setOnClickListener {
            // startActivity<RegisterActivity>()

            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        playAnimation()
    }

    override fun onSuccessLogin(msg: String?, data: User?) {
        /*alert {
            title = "Information"
            message = "Login Success"
        }.show()*/

        sharedPreferences = getSharedPreferences("user_details", MODE_PRIVATE);
        sharedPreferences.contains("token");

        val editor = sharedPreferences.edit()
        editor.putString("token", data?.token)
        editor.apply()

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

    private fun playAnimation() {
        val ivGambar = findViewById<ImageView>(R.id.ivGambar)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val btnRegister = findViewById<Button>(R.id.btnRegister)
        val loginEmail = findViewById<EditText>(R.id.loginEmail)
        val loginPassword = findViewById<EditText>(R.id.loginPassword)
        val tvTitle = findViewById<TextView>(R.id.tvTitle)

        ObjectAnimator.ofFloat(ivGambar, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val login = ObjectAnimator.ofFloat(btnLogin, View.ALPHA, 1f).setDuration(500)
        val register = ObjectAnimator.ofFloat(btnRegister, View.ALPHA, 1f).setDuration(500)
        val email = ObjectAnimator.ofFloat(loginEmail, View.ALPHA, 1f).setDuration(500)
        val password = ObjectAnimator.ofFloat(loginPassword, View.ALPHA, 1f).setDuration(500)
        val title = ObjectAnimator.ofFloat(tvTitle, View.ALPHA, 1f).setDuration(500)

        val together = AnimatorSet().apply {
            playTogether(login, register)
        }

        AnimatorSet().apply {
            playSequentially(title, email, password, together)
            start()
        }
    }
}
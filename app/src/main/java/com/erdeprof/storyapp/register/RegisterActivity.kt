package com.erdeprof.storyapp.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.erdeprof.storyapp.R

import com.erdeprof.storyapp.register.presenter.RegisterPresenter
import com.erdeprof.storyapp.register.presenter.RegisterView

class RegisterActivity : AppCompatActivity(), RegisterView {
    private lateinit var presenter : RegisterPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        presenter = RegisterPresenter(this)

        val btnRegister = findViewById<Button>(R.id.btnRegister)

        btnRegister.setOnClickListener {
            val name = findViewById<EditText>(R.id.registerName).text.toString()
            val email = findViewById<EditText>(R.id.registerEmail).text.toString()
            // val hp = R.id.registerHp.toString()
            val password = findViewById<EditText>(R.id.registerPassword).text.toString()
            // val alamat = registerAlamat.text.toString()

            presenter.register(name, email, password)
        }
    }

    override fun onSuccessRegister(msg: String?) {
        /*alert {
            title = "Information Register"
            message = "Success Register"
        }.show()*/

        Toast.makeText(this@RegisterActivity, "Success Register.", Toast.LENGTH_SHORT).show()
    }

    override fun onFailedRegister(msg: String?) {
        /*alert {
            title = "Information Register"
            message = msg.toString()
        }.show()*/

        Toast.makeText(this@RegisterActivity, msg.toString(), Toast.LENGTH_SHORT).show()
    }
}
package com.erdeprof.storyapp.register

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.erdeprof.storyapp.R
import com.erdeprof.storyapp.customview.MyEditText
import com.erdeprof.storyapp.login.LoginActivity

import com.erdeprof.storyapp.register.presenter.RegisterPresenter
import com.erdeprof.storyapp.register.presenter.RegisterView

class RegisterActivity : AppCompatActivity(), RegisterView {
    private lateinit var presenter : RegisterPresenter
    private lateinit var myEditText: MyEditText

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        presenter = RegisterPresenter(this)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setTitle(getString(R.string.text_register))

        val btnRegister = findViewById<Button>(R.id.btnRegister)
        myEditText = findViewById(R.id.registerPasswordCustom)

        btnRegister.setOnClickListener {
            val name = findViewById<EditText>(R.id.registerName).text.toString()
            val email = findViewById<EditText>(R.id.registerEmail).text.toString()
            val password = myEditText.text.toString()

            presenter.register(name, email, password)
        }
    }

    override fun onSuccessRegister(msg: String?) {
        Toast.makeText(this@RegisterActivity, "${getString(R.string.text_success_register)}.", Toast.LENGTH_SHORT).show()

        val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onFailedRegister(msg: String?) {
        Toast.makeText(this@RegisterActivity, msg.toString(), Toast.LENGTH_SHORT).show()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
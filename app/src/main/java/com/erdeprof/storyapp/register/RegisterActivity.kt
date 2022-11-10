package com.erdeprof.storyapp.register

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.erdeprof.storyapp.R
import com.erdeprof.storyapp.customview.MyEditText
import com.erdeprof.storyapp.dashboard.DashboardActivity
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

        val btnRegister = findViewById<Button>(R.id.btnRegister)
        myEditText = findViewById(R.id.registerPasswordCustom)

        btnRegister.setOnClickListener {
            val name = findViewById<EditText>(R.id.registerName).text.toString()
            val email = findViewById<EditText>(R.id.registerEmail).text.toString()
            // val hp = R.id.registerHp.toString()
            val password = myEditText.text.toString() // findViewById<EditText>(R.id.registerPassword).text.toString()
            // val alamat = registerAlamat.text.toString()

            presenter.register(name, email, password)
        }

        myEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }
            override fun afterTextChanged(s: Editable) {
                if (myEditText.toString().isNotEmpty() && myEditText.text.toString().length < 6) {
                    myEditText.setError("Password minimal harus 6 karakter!");
                } else {
                    myEditText.setError(null);
                }
            }
        })
    }

    override fun onSuccessRegister(msg: String?) {
        Toast.makeText(this@RegisterActivity, "Success Register.", Toast.LENGTH_SHORT).show()

        val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onFailedRegister(msg: String?) {
        Toast.makeText(this@RegisterActivity, msg.toString(), Toast.LENGTH_SHORT).show()
    }
}
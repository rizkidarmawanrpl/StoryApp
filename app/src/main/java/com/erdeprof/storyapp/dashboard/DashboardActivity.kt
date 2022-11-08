package com.erdeprof.storyapp.dashboard

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.erdeprof.storyapp.R
import com.erdeprof.storyapp.login.LoginActivity


class DashboardActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        sharedPreferences = getSharedPreferences("user_details", MODE_PRIVATE);

        val token = sharedPreferences.getString("token", null);

        if (token == null) {
            val intent = Intent(this@DashboardActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        val btnLogout = findViewById<Button>(R.id.btnLogOut)

        btnLogout.setOnClickListener(View.OnClickListener {
            val editor = sharedPreferences.edit()
            editor.clear()
            editor.apply()

            val intent = Intent(this@DashboardActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        })

        println("TOKEN = " + token.toString());
    }
}
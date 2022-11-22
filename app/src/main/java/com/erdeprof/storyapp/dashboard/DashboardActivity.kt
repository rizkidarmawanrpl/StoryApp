package com.erdeprof.storyapp.dashboard

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.erdeprof.storyapp.R
import com.erdeprof.storyapp.dashboard.adapter.ListStoryPagerAdapter
import com.erdeprof.storyapp.dashboard.data.Story
import com.erdeprof.storyapp.dashboard.model.MainViewModel
import com.erdeprof.storyapp.login.LoginActivity

class DashboardActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var rvStories: RecyclerView
    private var session: Boolean? = false
    private var token: String? = ""

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        sharedPreferences = getSharedPreferences("user_details", MODE_PRIVATE);

        session = sharedPreferences.getBoolean("session", false);
        token = sharedPreferences.getString("token", null);

        val btnAddStory = findViewById<Button>(R.id.btnAddStory)
        val btnMap = findViewById<Button>(R.id.btnMap)
        val btnLogout = findViewById<Button>(R.id.btnLogOut)

        btnLogout.setOnClickListener(View.OnClickListener {
            val editor = sharedPreferences.edit()
            editor.clear()
            editor.apply()

            val intent = Intent(this@DashboardActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        })

        btnAddStory.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@DashboardActivity, AddStoryActivity::class.java)
            startActivity(intent)
        })

        btnMap.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@DashboardActivity, MapsActivity::class.java)
            startActivity(intent)
        })
    }

    override fun onResume() {
        super.onResume()

        if (session == false) {
            val intent = Intent(this@DashboardActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            rvStories = findViewById(R.id.rvStories)
            rvStories.setHasFixedSize(true)

            rvStories.layoutManager = LinearLayoutManager(this)

            getData()
        }
    }

    private fun getData() {
        val mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[MainViewModel::class.java]

        val adapter = ListStoryPagerAdapter()
        rvStories.adapter = adapter
        token?.let { mainViewModel.getStory(it) }
        mainViewModel.story.observe(this) {
            adapter.submitData(lifecycle, it)
        }

        adapter.setOnItemClickCallback(object : ListStoryPagerAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Story) {
                showSelectedStory(data)
            }
        })
    }

    private fun showSelectedStory(story: Story) {
        val activityDetail = Intent(this@DashboardActivity, DetailActivity::class.java)
        activityDetail.putExtra(DetailActivity.EXTRA_STORY, story)
        startActivity(activityDetail)
    }
}
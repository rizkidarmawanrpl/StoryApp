package com.erdeprof.storyapp.dashboard

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.erdeprof.storyapp.R
import com.erdeprof.storyapp.dashboard.adapter.ListStoryAdapter
import com.erdeprof.storyapp.dashboard.data.Story
import com.erdeprof.storyapp.dashboard.presenter.StoriesPresenter
import com.erdeprof.storyapp.dashboard.presenter.StoriesView
import com.erdeprof.storyapp.login.LoginActivity


class DashboardActivity : AppCompatActivity(), StoriesView {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var rvStories: RecyclerView
    private lateinit var storiesPresenter: StoriesPresenter
    private var token: String? = ""
    private val list = ArrayList<Story>()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        sharedPreferences = getSharedPreferences("user_details", MODE_PRIVATE);

        token = sharedPreferences.getString("token", null);

        val btnAddStory =findViewById<Button>(R.id.btnAddStory)
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

        // println("TOKEN = " + token.toString());
    }

    override fun onResume() {
        super.onResume()

        if (token == null) {
            val intent = Intent(this@DashboardActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            storiesPresenter = StoriesPresenter(this)
            storiesPresenter.stories(token)

            rvStories = findViewById(R.id.rvStories)
            rvStories.setHasFixedSize(true)
        }
    }

    override fun onSuccessStories(msg: String?, data: ArrayList<Story>?) {
        if (data != null) {
            list.addAll(data)
            showRecyclerList()
        }
    }

    override fun onFailedStories(msg: String?) {
        Toast.makeText(this@DashboardActivity, msg, Toast.LENGTH_SHORT).show()
    }

    private fun showRecyclerList() {
        rvStories.layoutManager = LinearLayoutManager(this)
        val listStoryAdapter = ListStoryAdapter(list)
        rvStories.adapter = listStoryAdapter

        listStoryAdapter.setOnItemClickCallback(object : ListStoryAdapter.OnItemClickCallback {
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
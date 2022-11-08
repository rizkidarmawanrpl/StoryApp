package com.erdeprof.storyapp.dashboard

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
import com.erdeprof.storyapp.login.presenter.LoginPresenter


class DashboardActivity : AppCompatActivity(), StoriesView {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var rvStories: RecyclerView
    private lateinit var storiesPresenter: StoriesPresenter
    private val list = ArrayList<Story>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        sharedPreferences = getSharedPreferences("user_details", MODE_PRIVATE);

        val token = sharedPreferences.getString("token", null);

        val btnLogout = findViewById<Button>(R.id.btnLogOut)

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
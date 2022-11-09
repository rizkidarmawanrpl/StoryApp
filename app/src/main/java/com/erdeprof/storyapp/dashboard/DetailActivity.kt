package com.erdeprof.storyapp.dashboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.erdeprof.storyapp.R
import com.erdeprof.storyapp.dashboard.data.Story

class DetailActivity : AppCompatActivity() {
    private var id: String = ""
    private var name: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setTitle("Story Detail")

        val story = intent.getParcelableExtra<Story>(EXTRA_STORY) as Story
        id = story.id.toString()
        name = story.name.toString()

        val tvName = findViewById<TextView>(R.id.tvName)

        tvName.text = name

        println("ID $id")
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {
        const val EXTRA_STORY = "extra_story"
    }
}
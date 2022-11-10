package com.erdeprof.storyapp.dashboard

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.erdeprof.storyapp.R
import com.erdeprof.storyapp.dashboard.data.Story
import java.text.SimpleDateFormat

class DetailActivity : AppCompatActivity() {
    private var id: String = ""
    private var name: String = ""
    private var photoUrl: String = ""
    private var description: String = ""
    private var createdAt: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setTitle(getString(R.string.text_story_detail))

        val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val formatter = SimpleDateFormat("dd MMM yyyy")

        val story = intent.getParcelableExtra<Story>(EXTRA_STORY) as Story
        id = story.id.toString()
        name = story.name.toString()
        photoUrl = story.photoUrl.toString()
        description = story.description.toString()
        createdAt = formatter.format(parser.parse(story.createdAt.toString()))

        val tvName = findViewById<TextView>(R.id.tvName)
        val imgPhoto = findViewById<ImageView>(R.id.imgPhoto)
        val tvDescription = findViewById<TextView>(R.id.tvDescription)
        val tvCreatedAt = findViewById<TextView>(R.id.tvCreatedAt)

        Glide.with(this)
            .load(photoUrl)
            .placeholder(R.mipmap.ic_launcher)
            .error(R.mipmap.ic_launcher)
            .into(imgPhoto)
        tvName.text = name
        tvDescription.text = description
        tvCreatedAt.text = createdAt
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {
        const val EXTRA_STORY = "extra_story"
    }
}
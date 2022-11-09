package com.erdeprof.storyapp.dashboard

import android.content.Intent
import android.content.SharedPreferences
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.loader.content.CursorLoader
import com.erdeprof.storyapp.R
import com.erdeprof.storyapp.dashboard.presenter.AddStoryView
import com.erdeprof.storyapp.dashboard.presenter.AddstoryPresenter
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File


class AddStoryActivity : AppCompatActivity(), AddStoryView {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var presenterAddStory : AddstoryPresenter
    private lateinit var fileUri : Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_story)
        presenterAddStory = AddstoryPresenter(this)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setTitle("Add Story")

        sharedPreferences = getSharedPreferences("user_details", MODE_PRIVATE);

        val token = sharedPreferences.getString("token", null);

        var imgPhoto = findViewById<ImageView>(R.id.imgPhoto)
        val btnGallery = findViewById<Button>(R.id.btnGallery)
        val btnUpload = findViewById<Button>(R.id.btnUpload)

        val getContent = registerForActivityResult(ActivityResultContracts.GetContent())  { uri: Uri? ->
            if (uri != null) {
                imgPhoto.setImageURI(uri)
                fileUri = uri
            }    // Handle the returned Uri
        }

        btnGallery.setOnClickListener(View.OnClickListener {
            getContent.launch("image/*")
        })

        btnUpload.setOnClickListener(View.OnClickListener {
            val file = File(getRealPathFromURI(fileUri))

            val description = findViewById<EditText>(R.id.etDescription).text.toString().toRequestBody("text/plain".toMediaType())
            val photo = file.asRequestBody("image/jpeg".toMediaType())

            presenterAddStory.addStory(token, description, photo)

            /*val intent = Intent(this@AddStoryActivity, DashboardActivity::class.java)
            startActivity(intent)
            finish()*/
        })
    }

    override fun onSuccessAddStory(msg: String?) {
        println(msg)
        Toast.makeText(this@AddStoryActivity, msg.toString(), Toast.LENGTH_SHORT).show()
    }

    override fun onFailedAddStory(msg: String?) {
        println(msg)
        Toast.makeText(this@AddStoryActivity, msg.toString(), Toast.LENGTH_SHORT).show()
    }

    private fun getRealPathFromURI(contentUri: Uri): String? {
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val loader = CursorLoader(this, contentUri, proj, null, null, null)
        val cursor: Cursor? = loader.loadInBackground()
        val column_index = cursor?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor?.moveToFirst()
        val result = column_index?.let { cursor?.getString(it) }
        cursor?.close()
        return result
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
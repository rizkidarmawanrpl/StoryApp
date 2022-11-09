package com.erdeprof.storyapp.dashboard

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.database.Cursor
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
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
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.*


class AddStoryActivity : AppCompatActivity(), AddStoryView {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var presenterAddStory : AddstoryPresenter
    private lateinit var fileUri : File
    private lateinit var currentPhotoPath: String

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
        val btnCamera = findViewById<Button>(R.id.btnCamera)
        val btnUpload = findViewById<Button>(R.id.btnUpload)

        val launcherIntentGallery = registerForActivityResult(ActivityResultContracts.StartActivityForResult())  { result ->
            if (result.resultCode == RESULT_OK) {
                val selectedImg = result.data?.data as Uri
                fileUri = uriToFile(selectedImg, this)
                imgPhoto.setImageURI(selectedImg)
            }
        }

        val launcherIntentCamera = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                fileUri = File(currentPhotoPath)

                val resultImg = BitmapFactory.decodeFile(fileUri?.path)
                imgPhoto.setImageBitmap(resultImg)
            }
        }

        btnGallery.setOnClickListener(View.OnClickListener {
            launcherIntentGallery.launch(Intent.createChooser(Intent().apply {
                action = Intent.ACTION_GET_CONTENT
                type = "image/*"
            }, "Pilih Gambar"))
        })

        btnCamera.setOnClickListener(View.OnClickListener {
            try {
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                intent.resolveActivity(packageManager)

                createCustomTempFile(application).also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        this,
                        "com.erdeprof.storyapp.mycamera",
                        it
                    )

                    currentPhotoPath = it.absolutePath
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    launcherIntentCamera.launch(intent)
                }
            } catch (e: Exception){
                Log.d("DEBUG", e.message.toString())
            }
        })

        btnUpload.setOnClickListener(View.OnClickListener {
            val description = findViewById<EditText>(R.id.etDescription).text.toString().toRequestBody("text/plain".toMediaType())
            val requestImageFile = fileUri.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val photo = MultipartBody.Part.createFormData("photo", fileUri.name, requestImageFile)

            presenterAddStory.addStory(token, description, photo)

            val intent = Intent(this@AddStoryActivity, DashboardActivity::class.java)
            startActivity(intent)
            finish()
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

    private val timeStamp: String = SimpleDateFormat(
        "HHmmddMMyyyy",
        Locale.US
    ).format(System.currentTimeMillis())

    fun createCustomTempFile(context: Context): File {
        val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(timeStamp, ".jpg", storageDir)
    }

    private fun uriToFile(selectedImg: Uri, context: Context): File {
        val contentResolver = context.contentResolver
        val myFile = createCustomTempFile(context)

        val inputStream = contentResolver.openInputStream(selectedImg) as InputStream
        val outputStream: OutputStream = FileOutputStream(myFile)
        val buf = ByteArray(1024)
        var len: Int

        while(inputStream.read(buf).also { len = it } > 0) outputStream.write(buf, 0, len)
        outputStream.close()
        inputStream.close()

        return myFile
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
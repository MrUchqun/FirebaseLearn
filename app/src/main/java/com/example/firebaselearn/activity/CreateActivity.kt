package com.example.firebaselearn.activity

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firebaselearn.R
import com.example.firebaselearn.activity.BaseActivity
import com.example.firebaselearn.managers.DatabaseHandler
import com.example.firebaselearn.managers.DatabaseManager
import com.example.firebaselearn.model.Post
import java.lang.Exception

class CreateActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)
        initViews()
    }

    private fun initViews() {
        val iv_close = findViewById<ImageView>(R.id.iv_close)
        val et_title = findViewById<EditText>(R.id.et_title)
        val et_body = findViewById<EditText>(R.id.et_body)
        val b_create = findViewById<Button>(R.id.b_create)

        iv_close.setOnClickListener {
            finish()
        }

        b_create.setOnClickListener {
            val title = et_title.text.toString().trim()
            val body = et_body.text.toString().trim()
            val post = Post(title, body)
            storePost(post)
        }
    }

    private fun storePost(post: Post) {
        storeDatabase(post)
    }


    private fun storeDatabase(post: Post) {
        showLoading(this)
        DatabaseManager.storePost(post, object : DatabaseHandler {
            override fun onSuccess(post: Post?, posts: ArrayList<Post>) {
                Log.d("@@@", "post is saved")
                dismissLoading()
                finishIntent()
            }

            override fun onError() {
                dismissLoading()
                Log.d("@@@", "post is not saved")
            }
        })
    }

    fun finishIntent() {
        val returnIntent = intent
        setResult(RESULT_OK, returnIntent)
        finish()
    }
}